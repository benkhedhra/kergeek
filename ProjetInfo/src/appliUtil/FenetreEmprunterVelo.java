package appliUtil;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreEmprunterVelo extends JFrame {

	public FenetreEmprunterVelo () {

		//Définit un titre pour votre fenêtre
		this.setTitle("Emprunter un vélo");
		//Définit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
		this.setSize(700, 500);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);
	/* les lignes suivantes servent à instancier un objet Jpanel et à définir sa couleur de fond
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