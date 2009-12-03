package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAffichageResultats;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Utilisateur;

public class FenetreInfoCompteAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private Compte compte;
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("");
	private JLabel labelQualite = new JLabel("Qualité");
	private JLabel labelQualiteCompte = new JLabel("");
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private JLabel labelAdresseEMailCompte = new JLabel("");

	private JLabel labelNom = new JLabel("Nom");
	private JLabel labelNomCompte = new JLabel("");
	private JLabel labelPrenom = new JLabel("Prénom");
	private JLabel labelPrenomCompte = new JLabel("");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private JLabel labelAdressePostaleCompte = new JLabel("");
	private JLabel labelStatut = new JLabel("Statut du compte");
	private JLabel labelStatutCompte = new JLabel("");

	private JButton boutonChoix = new JButton("");
	private JButton boutonAutreCompte = new JButton("Afficher informations sur un autre compte");
	private JButton boutonRetour = new JButton("Retour au menu principal");


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

	public FenetreInfoCompteAdmin(Administrateur a,Compte c,boolean stat){

		System.out.println("Fenêtre pour afficher les informations sur un compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Informations sur un compte");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);
		this.setCompte(c);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		labelMsg.setText("Informations sur le compte "+c.getId());
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(500,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerWest.setLayout(new GridLayout(6,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(150,30));
		labelQualite.setMaximumSize(new Dimension(150,30));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		String qualiteCompte="";
		if(c.getType()==Compte.TYPE_UTILISATEUR){qualiteCompte="utilisateur de Bélo Breizh";}
		else if(c.getType()==Compte.TYPE_ADMINISTRATEUR){qualiteCompte="administrateur Bélo Breizh";}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){qualiteCompte="technicien de Bélo Breizh";}
		labelQualiteCompte.setText(qualiteCompte);
		labelQualiteCompte.setPreferredSize(new Dimension(300,30));
		labelQualiteCompte.setMinimumSize(new Dimension(300,30));
		panel2.add(labelQualiteCompte);
		centerWest.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(150,30));
		labelAdresseEMail.setMaximumSize(new Dimension(150,30));
		panel3.add(labelAdresseEMail);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAdresseEMailCompte.setText(c.getAdresseEmail());
		labelAdresseEMailCompte.setPreferredSize(new Dimension(300,30));
		labelAdresseEMailCompte.setMinimumSize(new Dimension(300,30));
		panel4.add(labelAdresseEMailCompte);
		centerWest.add(panel4);	

		if (c.getType()==Compte.TYPE_UTILISATEUR){	
			Utilisateur u = new Utilisateur();
			try {
				u = DAOUtilisateur.getUtilisateurById(c.getId());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
			JPanel panel5 = new JPanel();
			panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			labelNom.setPreferredSize(new Dimension(150,30));
			labelNom.setMaximumSize(new Dimension(150,30));
			panel5.add(labelNom);
			centerWest.add(panel5);	

			JPanel panel6 = new JPanel();
			panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			labelNomCompte.setText(u.getNom());
			labelNomCompte.setPreferredSize(new Dimension(300,30));
			labelNomCompte.setMinimumSize(new Dimension(300,30));
			panel6.add(labelNomCompte);
			centerWest.add(panel6);			

			JPanel panel7 = new JPanel();
			panel7.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			labelPrenom.setPreferredSize(new Dimension(150,30));
			labelPrenom.setMaximumSize(new Dimension(150,30));
			panel7.add(labelPrenom);
			centerWest.add(panel7);	

			JPanel panel8 = new JPanel();
			panel8.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			System.out.println("prénom = "+u.getPrenom());
			labelPrenomCompte.setText(u.getPrenom());
			labelPrenomCompte.setPreferredSize(new Dimension(300,30));
			labelPrenomCompte.setMinimumSize(new Dimension(300,30));
			panel8.add(labelPrenomCompte);
			centerWest.add(panel8);	

			JPanel panel9 = new JPanel();
			panel9.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			labelAdressePostale.setPreferredSize(new Dimension(150,30));
			labelAdressePostale.setMaximumSize(new Dimension(150,30));
			panel9.add(labelAdressePostale);
			centerWest.add(panel9);	

			JPanel panel10 = new JPanel();
			panel10.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			labelAdressePostaleCompte.setText(u.getAdressePostale());
			labelAdressePostaleCompte.setPreferredSize(new Dimension(300,30));
			labelAdressePostaleCompte.setMinimumSize(new Dimension(300,30));
			panel10.add(labelAdressePostaleCompte);
			centerWest.add(panel10);

			JPanel panel11 = new JPanel();
			panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			labelStatut.setPreferredSize(new Dimension(150,30));
			labelStatut.setMaximumSize(new Dimension(150,30));
			panel11.add(labelStatut);
			centerWest.add(panel11);	

			JPanel panel12 = new JPanel();
			panel12.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			String statut;
			if(u.isBloque()){statut="bloqué";}
			else{statut="non bloqué";}
			labelStatutCompte.setText(statut);
			labelStatutCompte.setPreferredSize(new Dimension(300,30));
			labelStatutCompte.setMinimumSize(new Dimension(300,30));
			panel12.add(labelStatutCompte);
			centerWest.add(panel12);
		}

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(200,350));

		if(!stat){boutonChoix.setText("Modifier des informations sur ce compte");}
		else{boutonChoix.setText("Afficher statistiques sur ce compte");
		}
		boutonChoix.setPreferredSize(new Dimension(170,60));
		boutonChoix.setMaximumSize(new Dimension(170,60));
		boutonChoix.setBackground(Color.CYAN);
		boutonChoix.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonChoix.addActionListener(this);
		centerEast.add(boutonChoix);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel13 = new JPanel();
		panel13.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonAutreCompte.setPreferredSize(new Dimension(250,40));
		boutonAutreCompte.setMaximumSize(new Dimension(250,40));
		boutonAutreCompte.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonAutreCompte.setBackground(Color.GREEN);
		boutonAutreCompte.addActionListener(this);
		panel13.add(boutonAutreCompte);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel13.add(boutonRetour);
		south.add(panel13,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonChoix && boutonChoix.getText().equals("Modifier des informations sur ce compte")){
			new FenetreModifCompteAdmin(compte,this.getAdministrateur());
		}
		else if(arg0.getSource()==boutonChoix && boutonChoix.getText().equals("Afficher statistiques sur ce compte")){
			try {
				new FenetreAffichageResultats(this.getAdministrateur().getCompte(),this);
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
		}
		else if(arg0.getSource()==boutonAutreCompte){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),false);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}		

}
