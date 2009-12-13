package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

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

public class FenetreDemandeConfirmationAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private Compte compte;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelConfirm = new JLabel("Voulez-vous confirmer la résiliation ? ");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (revenir à l'écran précédent)");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}
	
	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public FenetreDemandeConfirmationAdmin(Administrateur a, Compte c, FenetreModifCompteAdmin fenetrePrec){

		this.setContentPane(new PanneauAdmin());

		this.setAdministrateur(a);
		this.setCompte(c);
		
		this.setTitle("Fenêtre de demande de confirmation");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdminTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdminTech);
		this.add(north, BorderLayout.NORTH);

		labelConfirm.setPreferredSize(new Dimension(550,30));
		boutonOui.setPreferredSize(new Dimension(200,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(200,50));
		boutonNon.setBackground(Color.CYAN);

		boutonOui.addActionListener(this);
		boutonNon.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelConfirm);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonOui){
			compte.setActif(false);
			try {
				DAOCompte.updateCompte(compte);
			} 
			catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} 
			catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
			try {
				new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
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
		else if (arg0.getSource()==boutonNon){
			try {
				new FenetreModifCompteAdmin(compte,administrateur);
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
	}
}