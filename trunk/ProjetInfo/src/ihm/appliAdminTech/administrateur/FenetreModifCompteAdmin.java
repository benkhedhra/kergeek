package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class FenetreModifCompteAdmin extends JFrame implements ActionListener {

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
	private JTextField adresseEMailCompte = new JTextField("");

	private JLabel labelNom = new JLabel("Nom");
	private JTextField nomCompte = new JTextField("");
	private JLabel labelPrenom = new JLabel("Prénom");
	private JTextField prenomCompte = new JTextField("");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private JTextField adressePostaleCompte = new JTextField("");
	private JLabel labelStatut = new JLabel("Statut du compte");
	private JComboBox statutCompte = new JComboBox();
	private boolean bloqueEntre;

	private JButton boutonValider = new JButton("Valider les modifications");
	private JButton boutonResilier = new JButton("Résilier ce compte");
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

	public FenetreModifCompteAdmin(Compte c,Administrateur a){

		System.out.println("Fenêtre pour modifier des informations sur un compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Modifier informations sur un compte");
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

		compte=c;

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setMinimumSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		labelMsg.setText("Informations sur le compte "+c.getId());
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(400,350));
		centerWest.setMinimumSize(new Dimension(400,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(100,30));
		labelQualite.setMinimumSize(new Dimension(100,30));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		String qualiteCompte="";
		if(c.getType()==Compte.TYPE_UTILISATEUR){qualiteCompte="utilisateur de Bélo Breizh";}
		else if(c.getType()==Compte.TYPE_ADMINISTRATEUR){qualiteCompte="administrateur Bélo Breizh";}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){qualiteCompte="technicien de Bélo Breizh";}
		labelQualiteCompte.setText(qualiteCompte);
		labelQualiteCompte.setPreferredSize(new Dimension(250,30));
		labelQualiteCompte.setMinimumSize(new Dimension(250,30));
		panel2.add(labelQualiteCompte);
		centerWest.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(100,30));
		labelAdresseEMail.setMinimumSize(new Dimension(100,30));
		panel3.add(labelAdresseEMail);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		adresseEMailCompte.setText(c.getAdresseEmail());
		adresseEMailCompte.setPreferredSize(new Dimension(250,30));
		adresseEMailCompte.setMinimumSize(new Dimension(250,30));
		panel4.add(adresseEMailCompte);
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
			labelNom.setPreferredSize(new Dimension(100,30));
			labelNom.setMinimumSize(new Dimension(100,30));
			panel5.add(labelNom);
			centerWest.add(panel5);	

			JPanel panel6 = new JPanel();
			panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			nomCompte.setText(u.getNom());
			nomCompte.setPreferredSize(new Dimension(250,30));
			nomCompte.setMinimumSize(new Dimension(250,30));
			panel6.add(nomCompte);
			centerWest.add(panel6);			

			JPanel panel7 = new JPanel();
			panel7.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			prenomCompte.setText(u.getPrenom());
			labelPrenom.setPreferredSize(new Dimension(100,30));
			labelPrenom.setMinimumSize(new Dimension(100,30));
			panel7.add(labelPrenom);
			centerWest.add(panel7);	

			JPanel panel8 = new JPanel();
			panel8.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			prenomCompte.setPreferredSize(new Dimension(250,30));
			prenomCompte.setMinimumSize(new Dimension(250,30));
			panel8.add(prenomCompte);
			centerWest.add(panel8);	

			JPanel panel9 = new JPanel();
			panel9.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			labelAdressePostale.setPreferredSize(new Dimension(100,30));
			labelAdressePostale.setMinimumSize(new Dimension(100,30));
			panel9.add(labelAdressePostale);
			centerWest.add(panel9);	

			JPanel panel10 = new JPanel();
			panel10.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			adressePostaleCompte.setText(u.getAdressePostale());
			adressePostaleCompte.setPreferredSize(new Dimension(250,30));
			adressePostaleCompte.setMinimumSize(new Dimension(250,30));
			panel10.add(adressePostaleCompte);
			centerWest.add(panel10);

			JPanel panel11 = new JPanel();
			panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			labelStatut.setPreferredSize(new Dimension(100,30));
			labelStatut.setMinimumSize(new Dimension(100,30));
			panel11.add(labelStatut);
			centerWest.add(panel11);	

			JPanel panel12 = new JPanel();
			panel12.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			String statut1;
			String statut2;
			if(u.isBloque()){
				statut1="bloqué";
				statut2="non bloqué";
			}
			else{
				statut1="non bloqué";
				statut2="bloqué";
			}

			String[] statuts = new String[2];
			statuts[0] = statut1;
			statuts[1] = statut2;
			DefaultComboBoxModel model = new DefaultComboBoxModel(statuts);
			statutCompte = new JComboBox(model);
			statutCompte.setPreferredSize(new Dimension(250,30));
			statutCompte.setMinimumSize(new Dimension(250,30));
			statutCompte.setFont(FenetreAuthentificationUtil.POLICE3);
			statutCompte.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String statutEntre = (String) (o);
					if(statutEntre.equals("bloqué")){bloqueEntre=true;}
					else if (statutEntre.equals("non bloqué")){bloqueEntre=false;}
				}

			});
			panel12.add(statutCompte);
			centerWest.add(panel12);
		}

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(300,350));
		centerEast.setMinimumSize(new Dimension(300,350));


		boutonValider.setPreferredSize(new Dimension(250,40));
		boutonValider.setMinimumSize(new Dimension(250,40));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		centerEast.add(boutonValider);
		boutonResilier.setPreferredSize(new Dimension(250,40));
		boutonResilier.setMinimumSize(new Dimension(250,40));
		boutonResilier.setBackground(Color.CYAN);
		boutonResilier.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonResilier.addActionListener(this);
		centerEast.add(boutonResilier);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMinimumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonValider){
			try {
				if (compte.getType()==Compte.TYPE_UTILISATEUR){
					boolean champsOk = UtilitaireIhm.verifieChampsCreationUtil(Compte.TYPE_UTILISATEUR, adresseEMailCompte.getText(), nomCompte.getText(), prenomCompte.getText(), adressePostaleCompte.getText());
					if(champsOk){
						compte.setAdresseEmail(adresseEMailCompte.getText());
						DAOCompte.updateCompte(compte);
						Utilisateur u = DAOUtilisateur.getUtilisateurById(compte.getId());
						u.setCompte(compte);
						u.setNom(nomCompte.getText());
						u.setPrenom(prenomCompte.getText());
						u.setAdressePostale(adressePostaleCompte.getText());
						u.setBloque(bloqueEntre);
						DAOUtilisateur.updateUtilisateur(u);
						new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
					}
					else{
						MsgBox.affMsg("L'un des champs au moins est incorrect");
						new FenetreModifCompteAdmin(this.getCompte(),this.getAdministrateur());
					}
				}
				if (compte.getType()==Compte.TYPE_ADMINISTRATEUR){
					boolean champsOk = UtilitaireIhm.verifieChampsCreationAdmin(Compte.TYPE_ADMINISTRATEUR, adresseEMailCompte.getText());
					if(champsOk){
						compte.setAdresseEmail(adresseEMailCompte.getText());
						DAOCompte.updateCompte(compte);
						Administrateur a = DAOAdministrateur.getAdministrateurById(compte.getId());
						a.setCompte(compte);
						DAOAdministrateur.updateAdministrateur(a);
						new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
					}
					else{
						MsgBox.affMsg("L'un des champs au moins est incorrect");
						new FenetreModifCompteAdmin(this.getCompte(),this.getAdministrateur());
					}
				}
				if (compte.getType()==Compte.TYPE_TECHNICIEN){
					boolean champsOk = UtilitaireIhm.verifieChampsCreationAdmin(Compte.TYPE_TECHNICIEN, adresseEMailCompte.getText());
					if(champsOk){
						compte.setAdresseEmail(adresseEMailCompte.getText());
						DAOCompte.updateCompte(compte);
						Technicien t = DAOTechnicien.getTechnicienById(compte.getId());
						t.setCompte(compte);
						DAOTechnicien.updateTechnicien(t);
						new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
					}
					else{
						MsgBox.affMsg("L'un des champs entrés au moins est incorrect");
						new FenetreModifCompteAdmin(this.getCompte(),this.getAdministrateur());
					}
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
		}
		else if(arg0.getSource()==boutonResilier){
			new FenetreDemandeConfirmationAdmin(this.getAdministrateur(),compte,this);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}		

}
