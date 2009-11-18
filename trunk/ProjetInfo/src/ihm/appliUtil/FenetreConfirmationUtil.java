package ihm.appliUtil;

import ihm.MsgBox;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FenetreConfirmationUtil extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

		this.getContentPane().setLayout(new BorderLayout());
		labelConfirm.setText(msg);
		labelConfirm.setPreferredSize(new Dimension(400,50));
		labelConfirm.setFont(FenetreAuthentificationUtil.POLICE1);
		
		this.getContentPane().add(labelConfirm,BorderLayout.CENTER);

		this.setVisible(true);

		//on veut attendre 3 secondes, puis fermer la fenêtre et ouvrir une nouvelle fenêtre d'authentification (solution à trouver)
		try {
			//il y a un problème ici : au bout des 3 secondes c'est this qui s'ouvre ... 
			Thread.sleep(3000);
			this.dispose();
			FenetreAuthentificationUtil f= new FenetreAuthentificationUtil(false);
			f.setVisible(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			MsgBox.affMsg(e.getMessage());
		}

	}
}