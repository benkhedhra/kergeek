package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreChangerMotDePasse;
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

import metier.Technicien;

/**
 * la classe {@link MenuPrincipalTech} hérite de {@link JFrame} et implémente l'interface {@link ActionListener}
 * <br>cette fenêtre apparaît lorsque le technicien vient de se connecter ou vient de cliquer sur un bouton "Retour au menu principal"
 * <br>elle propose au {@link Technicien} soit de se déconnecter, soit de cliquer sur un des 4 onglets suivants
 * <br>"Enregistrer un nouvel arrivage de vélos"
 * <br>"Retirer un vélo défectueux"
 * <br>"Gérer les demandes d'assignation"
 * <br>"Gérer les interventions"
 * <br>cette fenêtre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class MenuPrincipalTech extends JFrame implements ActionListener {

	/*
	 * liste des attributs privés de la fenêtre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JButton bouton1 = new JButton("<html> <center>Enregistrer<br>un nouvel arrivage de vélos</center></html>");
	private JButton bouton2 = new JButton("<html> <center>Retirer un vélo<br>défectueux<br>d'une station</center></html>");
	private JButton bouton3 = new JButton("<html> <center>Gérer les demandes<br>d'assignation</center></html>");
	private JButton bouton4 = new JButton("<html> <center>Gérer les<br>interventions</center></html>");
	private JButton boutonChangeMdp = new JButton("Changer le mot de passe");

	/**
	 * @return	le {@link MenuPrincipalTech#technicien} de la {@link MenuPrincipalTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link MenuPrincipalTech#technicien} de la {@link MenuPrincipalTech}
	 * @param tech
	 * le technicien connecté sur cette fenêtre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * constructeur du {@link MenuPrincipalTech}
	 * @param t : le technicien connecté sur la fenêtre
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public MenuPrincipalTech(Technicien t){

		this.setTechnicien(t);
		this.setContentPane(new PanneauTech());
		System.out.println("Affichage du menu principal du technicien");
		//Définit un titre pour votre fenêtre
		this.setTitle("Menu principal du technicien");
		//Définit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
		this.setSize(700, 500);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(UtilitaireIhm.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		bouton1.setPreferredSize(new Dimension(200,100));
		bouton1.setMaximumSize(new Dimension(200,100));
		bouton1.setFont(UtilitaireIhm.POLICE3);
		bouton1.addActionListener(this);
		center.add(bouton1);
		bouton2.setPreferredSize(new Dimension(200,100));
		bouton2.setMaximumSize(new Dimension(200,100));
		bouton2.setFont(UtilitaireIhm.POLICE3);
		bouton2.addActionListener(this);
		center.add(bouton2);
		bouton3.setPreferredSize(new Dimension(200,100));
		bouton3.setMaximumSize(new Dimension(200,100));
		bouton3.setFont(UtilitaireIhm.POLICE3);
		bouton3.addActionListener(this);
		center.add(bouton3);
		bouton4.setPreferredSize(new Dimension(200,100));
		bouton4.setMaximumSize(new Dimension(200,100));
		bouton4.setFont(UtilitaireIhm.POLICE3);
		bouton4.addActionListener(this);
		center.add(bouton4);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,50));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonChangeMdp.setPreferredSize(new Dimension(250,30));
		boutonChangeMdp.setMaximumSize(new Dimension(250,30));
		boutonChangeMdp.setFont(UtilitaireIhm.POLICE3);
		boutonChangeMdp.setForeground(Color.WHITE);
		boutonChangeMdp.setBackground(Color.BLUE);
		boutonChangeMdp.addActionListener(this);
		south.add(boutonChangeMdp);
		this.getContentPane().add(south, BorderLayout.SOUTH);
		this.setVisible(true);
	}


	/**
	 * cette méthode est exécutée si le {@link Technicien} a cliqué sur l'un des cinq boutons qui lui étaient proposés
	 * @see FenetreEnregistrerArrivageVelosTech#FenetreEnregistrerArrivageVelosTech(Technicien)
	 * @see FenetreRetirerVeloDefectueuxTech#FenetreRetirerVeloDefectueuxTech(Technicien)
	 * @see FenetreGererDemandesAssignationTech#FenetreGererDemandesAssignationTech(Technicien)
	 * @see FenetreGererInterventionsTech#FenetreGererInterventionsTech(Technicien)
	 * @see FenetreConfirmation#FenetreConfirmation(metier.Compte, JFrame)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try{
			if(arg0.getSource()==bouton1){
				new FenetreEnregistrerArrivageVelosTech(this.getTechnicien());
			}
			else if (arg0.getSource()==bouton2){
				new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
			}
			else if (arg0.getSource()==bouton3){
				new FenetreGererDemandesAssignationTech(this.getTechnicien());
			}
			else if (arg0.getSource()==bouton4){
				new FenetreGererInterventionsTech(this.getTechnicien());
			}
			else if (arg0.getSource()==boutonChangeMdp){
				new FenetreChangerMotDePasse(this.getTechnicien().getCompte());
			}
			else if (arg0.getSource()==boutonDeconnexion){
				new FenetreConfirmation(this.getTechnicien().getCompte(), this);
			}
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
	}
}