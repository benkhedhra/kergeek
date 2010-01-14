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
 * FenetreDemandeConfirmationAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle propose � l'administrateur connect� de confirmer son action ou de revenir � l'�cran pr�c�dent
 * <br>elle intervient pour des actions consid�r�es irr�versibles: la suppression d'un compte ou d'un v�lo
 * @author KerGeek
 */
public class FenetreDemandeConfirmationAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * administrateur est l'administrateur connect� � la fen�tre
	 */
	private Administrateur administrateur;
	/**
	 * fenetrePrecedente est la fen�tre sur laquelle l'administrateur �tait connect� � l'�tape pr�c�dente
	 */
	private JFrame fenetrePrecedente;
	/**
	 * compte est le compte que l'administrateur a pu vouloir r�silier � l'�tape pr�c�dente
	 */
	private Compte compte;
	/**
	 * velo est le v�lo que l'administrateur a pu vouloir r�silier � l'�tape pr�c�dente
	 */
	private Velo velo;
	/**
	 * labelAdmin permet d'afficher en haut de la fen�tre qui est l'individu connect�
	 */
	private JLabel labelAdmin = new JLabel("");
	/**
	 * labelChemin permet d'afficher l'endroit o� l'on se trouve dans l'application
	 */
	private JLabel labelChemin = new JLabel("");
	/**
	 * labelConfirm pose la question ad�quate � l'administrateur
	 */
	private JLabel labelConfirm = new JLabel("");
	//deux boutons pour que l'administrateur confirme ou infirme son intention
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("<html><center>NON <br>(revenir � l'�cran pr�c�dent)</center></html>");

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
	 * l'administrateur connect� sur cette fen�tre
	 * @param fenetrePrec
	 * la fen�tre sur laquelle l'administrateur �tait connect� � l'�tape pr�c�dente
	 */

	public FenetreDemandeConfirmationAdmin(Administrateur a, JFrame fenetrePrec){

		this.setContentPane(new PanneauAdmin());

		this.setAdministrateur(a);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Fen�tre de demande de confirmation");
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
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
			labelChemin.setText("Menu principal > G�rer les comptes > Afficher informations sur un compte > Modifier > Confirmation de la r�siliation");
			FenetreModifCompteAdmin f = (FenetreModifCompteAdmin) fenetrePrec;
			Compte compte = f.getCompte();
			this.setCompte(compte);
			labelConfirm.setText("Voulez-vous confirmer la r�siliation du compte "+compte.getId()+" ? ");
		}
		else if(fenetrePrec.getTitle().equals("Suppression d'un v�lo")){
			FenetreSupprimerUnVeloAdmin f = (FenetreSupprimerUnVeloAdmin)fenetrePrec;
			labelChemin.setText("Menu principal > Voir l'�tat du parc > Liste des v�los pr�sents dans un lieu > V�los en cours d'emprunt > Supprimer un v�lo > Confirmation de la suppression");
			Velo velo = f.getVeloEntre();
			this.setVelo(velo);
			labelConfirm.setText("Souhaitez-vous confirmer la suppression du v�lo "+velo.getId()+" ? ");
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
	 * m�thode ex�cut�e quand l'administrateur vientde confirmer ou d'infirmer son action
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
			//s'il est dans le contexte de la suppression d'un v�lo (emprunt� depuis plus de 30 jours)
			else if(fenetrePrecedente.getTitle().equals("Suppression d'un v�lo")){
				if(arg0.getSource()==boutonOui){
					//il vient de confirmer la suppression du v�lo
					Velo v = this.getAdministrateur().supprimerVelo(this.getVelo());
					DAOVelo.updateVelo(v);
					new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				}
				else if (arg0.getSource()==boutonNon){
					//il vient d'infirmer la suppression du v�lo
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
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}