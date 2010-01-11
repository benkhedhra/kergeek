package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

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
import metier.Utilisateur;

/**
 * FenetreInfoCompteAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsqu'un Administrateur a effectué une recherche et a sélectionné un compte parmi les résultats de {@link FenetreResultatsRechercheCompteAdmin}
 * <br>elle peut donner lieu à deux situations différentes : l'affichage de statistiques sur un utilisateur ou la modification d'attributs sur un compte
 * @author KerGeek
 */
public class FenetreInfoCompteAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur administrateur;
	/**
	 * le compte dont on affiche les informations
	 */
	private Compte compte;

	/**
	 * 3 JLabel permettant d'afficher l'id de l'administrateur connecté, l'endroit où il se trouve dans l'application, et le message introduisant le contenu de la fenêtre
	 */
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Gérer les comptes > Afficher informations sur un compte");
	
	/**
	 * 4 JLabel permettant d'afficher les informations du compte (quel que soit le type de compte)
	 */
	private JLabel labelQualite = new JLabel("Qualité");
	private JLabel labelQualiteCompte = new JLabel("");
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private JLabel labelAdresseEMailCompte = new JLabel("");

	/**
	 * 8 autres JLabel pour afficher les 4 renseignements supplémentaires s'il s'agit d'un compte de type Utilisateur
	 */
	private JLabel labelNom = new JLabel("Nom");
	private JLabel labelNomCompte = new JLabel("");
	private JLabel labelPrenom = new JLabel("Prénom");
	private JLabel labelPrenomCompte = new JLabel("");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private JLabel labelAdressePostaleCompte = new JLabel("");
	private JLabel labelStatut = new JLabel("Statut du compte");
	private JLabel labelStatutCompte = new JLabel("");

	/**
	 * 3 JButton permettant à l'administrateur d'afficher des stats OU de modifier le compte, d'afficher les informations d'un autre compte via une nouvelle recherche, ou de retourner au menu principal
	 */
	private JButton boutonChoix = new JButton("");
	private JButton boutonAutreCompte = new JButton("Afficher informations sur un autre compte");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	//Accesseurs utiles
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

	/**
	 * constructeur de {FenetreInfoCompte}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 * @param c
	 * le compte dont on affiche les informations
	 * @param stat
	 * booléen valant true si l'administrateur est dans le contexte d'obtention de statistiques sur les emprunts effectués par un utilisateur (c est alors toujours un compte de type Utilisateur)
	 * <br>s'il vaut false, c'est qu'il est dans le contexte de la gestion de l'ensemble des comptes (Utilisateur ou non)
	 * @throws ConnexionFermeeException
	 */
	public FenetreInfoCompteAdmin(Administrateur a,Compte c,boolean stat) throws ConnexionFermeeException{

		System.out.println("Fenêtre pour afficher les informations sur un compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Informations sur un compte");
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
		this.setCompte(c);

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
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		System.out.println("compte sélectionné = "+c.getId());
		labelMsg.setText("Informations sur le compte "+c.getId());
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(400,350));
		centerWest.setMinimumSize(new Dimension(400,350));
		centerWest.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(100,30));
		labelQualite.setMaximumSize(new Dimension(100,30));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
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
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(100,30));
		labelAdresseEMail.setMaximumSize(new Dimension(100,30));
		panel3.add(labelAdresseEMail);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelAdresseEMailCompte.setText(c.getAdresseEmail());
		labelAdresseEMailCompte.setPreferredSize(new Dimension(250,30));
		labelAdresseEMailCompte.setMinimumSize(new Dimension(250,30));
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
			panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelNom.setPreferredSize(new Dimension(100,30));
			labelNom.setMaximumSize(new Dimension(100,30));
			panel5.add(labelNom);
			centerWest.add(panel5);	

			JPanel panel6 = new JPanel();
			panel6.setBackground(UtilitaireIhm.TRANSPARENCE);
			labelNomCompte.setText(u.getNom());
			labelNomCompte.setPreferredSize(new Dimension(250,30));
			labelNomCompte.setMinimumSize(new Dimension(250,30));
			panel6.add(labelNomCompte);
			centerWest.add(panel6);			

			JPanel panel7 = new JPanel();
			panel7.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelPrenom.setPreferredSize(new Dimension(100,30));
			labelPrenom.setMaximumSize(new Dimension(100,30));
			panel7.add(labelPrenom);
			centerWest.add(panel7);	

			JPanel panel8 = new JPanel();
			panel8.setBackground(UtilitaireIhm.TRANSPARENCE);
			labelPrenomCompte.setText(u.getPrenom());
			labelPrenomCompte.setPreferredSize(new Dimension(250,30));
			labelPrenomCompte.setMinimumSize(new Dimension(250,30));
			panel8.add(labelPrenomCompte);
			centerWest.add(panel8);	

			JPanel panel9 = new JPanel();
			panel9.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelAdressePostale.setPreferredSize(new Dimension(100,30));
			labelAdressePostale.setMaximumSize(new Dimension(100,30));
			panel9.add(labelAdressePostale);
			centerWest.add(panel9);	

			JPanel panel10 = new JPanel();
			panel10.setBackground(UtilitaireIhm.TRANSPARENCE);
			labelAdressePostaleCompte.setText(u.getAdressePostale());
			labelAdressePostaleCompte.setPreferredSize(new Dimension(250,30));
			labelAdressePostaleCompte.setMinimumSize(new Dimension(250,30));
			panel10.add(labelAdressePostaleCompte);
			centerWest.add(panel10);

			JPanel panel11 = new JPanel();
			panel11.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelStatut.setPreferredSize(new Dimension(100,30));
			labelStatut.setMaximumSize(new Dimension(100,30));
			panel11.add(labelStatut);
			centerWest.add(panel11);	

			JPanel panel12 = new JPanel();
			panel12.setBackground(UtilitaireIhm.TRANSPARENCE);
			String statut;
			if(u.isBloque()){statut="bloqué";}
			else{statut="non bloqué";}
			labelStatutCompte.setText(statut);
			labelStatutCompte.setPreferredSize(new Dimension(250,30));
			labelStatutCompte.setMinimumSize(new Dimension(250,30));
			panel12.add(labelStatutCompte);
			centerWest.add(panel12);
		}

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(200,350));

		if(!stat){
			boutonChoix.setText("<html><center>Modifier des<br>informations<br>sur ce compte</center></html>");
			}
		else{
			boutonChoix.setText("<html><center>Afficher les<br>statistiques<br>de cet utilisateur</center></html>");
		}
		boutonChoix.setPreferredSize(new Dimension(190,60));
		boutonChoix.setMaximumSize(new Dimension(190,60));
		boutonChoix.setBackground(Color.CYAN);
		boutonChoix.setFont(UtilitaireIhm.POLICE3);
		boutonChoix.addActionListener(this);
		centerEast.add(boutonChoix);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel13 = new JPanel();
		panel13.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonAutreCompte.setPreferredSize(new Dimension(250,40));
		boutonAutreCompte.setMaximumSize(new Dimension(250,40));
		boutonAutreCompte.setFont(UtilitaireIhm.POLICE3);
		boutonAutreCompte.setBackground(Color.GREEN);
		boutonAutreCompte.addActionListener(this);
		panel13.add(boutonAutreCompte);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel13.add(boutonRetour);
		south.add(panel13,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'Administrateur a cliqué sur l'un des boutons qui lui étaient proposés
	 * @see FenetreModifCompteAdmin#FenetreModifCompteAdmin(Compte, Administrateur)
	 * @see FenetreRechercherCompteAdmin#FenetreRechercherCompteAdmin(Administrateur, boolean)
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//il a cliqué sur boutonChoix qui correspond à "modifier ce compte"
		if(arg0.getSource()==boutonChoix && boutonChoix.getText().equals("<html><center>Modifier des<br>informations<br>sur ce compte</center></html>")){
			try {
				new FenetreModifCompteAdmin(compte,this.getAdministrateur());
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//il a cliqué sur boutonChoix qui correspond à "afficher stats sur cet utilisateur"
		else if(arg0.getSource()==boutonChoix && boutonChoix.getText().equals("<html><center>Afficher les<br>statistiques<br>de cet utilisateur</center></html>")){
			try {
				new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
			} 
			catch (SQLException e) {
				MsgBox.affMsg("SQLException " + e.getMessage());
			} 
			catch (ClassNotFoundException e) {
				MsgBox.affMsg("ClassNotFoundException " + e.getMessage());
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//il a cliqué sur "voir infos sur un autre compte" et on est dans le contexte de la gestion des comptes
		else if(arg0.getSource()==boutonAutreCompte && boutonChoix.getText().equals("<html><center>Modifier des<br>informations<br>sur ce compte</center></html>")){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),false);
		}
		//il a cliqué sur "voir infos sur un autre compte" et on est dans le contexte de l'obtention de stats emprunts utilisateurs
		else if(arg0.getSource()==boutonAutreCompte && boutonChoix.getText().equals("<html><center>Afficher les<br>statistiques<br>de cet utilisateur</center></html>")){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),true);
		}
		//il a cliqué sur "retour au menu principal"
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}