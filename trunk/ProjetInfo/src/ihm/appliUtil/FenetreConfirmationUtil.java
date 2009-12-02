package ihm.appliUtil;

import ihm.MsgBox;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreConfirmationUtil extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel labelUtil = new JLabel ("Vous êtes à présent déconnecté");
	private JLabel labelConfirm = new JLabel("");

	public FenetreConfirmationUtil(String msg){

		this.setContentPane(new Panneau());
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

		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelUtil);
		this.getContentPane().add(north,BorderLayout.NORTH);
		
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelConfirm.setText(msg);
		labelConfirm.setFont(FenetreAuthentificationUtil.POLICE1);
		center.add(labelConfirm);
		this.getContentPane().add(center, BorderLayout.CENTER);
		
		this.setVisible(true);
	
		//on veut attendre 5 secondes, puis fermer la fenêtre et ouvrir une nouvelle fenêtre d'authentification (solution à trouver)
		/*try {
			//il y a un problème ici : pendant les 5 secondes la fenêtre ne s'affiche même pas
			Thread.sleep(5000);
			this.dispose();
			new FenetreAuthentificationUtil(false);
		} catch (InterruptedException e) {
			MsgBox.affMsg(e.getMessage());
		}*/
	}
}