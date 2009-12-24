package ihm.appliUtil;

import envoieMail.SendMail;
import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Station;
import metier.Technicien;
import metier.Utilisateur;
import metier.Velo;
import metier.exceptionsMetier.CompteBloqueException;

/**
 * FenetreEmprunterVeloUtil hérite de JFrame et implémente ActionListener
 * cette fenêtre s'ouvre dans le cas où l'utilisateur vient de manifester son intention d'emprunter un vélo dans son menu
 * elle demande à l'utilisateur d'entrer l'identifiant du vélo qu'il désire emprunter
 * cette fenêtre est propre à l'application Utilisateur
 * @author KerGeek
 */
public class FenetreEmprunterVeloUtil extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * attributs privés : composants de la fenêtre
	 */
	private Utilisateur utilisateur;
	private JLabel labelUtil = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du vélo emprunté");
	private TextFieldLimite veloARemplir = new TextFieldLimite(4,"");
	private JButton boutonValider = new JButton ("Valider");

	// Accesseurs utiles
		
	/**
	 * @return le {@link Utilisateur} de la FenetreEmprunterVeloUtil 
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	/**
	 * Initialise le {@link Utilisateur} de la FenetreEmprunterVeloUtil
	 * @param utilisateur
	 * 
	 * le nouvel utilisateur de la FenetreEmprunterVeloUtil
	 * @see Utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	/**
	 * constructeur de FenetreEmprunterVeloUtil
	 * @param u : l'utilisateur connecté sur la fenêtre
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */

	public FenetreEmprunterVeloUtil(Utilisateur u){

		this.setContentPane(new PanneauUtil());
		System.out.println("Affichage de la fenêtre d'emprunt d'un vélo");
		//Définit un titre pour votre fenêtre
		this.setTitle("Emprunter un vélo");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setUtilisateur(u);

		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(UtilitaireIhm.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(UtilitaireIhm.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);


		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelVelo.setPreferredSize(new Dimension(600,30));
		center.add(labelVelo);
		veloARemplir.setFont(UtilitaireIhm.POLICE3);
		veloARemplir.setPreferredSize(new Dimension(150, 30));
		veloARemplir.setForeground(Color.BLUE);
		center.add(veloARemplir);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);
		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	
	/**
	 * Override
	 * méthode exécutée si l'utilisateur a cliqué sur le bouton "Valider"
	 * la fenêtre courante se ferme et une nouvelle fenêtre adaptée s'ouvre
	 * si le vélo entré par l'utilisateur est bien existant disponible dans la station une fenêtre de confirmation s'ouvre et le nouvel emprunt est créé
	 * si l'identifiant du vélo entré n'est pas valide l'utilisateur est prévenu par une MsgBox adaptée
	 * si l'utilisateur emprunte le dernier vélo de la station un e-mail est envoyé à l'ensemble des admin et des tech
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws CompteBloqueException
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public void actionPerformed(ActionEvent arg0) {
		Velo velo;
		this.dispose();
		if(arg0.getSource()==boutonValider){
			try {
				if(!DAOVelo.existe(veloARemplir.getText())){
					MsgBox.affMsg("Saisie incorrecte : le vélo entré n'existe pas");
					new FenetreEmprunterVeloUtil(this.getUtilisateur());
				}
				else if(!DAOVelo.estDisponible(veloARemplir.getText())){
					MsgBox.affMsg("Saisie incorrecte : le vélo entré n'est pas disponible");
					new FenetreEmprunterVeloUtil(this.getUtilisateur());
				}
				else{
					
					velo = gestionBaseDeDonnees.DAOVelo.getVeloById(veloARemplir.getText());
					
					Station station = (Station) velo.getLieu();
					System.out.println(station.getAdresse());
					System.out.println(DAOVelo.getVelosByLieu(station).size());
					
					this.getUtilisateur().emprunteVelo(velo);

					DAOEmprunt.createEmprunt(velo.getEmpruntEnCours());
					DAOVelo.updateVelo(velo);
					
					if (DAOVelo.getVelosByLieu(station).isEmpty()){
						List<Technicien> listeTech = DAOTechnicien.getAllTechniciens();
						List<Administrateur> listeAdmin = DAOAdministrateur.getAllAdministrateurs();
						
						String adresseEMail;
						for (Technicien t : listeTech){
							adresseEMail = t.getCompte().getAdresseEmail();
							SendMail.sendMail(adresseEMail,"Station "+station.getAdresse()+" vide","Bonjour "+t.getCompte().getId()+"\n La station "+station.getAdresse()+" est vide. Veuillez consulter les demandes d'assignation à ce sujet. ");
						}
						for (Administrateur a : listeAdmin){
							adresseEMail = a.getCompte().getAdresseEmail();
							SendMail.sendMail(adresseEMail,"Station "+station.getAdresse()+" vide","Bonjour "+a.getCompte().getId()+"\n La station "+station.getAdresse()+" est vide. Veuillez envoyer une demande d'assignation adéquate. ");
						}
				}

					new FenetreConfirmationUtil("Vous pouvez retirer le vélo "+velo.getId() +" de son emplacement. Merci et à bientôt ! ");
					System.out.println("L'emprunt a bien été enregistré");
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (CompteBloqueException e) {
				MsgBox.affMsg("CompteBloqueException : " +e.getMessage());
			} catch (UnsupportedEncodingException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (MessagingException e) {
				MsgBox.affMsg(e.getMessage());
			}
			catch (ConnexionFermeeException e3){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentificationUtil(false);
			}
		}
		else{
			new FenetreConfirmationUtil("Merci et à bientôt ! ");
		}

	}
}