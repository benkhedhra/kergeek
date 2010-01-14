package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Velo;

/**
 * FenetreDemandeConfirmationAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle propose à l'administrateur connecté de confirmer son action ou de revenir à l'écran précédent
 * <br>elle intervient pour des actions considérées irréversibles: la suppression d'un compte ou d'un vélo
 * @author KerGeek
 */
public class FenetreDemandeConfirmationAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * administrateur est l'administrateur connecté à la fenêtre
	 */
	private Administrateur administrateur;
	/**
	 * fenetrePrecedente est la fenêtre sur laquelle l'administrateur était connecté à l'étape précédente
	 */
	private JFrame fenetrePrecedente;
	/**
	 * compte est le compte que l'administrateur a pu vouloir résilier à l'étape précédente
	 */
	private Compte compte;
	/**
	 * velo est le vélo que l'administrateur a pu vouloir résilier à l'étape précédente
	 */
	private Velo velo;
	/**
	 * labelAdmin permet d'afficher en haut de la fenêtre qui est l'individu connecté
	 */
	private JLabel labelAdmin = new JLabel("");
	/**
	 * labelChemin permet d'afficher l'endroit où l'on se trouve dans l'application
	 */
	private JLabel labelChemin = new JLabel("");
	/**
	 * labelConfirm pose la question adéquate à l'administrateur
	 */
	private JLabel labelConfirm = new JLabel("");
	//deux boutons pour que l'administrateur confirme ou infirme son intention
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("<html><center>NON <br>(revenir à l'écran précédent)</center></html>");

	//Accesseurs utiles

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public JFrame getFenetrePrecedente() {
		return fenetrePrecedente;
	}

	public void setFenetrePrecedente(JFrame fenetrePrecedente) {
		this.fenetrePrecedente = fenetrePrecedente;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Velo getVelo() {
		return velo;
	}

	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	/**
	 * constructeur de {@link FenetreDemandeConfirmationAdmin}
	 * @param a
	 * l'administrateur connecté sur cette fenêtre
	 * @param fenetrePrec
	 * la fenêtre sur laquelle l'administrateur était connecté à l'étape précédente
	 */

	public FenetreDemandeConfirmationAdmin(Administrateur a, JFrame fenetrePrec){

		this.setContentPane(new PanneauAdmin());

		this.setAdministrateur(a);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Fenêtre de demande de confirmation");
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		labelAdmin.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		if(fenetrePrec.getTitle().equals("Modifier informations sur un compte")){
			labelChemin.setText("Menu principal > Gérer les comptes > Afficher informations sur un compte > Modifier > Confirmation de la résiliation");
			FenetreModifCompteAdmin f = (FenetreModifCompteAdmin) fenetrePrec;
			Compte compte = f.getCompte();
			this.setCompte(compte);
			labelConfirm.setText("Voulez-vous confirmer la résiliation du compte "+compte.getId()+" ? ");
		}
		else if(fenetrePrec.getTitle().equals("Suppression d'un vélo")){
			FenetreSupprimerUnVeloAdmin f = (FenetreSupprimerUnVeloAdmin)fenetrePrec;
			labelChemin.setText("Menu principal > Voir l'état du parc > Liste des vélos présents dans un lieu > Vélos en cours d'emprunt > Supprimer un vélo > Confirmation de la suppression");
			Velo velo = f.getVeloEntre();
			this.setVelo(velo);
			labelConfirm.setText("Souhaitez-vous confirmer la suppression du vélo "+velo.getId()+" ? ");
		}
		labelConfirm.setPreferredSize(new Dimension(1100,40));
		boutonOui.setPreferredSize(new Dimension(300,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(300,50));
		boutonNon.setBackground(Color.CYAN);

		boutonOui.addActionListener(this);
		boutonNon.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelConfirm);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}
	
	/**
	 * méthode exécutée quand l'administrateur vientde confirmer ou d'infirmer son action
	 * @param arg0
	 * @see Administrateur#resilierCompte(Compte)
	 * @see Administrateur#supprimerVelo(Velo)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			//s'il est dans le contexte de la modification d'un compte
			if(fenetrePrecedente.getTitle().equals("Modifier informations sur un compte")){
				if(arg0.getSource()==boutonOui){
					//il vient de confirmer la suppression du compte
					Compte c = this.getAdministrateur().resilierCompte(this.getCompte());
					DAOCompte.updateCompte(c);
					new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				}
				else if (arg0.getSource()==boutonNon){
					//il vient d'infirmer la suppression du compte
					new FenetreModifCompteAdmin(this.getCompte(),this.getAdministrateur());
				}
			}
			//s'il est dans le contexte de la suppression d'un vélo (emprunté depuis plus de 30 jours)
			else if(fenetrePrecedente.getTitle().equals("Suppression d'un vélo")){
				if(arg0.getSource()==boutonOui){
					//il vient de confirmer la suppression du vélo
					Velo v = this.getAdministrateur().supprimerVelo(this.getVelo());
					DAOVelo.updateVelo(v);
					new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				}
				else if (arg0.getSource()==boutonNon){
					//il vient d'infirmer la suppression du vélo
					new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
				}
			}
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		} catch (ConnexionFermeeException e) {
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}