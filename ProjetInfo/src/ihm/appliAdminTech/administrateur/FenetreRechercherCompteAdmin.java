package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;

/**
 * FenetreRechercherCompteAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient dans 2 contextes différents
 * <br>un Administrateur a cliqué sur "Afficher informations sur un compte" dans son {@link MenuGererComptesAdmin}
 * <br>un Administrateur a cliqué sur "Afficher statistiques sur un utilisateur" dans son {@link MenuDemanderStatsAdmin}
 * <br>il peut aussi résilier ce compte
 * @author KerGeek
 */
public class FenetreRechercherCompteAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur administrateur;
	/**
	 * booléen indiquant si l'on se trouve dans un contexte de recherche de statistiques sur les emprunts d'un Utilisateur ou non
	 */
	private boolean stat;
	
	/**
	 * 3 JLabel permettant d'afficher l'id de l'administrateur connecté, l'endroit où il se trouve dans l'application, et le message introduisant le contenu de la fenêtre
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("");
	private JLabel labelMsg = new JLabel("Rechercher par : ");
	
	/**
	 * JLabel et TextFieldLimite indiquant les paramètres à remplir (ou non) pour la recherche de compte
	 */
	private JLabel labelQualite = new JLabel("Qualité");

	private JLabel labelId = new JLabel("Identifiant");
	private TextFieldLimite idARemplir = new TextFieldLimite(4,"");

	private JLabel labelNom = new JLabel("Nom");
	private TextFieldLimite nomARemplir = new TextFieldLimite(20,"");

	private JLabel labelPrenom = new JLabel("Prénom");
	private TextFieldLimite prenomARemplir = new TextFieldLimite(20,"");

	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private TextFieldLimite adresseEMailARemplir = new TextFieldLimite(50,"");

	/**
	 * 5 paramètres indiquant les champs remplis sur lesquels vont s'effectuer la recherche
	 */
	private int typeEntre;
	private String idEntre;
	private String nomEntre;
	private String prenomEntre;
	private String adresseEMailEntree;

	/**
	 * 2 JButton permettant à l'Administrateur de valider pour lancer la recherche ou de revenir à son menu principal
	 */
	private JButton boutonValider = new JButton("Lancer la recherche");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	
	//Accesseurs utiles
	
	/*
	 * attribut administrateur 
	 */
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	/*
	 * attribut typeEntre : le type de compte sélectionné pour la recherche
	 */
	public int getTypeEntre() {
		return typeEntre;
	}

	public void setTypeEntre(int typeEntre) {
		this.typeEntre = typeEntre;
	}

	/*
	 * attribut idEntre : l'identifiant du compte sélectionné pour la recherche
	 */
	public String getIdEntre() {
		return idEntre;
	}

	public void setIdEntre(String idEntre) {
		this.idEntre = idEntre;
	}

	/*
	 * attribut nomEntre : le nom (pour un utilisateur) sélectionné pour la recherche
	 */
	public String getNomEntre() {
		return nomEntre;
	}

	public void setNomEntre(String nomEntre) {
		this.nomEntre = nomEntre;
	}

	/*
	 * attribut prenomEntre : le prénom (pour un utilisateur) sélectionné pour la recherche
	 */
	public String getPrenomEntre() {
		return prenomEntre;
	}

	public void setPrenomEntre(String prenomEntre) {
		this.prenomEntre = prenomEntre;
	}

	/*
	 * attribut adresseEMailEntree : l'adresse e-mail sélectionnée pour la recherche
	 */
	public String getAdresseEMailEntree() {
		return adresseEMailEntree;
	}

	public void setAdresseEMailEntree(String adresseEMailEntree) {
		this.adresseEMailEntree = adresseEMailEntree;
	}


	/**
	 * constructeur de {@link FenetreRechercherCompteAdmin}
	 * @param a
	 * l'administrateur connecté sur cette fenêtre
	 * @param stat
	 * le booléen valant true si l'ont se trouve dans un contexte de recherche d'un utilisateur pour obtenir des statistiques sur ses emprunts
	 */
	public FenetreRechercherCompteAdmin(Administrateur a,boolean stat){

		System.out.println("Fenêtre pour rechercher un compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Rechercher un compte");
		//Définit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);
		this.stat=stat;
		
		if(stat){
			labelChemin.setText("Menu principal > Demander des statistiques > Statistiques sur utilisateurs");
		}
		else{
			labelChemin.setText("Menu principal > Gérer les comptes > Afficher informations sur un compte");
		}

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		labelAdmin.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,800));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);


		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(500,40));
		labelQualite.setMaximumSize(new Dimension(500,40));
		panel1.add(labelQualite);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(950,800));
		centerWest.setBackground(UtilitaireIhm.TRANSPARENCE);

		if(!stat){

			centerWest.add(panel1);

			String[] types = new String[4];
			types[0] = "Sélection à faire";
			types[1] = "utilisateur";
			types[2] = "administrateur";
			types[3] = "technicien";
			DefaultComboBoxModel model = new DefaultComboBoxModel(types);
			JComboBox qualiteARemplir = new JComboBox(model);
			qualiteARemplir.setPreferredSize(new Dimension(300,40));
			qualiteARemplir.setMaximumSize(new Dimension(300,40));
			qualiteARemplir.setFont(UtilitaireIhm.POLICE3);
			qualiteARemplir.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String qualiteEntree = (String)o;
					if(qualiteEntree.equals(null) || qualiteEntree.equals("Sélection à faire")){
						typeEntre=0;
					}
					else{
						if(qualiteEntree.equals("utilisateur")){typeEntre=Compte.TYPE_UTILISATEUR;}
						else if(qualiteEntree.equals("administrateur")){typeEntre=Compte.TYPE_ADMINISTRATEUR;}
						else if(qualiteEntree.equals("technicien")){typeEntre=Compte.TYPE_TECHNICIEN;}
					}
					modifieSiPasUtilisateur(typeEntre);
					repaint();
				}
			});

			panel2.add(qualiteARemplir);
			centerWest.add(panel2);

			JPanel panel3 = new JPanel();
			panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelId.setPreferredSize(new Dimension(500,40));
			labelId.setMaximumSize(new Dimension(500,40));
			panel3.add(labelId);
			centerWest.add(panel3);	

			JPanel panel4 = new JPanel();
			panel4.setBackground(UtilitaireIhm.TRANSPARENCE);	
			idARemplir.setPreferredSize(new Dimension(300,40));
			idARemplir.setMaximumSize(new Dimension(300,40));
			panel4.add(idARemplir);
			centerWest.add(panel4);	

			JPanel panel5 = new JPanel();
			panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelAdresseEMail.setPreferredSize(new Dimension(500,40));
			labelAdresseEMail.setMaximumSize(new Dimension(500,40));
			panel5.add(labelAdresseEMail);
			centerWest.add(panel5);	

			JPanel panel6 = new JPanel();
			panel6.setBackground(UtilitaireIhm.TRANSPARENCE);	
			adresseEMailARemplir.setPreferredSize(new Dimension(300,40));
			adresseEMailARemplir.setMaximumSize(new Dimension(300,40));
			panel6.add(adresseEMailARemplir);
			centerWest.add(panel6);
			
			JPanel panel7 = new JPanel();
			panel7.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelNom.setPreferredSize(new Dimension(500,40));
			labelNom.setMaximumSize(new Dimension(500,40));
			panel7.add(labelNom);
			centerWest.add(panel7);

			JPanel panel8 = new JPanel();
			panel8.setBackground(UtilitaireIhm.TRANSPARENCE);	
			nomARemplir.setPreferredSize(new Dimension(300,40));
			nomARemplir.setMaximumSize(new Dimension(300,40));
			panel8.add(nomARemplir);
			centerWest.add(panel8);	

			JPanel panel9 = new JPanel();
			panel9.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelPrenom.setPreferredSize(new Dimension(500,40));
			labelPrenom.setMaximumSize(new Dimension(500,40));
			panel9.add(labelPrenom);
			centerWest.add(panel9);

			JPanel panel10 = new JPanel();
			panel10.setBackground(UtilitaireIhm.TRANSPARENCE);	
			prenomARemplir.setPreferredSize(new Dimension(300,40));
			prenomARemplir.setMaximumSize(new Dimension(300,40));
			panel10.add(prenomARemplir);
			centerWest.add(panel10);
		}

		else{
			typeEntre=Compte.TYPE_UTILISATEUR;

			JLabel labelUtil = new JLabel ("utilisateur");
			panel2.add(labelUtil);

			JPanel panel3 = new JPanel();
			panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelId.setPreferredSize(new Dimension(500,40));
			labelId.setMaximumSize(new Dimension(500,40));
			panel3.add(labelId);
			centerWest.add(panel3);	

			JPanel panel4 = new JPanel();
			panel4.setBackground(UtilitaireIhm.TRANSPARENCE);	
			idARemplir.setPreferredSize(new Dimension(300,40));
			idARemplir.setMaximumSize(new Dimension(300,40));
			panel4.add(idARemplir);
			centerWest.add(panel4);	

			JPanel panel5 = new JPanel();
			panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelAdresseEMail.setPreferredSize(new Dimension(500,40));
			labelAdresseEMail.setMaximumSize(new Dimension(500,40));
			panel5.add(labelAdresseEMail);
			centerWest.add(panel5);	

			JPanel panel6 = new JPanel();
			panel6.setBackground(UtilitaireIhm.TRANSPARENCE);	
			adresseEMailARemplir.setPreferredSize(new Dimension(300,40));
			adresseEMailARemplir.setMaximumSize(new Dimension(300,40));
			panel6.add(adresseEMailARemplir);
			centerWest.add(panel6);
			
			JPanel panel7 = new JPanel();
			panel7.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelNom.setPreferredSize(new Dimension(500,40));
			labelNom.setMaximumSize(new Dimension(500,40));
			panel7.add(labelNom);
			centerWest.add(panel7);

			JPanel panel8 = new JPanel();
			panel8.setBackground(UtilitaireIhm.TRANSPARENCE);	
			nomARemplir.setPreferredSize(new Dimension(300,40));
			nomARemplir.setMaximumSize(new Dimension(300,40));
			panel8.add(nomARemplir);
			centerWest.add(panel8);	

			JPanel panel9 = new JPanel();
			panel9.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelPrenom.setPreferredSize(new Dimension(500,40));
			labelPrenom.setMaximumSize(new Dimension(500,40));
			panel9.add(labelPrenom);
			centerWest.add(panel9);

			JPanel panel10 = new JPanel();
			panel10.setBackground(UtilitaireIhm.TRANSPARENCE);	
			prenomARemplir.setPreferredSize(new Dimension(300,40));
			prenomARemplir.setMaximumSize(new Dimension(300,40));
			panel10.add(prenomARemplir);
			centerWest.add(panel10);
		}

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(250,800));
		boutonValider.setPreferredSize(new Dimension(200,50));
		boutonValider.setMaximumSize(new Dimension(200,50));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		centerEast.add(boutonValider);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);

	}

	/**
	 * cette méthode rend invisible un certain nombre d'élements graphiques impropres à la recherche d'un compte autre qu'un compte de type Utilisateur
	 * @param typeEntre
	 * le type de compte en fonction duquel il faut dessiner le reste de la fenêtre
	 */
	private void modifieSiPasUtilisateur(int typeEntre){
		if(typeEntre!=Compte.TYPE_UTILISATEUR){
			labelNom.setVisible(false);
			nomARemplir.setVisible(false);
			labelPrenom.setVisible(false);
			prenomARemplir.setVisible(false);
		}
	}

	/**
	 * méthode exécutée quand l'Administrateur a cliqué sur l'un des 2 boutons qui lui étaient proposés
	 * il peut n'avoir rempli aucun champ pour sa recherche
	 * @param arg0 
	 * @see FenetreResultatsRechercheCompteAdmin#FenetreResultatsRechercheCompteAdmin(Administrateur, FenetreRechercherCompteAdmin, boolean)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqué sur "Lancer la recherche"
		if(arg0.getSource()==boutonValider){
			this.setTypeEntre(typeEntre);
			//s'il n'a rien entré pour le type alors typeEntre vaudra null, ou 0 (pour un entier)
			if(!idARemplir.getText().equals("")){
				this.setIdEntre(idARemplir.getText());
			}
			if(!nomARemplir.getText().equals("")){
				this.setNomEntre(nomARemplir.getText());
			}
			if(!prenomARemplir.getText().equals("")){
				this.setPrenomEntre(prenomARemplir.getText());
			}
			if(!adresseEMailARemplir.getText().equals("")){
				this.setAdresseEMailEntree(adresseEMailARemplir.getText());
			}
			try {
				new FenetreResultatsRechercheCompteAdmin(this.getAdministrateur(),this,stat);
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//s'il a cliqué sur "Retourner au menu principal"
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}		
}