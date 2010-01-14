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

/**
 * FenetreConfirmationUtil hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>cette fenêtre permet d'afficher un message à l'utilisateur qui vient de terminer à son action et qui est donc déconnecté
 * <br>cette fenêtre est propre à l'application Utilisateur
 * @author KerGeek
 */
public class FenetreConfirmationUtil extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * liste des attributs privés : composants de la fenêtre
	 */
	private JLabel labelUtil = new JLabel ("Vous êtes à présent déconnecté");
	private JLabel labelConfirm = new JLabel("");
	private JButton boutonAuthentification = new JButton("Nouvelle authentification");

	/**
	 * constructeur de FenetreConfirmationUtil
	 * @param msg
	 * le message à afficher dans la fenêtre
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public FenetreConfirmationUtil(String msg){

		this.setContentPane(new PanneauUtil());
		System.out.println("Ecran de confirmation");
		//Définit un titre pour notre fenêtre
		this.setTitle("Ecran de confirmation");
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

		labelUtil.setFont(UtilitaireIhm.POLICE4);
		labelUtil.setPreferredSize(new Dimension(300,30));
		boutonAuthentification.setPreferredSize(new Dimension(250,30));
		boutonAuthentification.setBackground(Color.MAGENTA);
		boutonAuthentification.setFont(UtilitaireIhm.POLICE4);
		boutonAuthentification.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonAuthentification);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelConfirm.setText(msg);
		if(labelUtil.getText().length()>=30){
			labelConfirm.setFont(UtilitaireIhm.POLICE2);
		}
		else{
			labelConfirm.setFont(UtilitaireIhm.POLICE1);
		}
		center.add(labelConfirm);
		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);

		//on veut attendre 5 secondes, puis fermer la fenêtre et ouvrir une nouvelle fenêtre d'authentification (solution à trouver)
		/*try {
			//il y a un problème ici : pendant les 5 secondes la fenêtre ne s'affiche même pas
			Thread.sleep(5000);
			this.dispose();
			new UtilitaireIhm(false);
		} catch (InterruptedException e) {
			MsgBox.affMsg(e.getMessage());
		}*/
	}
	
	/**
	 * méthode exécutée si l'utilisateur a cliqué sur le bouton "Nouvelle identification"
	 * <br>ferme la fenêtre en cours et ouvre une nouvelle fenêtre d'authentification
	 * @param arg0 
	 * @see FenetreAuthentificationUtil#FenetreAuthentificationUtil(Boolean)
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonAuthentification){
			new FenetreAuthentificationUtil(false);			
		}

	}
}