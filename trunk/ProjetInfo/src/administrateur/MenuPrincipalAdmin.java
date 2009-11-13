package administrateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import appliAdminTech.FenetreAuthentification;
import appliUtil.FenetreEmprunterVelo;
import appliUtil.FenetreRendreVelo;

public class MenuPrincipalAdmin extends JFrame implements ActionListener {

	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonDeconnexion = new JButton("D�connexion");
	private JButton boutonComptes = new JButton("G�rer les comptes");
	private JButton boutonStats = new JButton("Demander des statistiques");
	private JButton boutonEtat = new JButton("Voir l'�tat actuel des stations et des v�los");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuPrincipalAdmin(Administrateur a){

		this.setAdministrateur(a);
		System.out.println("Affichage du menu principal de l'administrateur");
		//D�finit un titre pour votre fen�tre
		this.setTitle("Menu principal de l'administrateur");
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

		this.getContentPane().setBackground(Color.BLUE);	
		this.setLayout(new BorderLayout());

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentification.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.add(labelAdmin);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		boutonComptes.setPreferredSize(new Dimension(80,60));
		boutonComptes.addActionListener(this);
		center.add(boutonComptes);
		boutonStats.setPreferredSize(new Dimension(80,60));
		boutonStats.addActionListener(this);
		center.add(boutonStats);
		boutonEtat.setPreferredSize(new Dimension(80,60));
		boutonEtat.addActionListener(this);
		center.add(boutonEtat);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//la suite est mise en commentaire car les classes correspondantes ne sont pas �crites
/*
		if(arg0.getSource()==boutonComptes){
			MenuGererComptesAdmin m = new MenuGererComptesAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		else if (arg0.getSource()==boutonStats){
			MenuDemanderStatsAdmin m = new MenuDemanderStatsAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		else if (arg0.getSource()==boutonEtat){
			MenuVoirEtat m = new MenuVoirEtat(this.getAdministrateur());
			m.setVisible(true);
		}		
		else if (arg0.getSource()==boutonDeconnexion){
			FenetreConfirmation f = new FenetreConfirmation("Au revoir et � bient�t ! ");
			f.setVisible(true);
		}
*/
	}
}