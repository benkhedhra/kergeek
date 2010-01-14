package ihm.appliAdminTech.administrateur;

import envoieMail.SendMail;
import gestionBaseDeDonnees.DAOAdministrateur;
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
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
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
import metier.exceptionsMetier.TypeCompteException;

/** 
 * FenetreCreationCompteAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle permet � l'{@link Administrateur} de cr�er un nouveau {@link Compte}
 * @author KerGeek
 */
public class FenetreCreationCompteAdmin extends JFrame implements ActionListener {

	/**
	 * serial version ID par d�faut
	 */
	private static final long serialVersionUID = 1L;

	//Attributs
	/**
	 * administrateur est l'administrateur actuellement connect�
	 * <br>labelAdmin est le JLabel permettant d'afficher qui est l'administrateur connect�
	 * labelChemin est le JLabel indiquant l'endroit de l'application o� il se trouve dans l'application
	 * <br>labelMsg est le JLabel disant � l'administrateur ce qu'il doit faire dans cette fen�tre
	 * <br>labelQualite, labelNom, labelPrenom, labelAdresseEMail et labelAdressePostale sont des JLabel
	 * <br>typeEntre est le type de compte choisi pour le nouveau compte � cr�er
	 * <br>nomARemplir, prenomARemplir, adresseEMailARemplir, adressePostaleARemplir sont des TextFieldLimite
	 * <br>boutonValider est le bouton permettant de cr�er le nouveau compte si les param�tres entr�s sont corrects
	 * <br>boutonRetour est le bouton de retour au menu principal
	 */
	private Administrateur administrateur;

	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > G�rer les comptes > Cr�er un compte");

	private JLabel labelMsg = new JLabel("Entrer les donn�es du titulaire du nouveau compte");

