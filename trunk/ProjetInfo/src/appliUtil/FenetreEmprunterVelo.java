package appliUtil;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreEmprunterVelo extends JFrame {

	public FenetreEmprunterVelo () {

		//D�finit un titre pour votre fen�tre
		this.setTitle("Emprunter un v�lo");
		//D�finit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
		this.setSize(700, 500);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);
	/* les lignes suivantes servent � instancier un objet Jpanel et � d�finir sa couleur de fond
	 * 
	 * JPanel pan = new JPanel();
	 * pan.setBackground(Color.BLUE);
	 * */

        this.setContentPane(new Panneau());
        JButton bouton = new JButton("Bonjour bla bli blou");
        this.getContentPane().add(bouton);    
		
		
		
		this.setVisible(true);
	}       
}