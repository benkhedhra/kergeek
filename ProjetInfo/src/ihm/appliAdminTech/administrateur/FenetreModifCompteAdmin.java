package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;
import metier.Utilisateur;

/**
 * FenetreModifCompteAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsqu'un Administrateur a cliqué sur "Modifier des informations" dans une {@link FenetreInfoCompteAdmin}
 * <br>l'administrateur peut modifier tous les champs du compte excepté son identifiant et son type
 * <br>il peut aussi résilier ce compte
 * @author KerGeek
 */
public class FenetreModifCompteAdmin extends JFrame implements ActionListener {

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
	 * 2 JLabel permettant d'afficher l'id de l'administrateur connecté et le message introduisant le contenu de la fenêtre
	 */
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("");

	/**
	 * 2 JLabel et 2 TextFieldLimite permettant d'afficher les informations du compte et de les modifier(quel que soit le type de compte)
	 */
	private JLabel labelQualite = new JLabel("Qualité");
	private JLabel labelQualiteCompte = new JLabel("");
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private TextFieldLimite adresseEMailCompte = new TextFieldLimite(250,"");

	/**
	 * 4 autres JLabel, et 3 TextFieldLimite et 1 JComboBox pour afficher les 4 renseignements supplémentaires s'il s'agit d'un compte de type Utilisateur
	 */
	private JLabel labelNom = new JLabel("Nom");
	private TextFieldLimite nomCompte = new TextFieldLimite(20,"");
	private JLabel labelPrenom = new JLabel("Prénom");
	private TextFieldLimite prenomCompte = new TextFieldLimite(20,"");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private TextFieldLimite adressePostaleCompte = new TextFieldLimite(250,"");
	private JLabel labelStatut = new JLabel("Statut du compte");
	private JComboBox statutCompte = new JComboBox();

	/**
	 * booléen correspondant au statut entré par l'administrateur (bloqué ou non)
	 */
	private boolean bloqueEntre;

