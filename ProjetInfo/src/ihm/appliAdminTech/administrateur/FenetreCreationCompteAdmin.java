package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;
import metier.Utilisateur;

public class FenetreCreationCompteAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("Entrer les données du titulaire du nouveau compte");
	private JLabel labelQualite = new JLabel("Qualité");
	private int typeEntre;
	private JLabel labelNom = new JLabel("Nom");
	private JTextField nomARemplir = new JTextField("");
	private JLabel labelPrenom = new JLabel("Prénom");
	private JTextField prenomARemplir = new JTextField("");
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private JTextField adresseEMailARemplir = new JTextField("");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private JTextField adressePostaleARemplir = new JTextField("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}


	public FenetreCreationCompteAdmin(Administrateur a){

		System.out.println("Fenêtre pour créer un nouveau compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Création d'un nouveau compte");
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
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		String[] types = new String[4];
		//idée : getAllStations : ici lignes suivantes pour tester provisoirement
		types[0] = "Sélection à faire";
		types[1] = "utilisateur";
		types[2] = "administrateur";
		types[3] = "technicien";
		DefaultComboBoxModel model = new DefaultComboBoxModel(types);
		JComboBox qualiteARemplir = new JComboBox(model);
		qualiteARemplir.setPreferredSize(new Dimension(150,30));
		qualiteARemplir.setMaximumSize(new Dimension(150,30));
		qualiteARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		qualiteARemplir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				String qualiteEntree = (String)o;

				if(qualiteEntree.equals("utilisateur")){typeEntre=Compte.TYPE_UTILISATEUR;}
				if(qualiteEntree.equals("administrateur")){typeEntre=Compte.TYPE_ADMINISTRATEUR;}
				if(qualiteEntree.equals("technicien")){typeEntre=Compte.TYPE_TECHNICIEN;}

				modifieSiUtilisateur(typeEntre);
			}

		});

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerWest.setLayout(new GridLayout(5,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(150,30));
		labelQualite.setMaximumSize(new Dimension(150,30));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		panel2.add(qualiteARemplir);
		centerWest.add(panel2);	
	
		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(150,30));
		labelAdresseEMail.setMaximumSize(new Dimension(150,30));
		panel3.add(labelAdresseEMail);
		centerWest.add(panel3);	
	
		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		adresseEMailARemplir.setPreferredSize(new Dimension(150,30));
		adresseEMailARemplir.setMaximumSize(new Dimension(150,30));
		panel4.add(adresseEMailARemplir);
		centerWest.add(panel4);	
		
		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNom.setPreferredSize(new Dimension(150,30));
		labelNom.setMaximumSize(new Dimension(150,30));
		panel5.add(labelNom);
		centerWest.add(panel5);	
		
		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		nomARemplir.setPreferredSize(new Dimension(150,30));
		nomARemplir.setMaximumSize(new Dimension(150,30));
		panel6.add(nomARemplir);
		centerWest.add(panel6);			
		
		JPanel panel7 = new JPanel();
		panel7.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelPrenom.setPreferredSize(new Dimension(150,30));
		labelPrenom.setMaximumSize(new Dimension(150,30));
		panel7.add(labelPrenom);
		centerWest.add(panel7);	
		
		JPanel panel8 = new JPanel();
		panel8.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		prenomARemplir.setPreferredSize(new Dimension(150,30));
		prenomARemplir.setMaximumSize(new Dimension(150,30));
		panel8.add(prenomARemplir);
		centerWest.add(panel8);	
		
		JPanel panel9 = new JPanel();
		panel9.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAdressePostale.setPreferredSize(new Dimension(150,30));
		labelAdressePostale.setMaximumSize(new Dimension(150,30));
		panel9.add(labelAdressePostale);
		centerWest.add(panel9);	
	
		JPanel panel10 = new JPanel();
		panel10.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		adressePostaleARemplir.setPreferredSize(new Dimension(150,30));
		adressePostaleARemplir.setMaximumSize(new Dimension(150,30));
		panel10.add(adressePostaleARemplir);
		centerWest.add(panel10);	

		center.add(centerWest,BorderLayout.WEST);
		
		JPanel centerEast = new JPanel();
		centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(200,350));
		boutonValider.setPreferredSize(new Dimension(80,40));
		boutonValider.setMaximumSize(new Dimension(80,40));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		centerEast.add(boutonValider);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());
		
		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void modifieSiUtilisateur(int type){
		if(type!=Compte.TYPE_UTILISATEUR){
			labelNom.setForeground(Color.GRAY);
			nomARemplir.setEnabled(true);
			labelPrenom.setForeground(Color.GRAY);
			prenomARemplir.setEnabled(true);
			labelAdressePostale.setForeground(Color.GRAY);
			adressePostaleARemplir.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonValider){
			Compte compte = new Compte("",typeEntre,adresseEMailARemplir.getText());
			// PROBLEME AVEC LES CONSTRUCTEURS DE COMPTE : paramètres bizarres
			try {
				DAOCompte.createCompte(compte);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// si c'est un compte utilisateur
			if(compte.getType()==Compte.TYPE_UTILISATEUR){
				Utilisateur utilisateur = new Utilisateur (compte,nomARemplir.getText(),prenomARemplir.getText(),adressePostaleARemplir.getText());
				try {
					DAOUtilisateur.createUtilisateur(utilisateur);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// si c'est un compte administrateur
			else if(compte.getType()==Compte.TYPE_ADMINISTRATEUR){
				Administrateur administrateur = new Administrateur (compte);
				try {
					DAOAdministrateur.createAdministrateur(administrateur);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// si c'est un compte technicien
			else if(compte.getType()==Compte.TYPE_TECHNICIEN){
				Technicien technicien = new Technicien (compte);
				try {
					DAOTechnicien.createTechnicien(technicien);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
		

	}		

}
