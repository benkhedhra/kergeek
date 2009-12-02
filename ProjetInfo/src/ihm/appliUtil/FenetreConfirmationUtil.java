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
	
	private JLabel labelUtil = new JLabel ("Vous �tes � pr�sent d�connect�");
	private JLabel labelConfirm = new JLabel("");

	public FenetreConfirmationUtil(String msg){

		this.setContentPane(new Panneau());
		System.out.println("Ecran de confirmation");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Ecran de confirmation");
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
	
		//on veut attendre 5 secondes, puis fermer la fen�tre et ouvrir une nouvelle fen�tre d'authentification (solution � trouver)
		try {
			//il y a un probl�me ici : pendant les 5 secondes la fen�tre ne s'affiche m�me pas
			Thread.sleep(5000);
			this.dispose();
			new FenetreAuthentificationUtil(false);
		} catch (InterruptedException e) {
			MsgBox.affMsg(e.getMessage());
		}
	}
}