	/**
	 * 3 JButton permettant à l'administrateur de valider les modifications, de résilier le compte, ou de retourner à son menu principal
	 */
	private JButton boutonValider = new JButton("Valider les modifications");
	private JButton boutonResilier = new JButton("Résilier ce compte");
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
	 * constructeur de {@link FenetreModifCompteAdmin}
	 * @param c
	 * le compte en train d'être modifié
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 * @throws ConnexionFermeeException
	 */
	public FenetreModifCompteAdmin(Compte c,Administrateur a) throws ConnexionFermeeException{

		System.out.println("Fenêtre pour modifier des informations sur un compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Modifier informations sur un compte");
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

		//on donne à la fenêtre les attributs administrateur et compte à partir des paramètres du constructeur
		this.setAdministrateur(a);
		this.setCompte(c);
		
		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setMinimumSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
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
		labelQualite.setMinimumSize(new Dimension(100,30));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
		String qualiteCompte="";
		if(c.getType()==Compte.TYPE_UTILISATEUR){
			qualiteCompte="utilisateur de Bélo Breizh";
		}
		else if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			qualiteCompte="administrateur Bélo Breizh";
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			qualiteCompte="technicien de Bélo Breizh";
		}
		labelQualiteCompte.setText(qualiteCompte);
		labelQualiteCompte.setPreferredSize(new Dimension(250,30));
		labelQualiteCompte.setMinimumSize(new Dimension(250,30));
		panel2.add(labelQualiteCompte);
		centerWest.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(100,30));
		labelAdresseEMail.setMinimumSize(new Dimension(100,30));
		panel3.add(labelAdresseEMail);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);
		adresseEMailCompte.setText(c.getAdresseEmail());
		adresseEMailCompte.setPreferredSize(new Dimension(250,30));
		adresseEMailCompte.setMinimumSize(new Dimension(250,30));
		panel4.add(adresseEMailCompte);
		centerWest.add(panel4);	

		//la suite ne concerne que les comptes utilisateurs (les attributs nom, prenom, adressePostale et bloque, lui sont propres)
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
			labelNom.setMinimumSize(new Dimension(100,30));
			panel5.add(labelNom);
			centerWest.add(panel5);	

			JPanel panel6 = new JPanel();
			panel6.setBackground(UtilitaireIhm.TRANSPARENCE);
			nomCompte.setText(u.getNom());
			nomCompte.setPreferredSize(new Dimension(250,30));
			nomCompte.setMinimumSize(new Dimension(250,30));
			panel6.add(nomCompte);
			centerWest.add(panel6);			

			JPanel panel7 = new JPanel();
			panel7.setBackground(UtilitaireIhm.TRANSPARENCE);
			prenomCompte.setText(u.getPrenom());
			labelPrenom.setPreferredSize(new Dimension(100,30));
			labelPrenom.setMinimumSize(new Dimension(100,30));
			panel7.add(labelPrenom);
			centerWest.add(panel7);	

			JPanel panel8 = new JPanel();
			panel8.setBackground(UtilitaireIhm.TRANSPARENCE);	
			prenomCompte.setPreferredSize(new Dimension(250,30));
			prenomCompte.setMinimumSize(new Dimension(250,30));
			panel8.add(prenomCompte);
			centerWest.add(panel8);	

			JPanel panel9 = new JPanel();
			panel9.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelAdressePostale.setPreferredSize(new Dimension(100,30));
			labelAdressePostale.setMinimumSize(new Dimension(100,30));
			panel9.add(labelAdressePostale);
			centerWest.add(panel9);	

			JPanel panel10 = new JPanel();
			panel10.setBackground(UtilitaireIhm.TRANSPARENCE);
			adressePostaleCompte.setText(u.getAdressePostale());
			adressePostaleCompte.setPreferredSize(new Dimension(250,30));
			adressePostaleCompte.setMinimumSize(new Dimension(250,30));
			panel10.add(adressePostaleCompte);
			centerWest.add(panel10);

			JPanel panel11 = new JPanel();
			panel11.setBackground(UtilitaireIhm.TRANSPARENCE);	
			labelStatut.setPreferredSize(new Dimension(100,30));
			labelStatut.setMinimumSize(new Dimension(100,30));
			panel11.add(labelStatut);
			centerWest.add(panel11);	

			JPanel panel12 = new JPanel();
			panel12.setBackground(UtilitaireIhm.TRANSPARENCE);
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
			statutCompte.setFont(UtilitaireIhm.POLICE3);
			statutCompte.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String statutEntre = (String) (o);
					if(statutEntre.equals("bloqué")){bloqueEntre=true;}
					else if (statutEntre.equals("non bloqué")){bloqueEntre=false;}
					repaint();
				}
			});
			panel12.add(statutCompte);
			centerWest.add(panel12);
		}
		//fin de la partie supplémentaire pour les utilisateurs

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(300,350));
		centerEast.setMinimumSize(new Dimension(300,350));

		boutonValider.setPreferredSize(new Dimension(250,40));
		boutonValider.setMinimumSize(new Dimension(250,40));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		centerEast.add(boutonValider);
		boutonResilier.setPreferredSize(new Dimension(250,40));
		boutonResilier.setMinimumSize(new Dimension(250,40));
		boutonResilier.setBackground(Color.CYAN);
		boutonResilier.setFont(UtilitaireIhm.POLICE3);
		boutonResilier.addActionListener(this);
		//l'administrateur ne peut pas s'autorésilier, pour la bonne poursuite de l'application
		//en revanche un administrateur peut résilier n'importe quel compte excepté le sien, y compris celui d'un autre administrateur
		System.out.println(c.getId()+"="+this.getAdministrateur().getCompte().getId());
		if(c.getId().equals(this.getAdministrateur().getCompte().getId())){
			boutonResilier.setVisible(false);
		}
		centerEast.add(boutonResilier);
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
	 * méthode exécutée quand l'administrateur a cliqué sur l'un des 3 JButton qui lui étaient proposés
	 * @param arg0
	 * @see UtilitaireIhm#verifieChampsCreationAdmin(int, String)
	 * @see UtilitaireIhm#verifieChampsCreationTech(int, String)
	 * @see UtilitaireIhm#verifieChampsCreationUtil(int, String)
	 * @see FenetreConfirmation#FenetreConfirmation(Compte, JFrame)
	 * @see FenetreDemandeConfirmationAdmin#FenetreDemandeConfirmationAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqué sur "valider les modifications"
		if(arg0.getSource()==boutonValider){
			try {
				//si le compte en train d'être modifié est celui d'un utilisateur
				if (compte.getType()==Compte.TYPE_UTILISATEUR){
					//champsOk indique si les champs remplis par l'administrateur sont corrects
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
				else if (compte.getType()==Compte.TYPE_ADMINISTRATEUR){
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
				else if (compte.getType()==Compte.TYPE_TECHNICIEN){
					boolean champsOk = UtilitaireIhm.verifieChampsCreationTech(Compte.TYPE_TECHNICIEN, adresseEMailCompte.getText());
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
		//s'il a cliqué sur "résilier le compte", il y a une étape supplémentaire car l'action est irréversible
		else if(arg0.getSource()==boutonResilier){
			new FenetreDemandeConfirmationAdmin(this.getAdministrateur(),this);
		}
		//sinon il retourne à son menu principal
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}		
}