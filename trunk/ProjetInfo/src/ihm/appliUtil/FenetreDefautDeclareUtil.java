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
 * FenetreDefautDeclareUtil hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>cette fenêtre s'ouvre dans le cas où l'utilisateur vient de déclarer un défaut sur un vélo suite à un emprunt court
 * <br>elle propose à l'utilisateur d'emprunter un autre vélo
 * <br>cette fenêtre est propre à l'application Utilisateur
 * @author KerGeek
 */
public class FenetreDefautDeclareUtil extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (Déconnexion)");

	/**
	 * @return l'{@link Utilisateur} de la {@link FenetreDefautDeclareUtil} 
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * Initialise l'{@link Utilisateur} de la {@link FenetreDefautDeclareUtil}
	 * @param utilisateur
	 * le nouvel {@link Utilisateur} de la {@link FenetreDefautDeclareUtil}
	 * @see Utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * constructeur de {@link FenetreDefautDeclareUtil}
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 * @param u
	 * l'utilisateur connecté à l'application
	 */
	public FenetreDefautDeclareUtil (Utilisateur u) {

		this.setContentPane(new PanneauUtil());
		System.out.println("Fenêtre proposant emprunt après déclaration d'un vélo défectueux");
		//Définit un titre pour notre fenêtre
		this.setTitle("Fenêtre défaut déclaré");
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
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setText("La présence d’un défaut a bien été signalée. Souhaitez-vous emprunter un autre vélo ? ");
		labelMsg.setPreferredSize(new Dimension(550,30));
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
	 * cette méthode est exécutée si l'utilisateur a cliqué sur l'un des deux boutons qui lui étaient proposés
	 * <br>s'il a cliqué sur "Oui", une nouvelle FenetreEmprunterVeloUtil s'ouvre
	 * <br>s'il a cliqué sur "Non", une nouvelle FenetreConfirmationUtil s'ouvre indiquant un message d'au-revoir à l'utilisateur
	 * @param arg0 
	 * @see FenetreEmprunterVeloUtil#FenetreEmprunterVeloUtil(Utilisateur)
	 * @see FenetreConfirmationUtil#FenetreConfirmationUtil(String)
	 */

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==boutonOui){
			new FenetreEmprunterVeloUtil(utilisateur);
		}
		else if (arg0.getSource()==boutonNon){
			new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
		}
		this.dispose();
	}
}