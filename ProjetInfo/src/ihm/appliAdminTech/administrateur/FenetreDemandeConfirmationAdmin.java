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

public class FenetreDemandeConfirmationAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JFrame fenetrePrecedente;
	private Compte compte;
	private Velo velo;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelConfirm = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (revenir à l'écran précédent)");

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

	public FenetreDemandeConfirmationAdmin(Administrateur a, JFrame fenetrePrec){

		this.setContentPane(new PanneauAdmin());

		this.setAdministrateur(a);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Fenêtre de demande de confirmation");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdminTech.setFont(UtilitaireIhm.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdminTech);
		this.add(north, BorderLayout.NORTH);

		if(fenetrePrec.getTitle().equals("Modifier informations sur un compte")){
			FenetreModifCompteAdmin f = (FenetreModifCompteAdmin) fenetrePrec;
			Compte compte = f.getCompte();
			this.setCompte(compte);
			labelConfirm.setText("Voulez-vous confirmer la résiliation du compte "+compte.getId()+" ? ");
		}
		else if(fenetrePrec.getTitle().equals("Suppression d'un vélo")){
			FenetreSupprimerUnVeloAdmin f = (FenetreSupprimerUnVeloAdmin)fenetrePrec;
			Velo velo = f.getVeloEntre();
			this.setVelo(velo);
			labelConfirm.setText("Souhaitez-vous confirmer la suppression du vélo "+velo.getId()+" ? ");
		}
		labelConfirm.setPreferredSize(new Dimension(550,30));
		boutonOui.setPreferredSize(new Dimension(200,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(200,50));
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

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if(fenetrePrecedente.getTitle().equals("Modifier informations sur un compte")){
				if(arg0.getSource()==boutonOui){
					compte.setActif(false);
					DAOCompte.updateCompte(compte);
					new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				}
				else if (arg0.getSource()==boutonNon){
					new FenetreModifCompteAdmin(this.getCompte(),this.getAdministrateur());
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Suppression d'un vélo")){
				if(arg0.getSource()==boutonOui){
					Velo v = this.getAdministrateur().supprimerVelo(this.getVelo());
					DAOVelo.updateVelo(v);
					new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				}
				else if (arg0.getSource()==boutonNon){
					new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
				}
			}
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
			e.printStackTrace();
		} catch (ConnexionFermeeException e) {
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}