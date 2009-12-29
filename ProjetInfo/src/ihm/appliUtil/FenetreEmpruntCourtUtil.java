package ihm.appliUtil;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;

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

import metier.DemandeIntervention;
import metier.Utilisateur;
import metier.Velo;

/**
 * FenetreEmpruntCourtUtil hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>cette fenêtre s'ouvre dans le cas où l'utilisateur vient de rendre un vélo moins de 2 minutes après l'avoir emprunté
 * <br>elle propose à l'utilisateur de déclarer ou non un défaut sur le vélo qu'il vient de rendre
 * <br>cette fenêtre est propre à l'application Utilisateur
 * @author KerGeek
 */
public class FenetreEmpruntCourtUtil extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * liste des attributs privés : composants de la fenêtre
	 */
	private Utilisateur utilisateur;
	private Velo velo;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (Déconnexion)");

	/**
	 * @return l'{@link Utilisateur} de la {@link FenetreEmpruntCourtUtil}
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	/**
	 * Initialise l'{@link Utilisateur} de la {@link FenetreEmpruntCourtUtil}
	 * @param utilisateur
	 * 
	 * le nouvel utilisateur de la FenetreEmpruntCourtUtil
	 * @see Utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return le {@link Velo} qui vient d'être rendu moins de 2 minutes après avoir été emprunté
	 */
	public Velo getVelo() {
		return velo;
	}

	/**
	 * Initialise le {@link Velo} de la FenetreEmpruntCourtUtil
	 * @param velo
	 * le nouveau vélo de la FenetreEmpruntCourtUtil
	 * @see Velo
	 */
	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	/**
	 * constructeur de {@link FenetreEmpruntCourtUtil}
	 * @param u
	 * l'utilisateur connecté sur la fenêtre
	 * @param v
	 * le vélo qui vient d'être emprunté et rendu moins de 2 minutes après
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public FenetreEmpruntCourtUtil (Utilisateur u,Velo v){

		this.setContentPane(new PanneauUtil());
		System.out.println("Le temps d'emprunt a été très court");
		//Définit un titre pour notre fenêtre
		this.setTitle("Temps d'emprunt < 2 minutes");
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
		this.setVelo(v);

		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(UtilitaireIhm.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setText("Vous avez emprunté ce vélo pendant un laps de temps très court. Souhaitez-vous signaler un défaut sur celui-ci ? ");
		labelMsg.setPreferredSize(new Dimension(650,30));
		boutonOui.setPreferredSize(new Dimension(150,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(150,50));
		boutonNon.setBackground(Color.CYAN);

		boutonOui.addActionListener(this);
		boutonNon.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelMsg);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'utilisateur a cliqué sur l'un des deux boutons qui lui étaient proposés
	 * <br>si l'utilisateur veut déclarer le vélo défectueux, le vélo est déclaré en panne et une nouvelle demande d'intervention est créée dans la base de données
	 * <br>sinon, l'utilisateur a terminé son action, il est déconnecté et un message d'au-revoir apparaît
	 * @param arg0 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see {@link DemandeIntervention#DemandeIntervention(Utilisateur, Velo)}
	 * @see DAODemandeIntervention#createDemandeIntervention(DemandeIntervention)
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonOui){
			this.getVelo().setEnPanne(true);
			DemandeIntervention demande = new DemandeIntervention(this.getUtilisateur(),velo);
			try {
				DAODemandeIntervention.createDemandeIntervention(demande);
				DAOVelo.updateVelo(this.getVelo());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ConnexionFermeeException e3){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentificationUtil(false);
			}
			new FenetreDefautDeclareUtil(this.getUtilisateur());
		}
		else if (arg0.getSource()==boutonNon){
			new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
		}
	}
}