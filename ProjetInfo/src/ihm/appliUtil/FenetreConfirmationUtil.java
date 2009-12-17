package ihm.appliUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreConfirmationUtil extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelUtil = new JLabel ("Vous êtes à présent déconnecté");
	private JLabel labelConfirm = new JLabel("");
	private JButton boutonAuthentification = new JButton("Nouvelle authentification");

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
		labelUtil.setPreferredSize(new Dimension(300,30));
		boutonAuthentification.setPreferredSize(new Dimension(250,30));
		boutonAuthentification.setBackground(Color.MAGENTA);
		boutonAuthentification.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonAuthentification.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonAuthentification);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelConfirm.setText(msg);
		if(labelUtil.getText().length()>=30){
			labelConfirm.setFont(FenetreAuthentificationUtil.POLICE2);
		}
		else{
			labelConfirm.setFont(FenetreAuthentificationUtil.POLICE1);
		}
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

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonAuthentification){
			new FenetreAuthentificationUtil(false);			
		}

	}
}