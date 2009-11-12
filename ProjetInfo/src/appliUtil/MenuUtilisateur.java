package appliUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Utilisateur;

public class MenuUtilisateur extends JFrame implements ActionListener {

	private Utilisateur utilisateur = LancerAppliUtil.UTEST;
	private JLabel labelUtil = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JButton boutonEmprunter = new JButton("Emprunter un vélo");
	private JButton boutonRendre = new JButton("Rendre un vélo");
	Boolean empruntEnCours=false;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public MenuUtilisateur (Utilisateur u){

		this.setUtilisateur(u);
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
		this.setLayout(new BorderLayout());

		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		
		empruntEnCours = (u.getVelo()!=null);
		boutonEmprunter.setPreferredSize(new Dimension(200,150));
		boutonRendre.setPreferredSize(new Dimension(200,150));
		
		Color translucide = new Color(255,255,255, 240);

		if (!empruntEnCours){
			boutonRendre.setEnabled(true);
			boutonRendre.setForeground(translucide);
		}
		else {
			boutonEmprunter.setEnabled(true);
			boutonEmprunter.setForeground(translucide);
		}
		
		boutonEmprunter.addActionListener(this);
		JPanel center = new JPanel();
		center.add(boutonEmprunter);
		center.add(boutonRendre);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonEmprunter){
			FenetreEmprunterVelo f = new FenetreEmprunterVelo(utilisateur);
			f.setVisible(true);
		}
		else if (arg0.getSource()==boutonRendre){
			FenetreRendreVelo f = new FenetreRendreVelo(utilisateur);
			f.setVisible(true);
		}
		else if (arg0.getSource()==boutonDeconnexion){
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
		}
	}
}