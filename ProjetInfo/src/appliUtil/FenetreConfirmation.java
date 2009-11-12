package appliUtil;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FenetreConfirmation extends JFrame {

	private JLabel labelConfirm = new JLabel("");

	public FenetreConfirmation(String msg){
		this.setTitle("Ecran de confirmation");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.setContentPane(new Panneau());
		this.getContentPane().setLayout(new BorderLayout());
		JLabel labelConfirm = new JLabel (msg);

		this.getContentPane().add(labelConfirm,BorderLayout.CENTER);
		
		this.setVisible(true);
		
		try{
			//on veut attendre 3 secondes, puis fermer la fenêtre et ouvrir une nouvelle fenêtre d'authentification (solution à trouver)
			this.wait(3000); 
			this.dispose();
			FenetreAuthentificationUtil f= new FenetreAuthentificationUtil(false);
			f.setVisible(true);
			}catch(InterruptedException e){
			System.out.println(e.getMessage());
			} 
		
	}
}