	private JLabel labelQualite = new JLabel("Qualit�");
	private int typeEntre;
	private JLabel labelNom = new JLabel("Nom");
	private TextFieldLimite nomARemplir = new TextFieldLimite(20,"");
	private JLabel labelPrenom = new JLabel("Pr�nom");
	private TextFieldLimite prenomARemplir = new TextFieldLimite(20,"");
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private TextFieldLimite adresseEMailARemplir = new TextFieldLimite(250,"");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private TextFieldLimite adressePostaleARemplir = new TextFieldLimite(50,"");

	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}


	/**
	 * constructeur de {@link FenetreCreationCompteAdmin}
	 * @param a
	 * l'administrateur connect� sur la fen�tre
	 */
	public FenetreCreationCompteAdmin(Administrateur a){

		System.out.println("Fen�tre pour cr�er un nouveau compte");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Cr�ation d'un nouveau compte");
		//D�finit une taille pour celle-ci
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

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

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,800));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);


		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(1000,800));
		centerWest.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(500,40));
		labelQualite.setMaximumSize(new Dimension(500,40));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
		String[] types = new String[4];
		types[0] = "S�lection � faire";
		types[1] = "utilisateur";
		types[2] = "administrateur";
		types[3] = "technicien";
		DefaultComboBoxModel model = new DefaultComboBoxModel(types);
		JComboBox qualiteARemplir = new JComboBox(model);
		qualiteARemplir.setPreferredSize(new Dimension(250,40));
		qualiteARemplir.setMaximumSize(new Dimension(250,40));
		qualiteARemplir.setFont(UtilitaireIhm.POLICE3);
		qualiteARemplir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				String qualiteEntree = (String)o;
				if(qualiteEntree.equals("utilisateur")){
					typeEntre=Compte.TYPE_UTILISATEUR;
				}
				else if(qualiteEntree.equals("administrateur")){
					typeEntre=Compte.TYPE_ADMINISTRATEUR;
				}
				else if(qualiteEntree.equals("technicien")){
					typeEntre=Compte.TYPE_TECHNICIEN;
				}
				else{
					repaint();
				}
				modifieSiPasUtilisateur(typeEntre);
				repaint();
			}
		});
		panel2.add(qualiteARemplir);
		centerWest.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(500,40));
		labelAdresseEMail.setMaximumSize(new Dimension(500,40));
		panel3.add(labelAdresseEMail);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);	
		adresseEMailARemplir.setPreferredSize(new Dimension(250,40));
		adresseEMailARemplir.setMaximumSize(new Dimension(250,40));
		panel4.add(adresseEMailARemplir);
		centerWest.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelNom.setPreferredSize(new Dimension(500,40));
		labelNom.setMaximumSize(new Dimension(500,40));
		panel5.add(labelNom);
		centerWest.add(panel5);	

		JPanel panel6 = new JPanel();
		panel6.setBackground(UtilitaireIhm.TRANSPARENCE);	
		nomARemplir.setPreferredSize(new Dimension(250,40));
		nomARemplir.setMaximumSize(new Dimension(250,40));
		panel6.add(nomARemplir);
		centerWest.add(panel6);			

		JPanel panel7 = new JPanel();
		panel7.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelPrenom.setPreferredSize(new Dimension(500,40));
		labelPrenom.setMaximumSize(new Dimension(500,40));
		panel7.add(labelPrenom);
		centerWest.add(panel7);	

		JPanel panel8 = new JPanel();
		panel8.setBackground(UtilitaireIhm.TRANSPARENCE);	
		prenomARemplir.setPreferredSize(new Dimension(250,40));
		prenomARemplir.setMaximumSize(new Dimension(250,40));
		panel8.add(prenomARemplir);
		centerWest.add(panel8);	

		JPanel panel9 = new JPanel();
		panel9.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelAdressePostale.setPreferredSize(new Dimension(500,40));
		labelAdressePostale.setMaximumSize(new Dimension(500,40));
		panel9.add(labelAdressePostale);
		centerWest.add(panel9);	

		JPanel panel10 = new JPanel();
		panel10.setBackground(UtilitaireIhm.TRANSPARENCE);	
		adressePostaleARemplir.setPreferredSize(new Dimension(250,40));
		adressePostaleARemplir.setMaximumSize(new Dimension(250,40));
		panel10.add(adressePostaleARemplir);
		centerWest.add(panel10);

		boutonValider.setPreferredSize(new Dimension(250,50));
		boutonValider.setMaximumSize(new Dimension(250,50));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		centerWest.add(boutonValider);

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(100,800));

		center.add(centerEast,BorderLayout.EAST);

		center.setBackground(UtilitaireIhm.TRANSPARENCE);
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
	 * m�thode qui modifie graphiquement la fen�tre si le type du compte � cr�er est un administrateur ou un technicien
	 * les JLabel et les TextFieldLimite sont rendus invisibles
	 * m�thode appel�e uniquement dans l'actionPerformed du JComboBox de la fen�tre
	 * @param type
	 * le type du compte en train d'�tre cr��
	 */
	public void modifieSiPasUtilisateur(int type){
		if(type!=Compte.TYPE_UTILISATEUR){
			labelNom.setVisible(false);
			nomARemplir.setVisible(false);
			labelPrenom.setVisible(false);
			prenomARemplir.setVisible(false);
			labelAdressePostale.setVisible(false);
			adressePostaleARemplir.setVisible(false);
		}
		else{
			labelNom.setVisible(true);
			nomARemplir.setVisible(true);
			labelPrenom.setVisible(true);
			prenomARemplir.setVisible(true);
			labelAdressePostale.setVisible(true);
			adressePostaleARemplir.setVisible(true);
		}
	}

	/**
	 * m�thode ex�cut�e quand l'administrateur a cliqu� sur l'un des boutons qui lui �taient propos�s
	 * @param arg0 
	 * @see Administrateur#creerAdministrateur(Compte)
	 * @see Administrateur#creerTechnicien(Compte)
	 * @see Administrateur#creerUtilisateur(Compte, String, String, String)
	 * @see UtilitaireIhm#verifieChampsCreationAdmin(int, String)
	 * @see UtilitaireIhm#verifieChampsCreationTech(int, String)
	 * @see UtilitaireIhm#verifieChampsCreationUtil(int, String, String, String, String)
	 */
	public void actionPerformed(ActionEvent arg0) {
		try{
			// s'il a cliqu� sur "Valider"
			if(arg0.getSource()==boutonValider){
				try {
					if (UtilitaireIhm.verifieTypeCreationCompte(typeEntre)){
						// si c'est un compte utilisateur
						if(typeEntre==Compte.TYPE_UTILISATEUR){
							if(UtilitaireIhm.verifieChampsCreationUtil(typeEntre,adresseEMailARemplir.getText(),nomARemplir.getText(), prenomARemplir.getText(), adressePostaleARemplir.getText())){
								Compte compte = this.getAdministrateur().creerCompte(typeEntre, adresseEMailARemplir.getText());
								try {
									Utilisateur utilisateur = this.getAdministrateur().creerUtilisateur(compte, nomARemplir.getText(), prenomARemplir.getText(), adressePostaleARemplir.getText());
									DAOUtilisateur.createUtilisateur(utilisateur);
									try{
										SendMail.sendMail(compte.getAdresseEmail(),"Votre compte a �t� cr��","Bonjour "+utilisateur.getPrenom()+" "+utilisateur.getPrenom()+"\n Vous pouvez d�s � pr�sent utiliser B�loBreizh ! \nA bient�t sur les routes de KerLann ! \n\nKerGeek");
									} catch (SendFailedException e) {
										MsgBox.affMsg("L'adresse e-mail donn�e est invalide. Veuillez la modifier ult�rieurement. ");
									} catch (UnsupportedEncodingException e) {
										MsgBox.affMsg("L'adresse e-mail donn�e est invalide. Veuillez la modifier ult�rieurement. ");
									} catch (MessagingException e) {
										MsgBox.affMsg("Echec dans l'envoi de l'e-mail : "+e.getMessage());					
									}
									new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
								}
								catch (TypeCompteException e) {
									MsgBox.affMsg(e.getMessage());
									new FenetreCreationCompteAdmin(this.getAdministrateur());
								}
							}
							else {
								MsgBox.affMsg("Les champs entr�s sont incorrects");
								new FenetreCreationCompteAdmin(this.getAdministrateur());
							}
						}
						// si c'est un compte administrateur
						if(typeEntre==Compte.TYPE_ADMINISTRATEUR){
							if(UtilitaireIhm.verifieChampsCreationAdmin(typeEntre,adresseEMailARemplir.getText())){
								Compte compte = this.getAdministrateur().creerCompte(typeEntre, adresseEMailARemplir.getText());
								Administrateur administrateur;
								administrateur = this.getAdministrateur().creerAdministrateur(compte);

								DAOAdministrateur.createAdministrateur(administrateur);
								try{
									SendMail.sendMail(compte.getAdresseEmail(),"Votre compte a �t� cr��","Bonjour "+compte.getId()+"\n Votre compte vient d'�tre cr�� en tant qu'administrateur de B�loBreizh. \n Votre mot de passe est le suivant : "+compte.getMotDePasse()+". \n Nous vous invitons d�s � pr�sent � vous connecter sur l'application afin de le modifier. \nA bient�t ! \n\nKerGeek");
								} catch (SendFailedException e) {
									MsgBox.affMsg("L'adresse e-mail donn�e est invalide. Veuillez la modifier ult�rieurement. ");
								} catch (UnsupportedEncodingException e) {
									MsgBox.affMsg("L'adresse e-mail donn�e est invalide. Veuillez la modifier ult�rieurement. ");
								} catch (MessagingException e) {
									MsgBox.affMsg("Echec dans l'envoi de l'e-mail : "+e.getMessage());					
								}
								new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
							}
							else {
								MsgBox.affMsg("Les champs entr�s sont incorrects");
								new FenetreCreationCompteAdmin(this.getAdministrateur());
							}
						}
						// si c'est un compte technicien
						if(typeEntre==Compte.TYPE_TECHNICIEN){
							if(UtilitaireIhm.verifieChampsCreationTech(typeEntre,adresseEMailARemplir.getText())){
								Compte compte = this.getAdministrateur().creerCompte(typeEntre, adresseEMailARemplir.getText());
								Technicien technicien = this.getAdministrateur().creerTechnicien(compte);
								DAOTechnicien.createTechnicien(technicien);
								try{
									SendMail.sendMail(compte.getAdresseEmail(),"Votre compte a �t� cr��","Bonjour "+compte.getId()+"\n Votre compte vient d'�tre cr�� en tant que technicien de B�loBreizh. \n Votre mot de passe est le suivant : "+compte.getMotDePasse()+". \n Nous vous invitons d�s � pr�sent � vous connecter sur l'application afin de le modifier. \nA bient�t ! \n\nKerGeek");
								} catch (SendFailedException e) {
									MsgBox.affMsg("L'adresse e-mail donn�e est invalide. Veuillez la modifier ult�rieurement. ");
								} catch (UnsupportedEncodingException e) {
									MsgBox.affMsg("L'adresse e-mail donn�e est invalide. Veuillez la modifier ult�rieurement. ");
								} catch (MessagingException e) {
									MsgBox.affMsg("Echec dans l'envoi de l'e-mail : "+e.getMessage());					
								}
								new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
							}
							else {
								MsgBox.affMsg("Les champs entr�s sont incorrects");
								new FenetreCreationCompteAdmin(this.getAdministrateur());
							}
						}
					}
					else{
						MsgBox.affMsg("S�lectionnez un type de compte");
						new FenetreCreationCompteAdmin(this.getAdministrateur());
					}

				} 
				catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
					new MenuPrincipalAdmin(this.getAdministrateur());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
					new MenuPrincipalAdmin(this.getAdministrateur());
				} catch (TypeCompteException e) {
					MsgBox.affMsg(e.getMessage());
					new FenetreCreationCompteAdmin(this.getAdministrateur());
				}
				catch (ConnexionFermeeException e){
					MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
					new FenetreAuthentification(false);
				}
			}
			//s'il a cliqu� sur "Retour au menu principal"
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalAdmin(this.getAdministrateur());
			}
		}
		finally{
			this.dispose();
		}
	}
}