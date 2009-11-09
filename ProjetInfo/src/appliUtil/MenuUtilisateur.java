package appliUtil;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import metier.Utilisateur;

public class MenuUtilisateur extends JFrame implements ActionListener {

	public Utilisateur u;
	
	public Utilisateur getUtilisateur() {
		return u;
	}

	public void setUtilisateur(Utilisateur u) {
		this.u = u;
	}

	public MenuUtilisateur (Utilisateur u){

		System.out.println("Affichage du menu de l'utilisateur");
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

		JButton bouton;

		if (u.getVelo() == null){
			bouton = new JButton("Emprunter un vélo");
		}
		else {
			bouton = new JButton("Rendre un vélo");
		}

		this.add(bouton, BorderLayout.CENTER);
		bouton.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		dispose();
		if (u.getVelo() == null){
			FenetreEmprunterVelo f = new FenetreEmprunterVelo(u);
			f.setVisible(true);
		}
		else{
			FenetreRendreVelo f = new FenetreRendreVelo(u);
			f.setVisible(true);
			}
	}       







}
