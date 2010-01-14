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
 * FenetreDefautDeclareUtil h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>cette fen�tre s'ouvre dans le cas o� l'utilisateur vient de d�clarer un d�faut sur un v�lo suite � un emprunt court
 * <br>elle propose � l'utilisateur d'emprunter un autre v�lo
 * <br>cette fen�tre est propre � l'application Utilisateur
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
	private JButton boutonNon = new JButton("NON (D�connexion)");

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
	 * l'utilisateur connect� � l'application
	 */
	public FenetreDefautDeclareUtil (Utilisateur u) {

		this.setContentPane(new PanneauUtil());
		System.out.println("Fen�tre proposant emprunt apr�s d�claration d'un v�lo d�fectueux");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Fen�tre d�faut d�clar�");
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
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setText("La pr�sence d�un d�faut a bien �t� signal�e. Souhaitez-vous emprunter un autre v�lo ? ");
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
	 * cette m�thode est ex�cut�e si l'utilisateur a cliqu� sur l'un des deux boutons qui lui �taient propos�s
	 * <br>s'il a cliqu� sur "Oui", une nouvelle FenetreEmprunterVeloUtil s'ouvre
	 * <br>s'il a cliqu� sur "Non", une nouvelle FenetreConfirmationUtil s'ouvre indiquant un message d'au-revoir � l'utilisateur
	 * @param arg0 
	 * @see FenetreEmprunterVeloUtil#FenetreEmprunterVeloUtil(Utilisateur)
	 * @see FenetreConfirmationUtil#FenetreConfirmationUtil(String)
	 */

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==boutonOui){
			new FenetreEmprunterVeloUtil(utilisateur);
		}
		else if (arg0.getSource()==boutonNon){
			new FenetreConfirmationUtil("Au revoir et � bient�t ! ");
		}
		this.dispose();
	}
}