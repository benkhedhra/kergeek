package technicien;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Technicien;
import appliAdminTech.FenetreAuthentification;
import appliUtil.FenetreEmprunterVelo;
import appliUtil.FenetreRendreVelo;

public class MenuPrincipalTech extends JFrame implements ActionListener {

	private Technicien tech;
	private JLabel labelTech = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JButton bouton1 = new JButton("Enregistrer un nouveau vélo");
	private JButton bouton2 = new JButton("Retirer un vélo défectueux d'une station");
	private JButton bouton3 = new JButton("Gérer les demandes d'assignation");

	public Technicien getTechnicien() {
		return tech;
	}

	public void setTechnicien(Technicien tech) {
		this.tech = tech;
	}

	public MenuPrincipalTech(Technicien t){

		this.setTechnicien(t);
		System.out.println("Affichage du menu principal du technicien");
		//Définit un titre pour votre fenêtre
		this.setTitle("Menu principal du technicien");
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

		this.getContentPane().setBackground(Color.BLUE);	
		this.setLayout(new BorderLayout());

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentification.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.add(labelTech);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		bouton1.setPreferredSize(new Dimension(80,60));
		bouton1.addActionListener(this);
		center.add(bouton1);
		bouton2.setPreferredSize(new Dimension(80,60));
		bouton2.addActionListener(this);
		center.add(bouton2);
		bouton3.setPreferredSize(new Dimension(80,60));
		bouton3.addActionListener(this);
		center.add(bouton3);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//la suite est mise en commentaire car les classes correspondantes ne sont pas écrites
		/*if(arg0.getSource()==bouton1){
			FenetreEnregistrerVelo f = new FenetreEnregistrerVelo(this.getTechnicien());
			f.setVisible(true);
		}
		else if (arg0.getSource()==bouton2Stats){
			FenetreRetirerVelo f = new FenetreRetirerVelo(this.getTechnicien());
			f.setVisible(true);
		}
		else if (arg0.getSource()==bouton3){
			FenetreGererAssignations f = new FenetreGererAssignations(this.getTechnicien());
			f.setVisible(true);
		}		
		else if (arg0.getSource()==boutonDeconnexion){
			FenetreConfirmation f = new FenetreConfirmation("Au revoir et à bientôt ! ");
			f.setVisible(true);
		}*/
	}
}