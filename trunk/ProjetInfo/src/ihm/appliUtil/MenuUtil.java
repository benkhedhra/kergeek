package ihm.appliUtil;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Utilisateur;

/**
 * MenuUtil hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>cette fenêtre propose à l'utilisateur connecté le seul choix possible compte tenu de s'il a un emprunt en cours ou non
 * <br>elle propose aussi à l'utilisateur de se déconnecter
 * <br>cette fenêtre est propre à l'application Utilisateur
 * @author KerGeek
 */
public class MenuUtil extends JFrame implements ActionListener {

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
	private JButton boutonChoix = new JButton("");
	Boolean empruntEnCours=false;

	// Accesseurs utiles

	/**
	 * @return le {@link Utilisateur} du MenuUtil 
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	/**
	 * Initialise le {@link Utilisateur} du MenuUtil
	 * @param utilisateur
	 * 
	 * le nouvel utilisateur du MenuUtil
	 * @see Utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * constructeur de MenuUtil
	 * @param u
	 * l'utilisateur connecté sur la fenêtre
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */

	public MenuUtil (Utilisateur u){

		this.setContentPane(new PanneauUtil());
		System.out.println("Affichage du menu de l'utilisateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu de l'utilisateur");
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

		empruntEnCours = (u.getEmpruntEnCours()!=null);
		boutonChoix.setPreferredSize(new Dimension(350,100));
		boutonChoix.setMaximumSize(new Dimension(350,100));
		boutonChoix.setBackground(Color.CYAN);
		boutonChoix.setFont(UtilitaireIhm.POLICE2);

		if (!empruntEnCours){
			boutonChoix.setText("Emprunter un vélo");
		}
		else {
			boutonChoix.setText("Rendre le vélo emprunté");
		}

		boutonChoix.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(boutonChoix);
		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée lorsque l'utilisateur connecté à cliqué sur l'un des boutons qui lui étaient proposés
	 * <br>s'il a cliqué sur le bouton "Déconnexion", la fenêtre  courante se ferme et une fenêtre d'au-revoir apparaît
	 * <br>sinon c'est qu'il a cliqué sur "Rendre" ou "!emprunter" un vélo, selon qu'il a un emprunt en cours ou non
	 * @param arg0 
	 * @see FenetreRendreVeloUtil#FenetreRendreVeloUtil(Utilisateur)
	 * @see FenetreEmprunterVeloUtil#FenetreEmprunterVeloUtil(Utilisateur)
	 * @throws ConnexionFermeeException
	 */
	public void actionPerformed(ActionEvent arg0) {
		try {
			if (arg0.getSource()==boutonDeconnexion){
				new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
			}
			else if(!empruntEnCours){
				FenetreEmprunterVeloUtil f = new FenetreEmprunterVeloUtil(this.getUtilisateur());
				f.setVisible(true);
			}
			else if (empruntEnCours){
				new FenetreRendreVeloUtil(this.getUtilisateur());
			}
		}catch (ConnexionFermeeException e3){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentificationUtil(false);
		}
		finally{
			this.dispose();
		}
	}
}