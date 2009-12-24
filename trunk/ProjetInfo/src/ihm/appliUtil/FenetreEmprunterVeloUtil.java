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
 * FenetreEmprunterVeloUtil h�rite de JFrame et impl�mente ActionListener
 * cette fen�tre s'ouvre dans le cas o� l'utilisateur vient de manifester son intention d'emprunter un v�lo dans son menu
 * elle demande � l'utilisateur d'entrer l'identifiant du v�lo qu'il d�sire emprunter
 * cette fen�tre est propre � l'application Utilisateur
 * @author KerGeek
 */
public class FenetreEmprunterVeloUtil extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * attributs priv�s : composants de la fen�tre
	 */
	private Utilisateur utilisateur;
	private JLabel labelUtil = new JLabel("");
	private JButton boutonDeconnexion = new JButton("D�connexion");
	private JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du v�lo emprunt�");
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
	 * @param u : l'utilisateur connect� sur la fen�tre
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */

	public FenetreEmprunterVeloUtil(Utilisateur u){

		this.setContentPane(new PanneauUtil());
		System.out.println("Affichage de la fen�tre d'emprunt d'un v�lo");
		//D�finit un titre pour votre fen�tre
		this.setTitle("Emprunter un v�lo");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setUtilisateur(u);

		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
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
	 * m�thode ex�cut�e si l'utilisateur a cliqu� sur le bouton "Valider"
	 * la fen�tre courante se ferme et une nouvelle fen�tre adapt�e s'ouvre
	 * si le v�lo entr� par l'utilisateur est bien existant disponible dans la station une fen�tre de confirmation s'ouvre et le nouvel emprunt est cr��
	 * si l'identifiant du v�lo entr� n'est pas valide l'utilisateur est pr�venu par une MsgBox adapt�e
	 * si l'utilisateur emprunte le dernier v�lo de la station un e-mail est envoy� � l'ensemble des admin et des tech
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
					MsgBox.affMsg("Saisie incorrecte : le v�lo entr� n'existe pas");
					new FenetreEmprunterVeloUtil(this.getUtilisateur());
				}
				else if(!DAOVelo.estDisponible(veloARemplir.getText())){
					MsgBox.affMsg("Saisie incorrecte : le v�lo entr� n'est pas disponible");
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
							SendMail.sendMail(adresseEMail,"Station "+station.getAdresse()+" vide","Bonjour "+t.getCompte().getId()+"\n La station "+station.getAdresse()+" est vide. Veuillez consulter les demandes d'assignation � ce sujet. ");
						}
						for (Administrateur a : listeAdmin){
							adresseEMail = a.getCompte().getAdresseEmail();
							SendMail.sendMail(adresseEMail,"Station "+station.getAdresse()+" vide","Bonjour "+a.getCompte().getId()+"\n La station "+station.getAdresse()+" est vide. Veuillez envoyer une demande d'assignation ad�quate. ");
						}
				}

					new FenetreConfirmationUtil("Vous pouvez retirer le v�lo "+velo.getId() +" de son emplacement. Merci et � bient�t ! ");
					System.out.println("L'emprunt a bien �t� enregistr�");
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
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentificationUtil(false);
			}
		}
		else{
			new FenetreConfirmationUtil("Merci et � bient�t ! ");
		}

	}
}