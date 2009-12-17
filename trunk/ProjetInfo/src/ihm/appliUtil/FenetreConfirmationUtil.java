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

	private JLabel labelUtil = new JLabel ("Vous �tes � pr�sent d�connect�");
	private JLabel labelConfirm = new JLabel("");
	private JButton boutonAuthentification = new JButton("Nouvelle authentification");

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

		//on veut attendre 5 secondes, puis fermer la fen�tre et ouvrir une nouvelle fen�tre d'authentification (solution � trouver)
		/*try {
			//il y a un probl�me ici : pendant les 5 secondes la fen�tre ne s'affiche m�me pas
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