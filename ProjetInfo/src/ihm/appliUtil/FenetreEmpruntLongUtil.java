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
 * FenetreEmpruntLongUtil h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>cette fen�tre s'ouvre dans le cas o� l'utilisateur vient de rendre un v�lo plus de 2 h apr�s l'avoir emprunt�
 * <br>elle informe l'utilisateur que son compte sera bloqu� pendant 7 jours
 * <br>l'utilisateur doit cliquer sur un bouton de d�connexion pour montrer qu'il a bien pris connaissance du message
 * <br>cette fen�tre est propre � l'application Utilisateur
 * @author KerGeek
 */
public class FenetreEmpruntLongUtil extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * attributs priv�s : composants de la fen�tre
	 */
	private Utilisateur utilisateur;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonDeconnexion = new JButton("D�connexion");

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
	 * @param u : l'utilisateur connect� sur la fen�tre
	 * @see Utilisateur#setBloque(boolean)
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public FenetreEmpruntLongUtil (Utilisateur u){

		this.setContentPane(new PanneauUtil());
		System.out.println("Temps d'emprunt trop long");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Temps d'emprunt > 2 heures");
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

		// l'utilisateur est bloqu� pendant une semaine
		utilisateur.setBloque(true);
		labelMsg.setText("<html><center>Vous avez rendu le v�lo plus de 2 heures apr�s l'emprunt.<br>Votre compte sera bloqu� pendant une semaine.<br>Remettez le v�lo dans un emplacement.</center></html> ");
		labelMsg.setPreferredSize(new Dimension(340,120));
		
		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelMsg);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * cette m�thode est appel�e lorsque l'utilisateur a cliqu� sur le bouton "D�connexion"
	 * <br>elle ferme la fen�tre courante et ouvre une nouvelle fen�tre d'au-revoir
	 * @param arg0 
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmationUtil("Au revoir et � bient�t ! ");
		}
	}
}