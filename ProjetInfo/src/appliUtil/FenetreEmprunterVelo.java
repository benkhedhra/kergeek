package appliUtil;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import metier.Utilisateur;

public class FenetreEmprunterVelo extends JFrame implements ActionListener {

	public FenetreEmprunterVelo(Utilisateur u){
		//Définit un titre pour votre fenêtre
		this.setTitle("Menu de l'utilisateur");
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

		this.setContentPane(new Panneau());	

		JLabel labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		this.add(labelUtil,BorderLayout.NORTH);		

	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
