package ihm.appliUtil;

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
 * FenetreEmpruntLongUtil hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>cette fenêtre s'ouvre dans le cas où l'utilisateur vient de rendre un vélo plus de 2 h après l'avoir emprunté
 * <br>elle informe l'utilisateur que son compte sera bloqué pendant 7 jours
 * <br>l'utilisateur doit cliquer sur un bouton de déconnexion pour montrer qu'il a bien pris connaissance du message
 * <br>cette fenêtre est propre à l'application Utilisateur
 * @author KerGeek
 */
public class FenetreEmpruntLongUtil extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * attributs privés : composants de la fenêtre
	 */
	private Utilisateur utilisateur;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");

	// Accesseurs utiles
	
	/**
	 * @return le {@link Utilisateur} de la FenetreEmpruntLongUtil 
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	/**
	 * Initialise le {@link Utilisateur} de la FenetreEmpruntLongUtil
	 * @param utilisateur
	 * 
	 * le nouvel utilisateur de la FenetreEmpruntLongUtil
	 * @see Utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	/**
	 * constructeur de FenetreEmpruntLongUtil
	 * @param u : l'utilisateur connecté sur la fenêtre
	 * @see Utilisateur#setBloque(boolean)
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public FenetreEmpruntLongUtil (Utilisateur u){

		this.setContentPane(new PanneauUtil());
		System.out.println("Temps d'emprunt trop long");
		//Définit un titre pour notre fenêtre
		this.setTitle("Temps d'emprunt > 2 heures");
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

		// l'utilisateur est bloqué pendant une semaine
		utilisateur.setBloque(true);
		labelMsg.setText("<html><center>Vous avez rendu le vélo plus de 2 heures après l'emprunt.<br>Votre compte sera bloqué pendant une semaine.<br>Remettez le vélo dans un emplacement.</center></html> ");
		labelMsg.setPreferredSize(new Dimension(340,120));
		
		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelMsg);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * cette méthode est appelée lorsque l'utilisateur a cliqué sur le bouton "Déconnexion"
	 * <br>elle ferme la fenêtre courante et ouvre une nouvelle fenêtre d'au-revoir
	 * @param arg0 
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
		}
	}
}