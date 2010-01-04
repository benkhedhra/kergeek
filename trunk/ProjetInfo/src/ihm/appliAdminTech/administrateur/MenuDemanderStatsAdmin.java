package ihm.appliAdminTech.administrateur;

import ihm.UtilitaireIhm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;

public class MenuDemanderStatsAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private JButton boutonFrequentation = new JButton("Fréquentation des stations");
	private JButton boutonInterventions = new JButton("<html> <center>Statistiques sur<br>les interventions<br>de maintenance</center></html>");
	private JButton boutonUtilisateurs = new JButton("<html> <center>Statistiques sur<br>les utilisateurs</center></html>");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuDemanderStatsAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu demander statistiques de l'administrateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu demander statistiques de l'administrateur");
		//Définit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);


		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonFrequentation.setPreferredSize(new Dimension(300,200));
		boutonFrequentation.setMaximumSize(new Dimension(300,200));
		boutonFrequentation.setFont(UtilitaireIhm.POLICE3);
		boutonFrequentation.addActionListener(this);
		center.add(boutonFrequentation);
		boutonInterventions.setPreferredSize(new Dimension(300,200));
		boutonInterventions.setMaximumSize(new Dimension(300,1200));
		boutonInterventions.setFont(UtilitaireIhm.POLICE3);
		boutonInterventions.addActionListener(this);
		center.add(boutonInterventions);
		boutonUtilisateurs.setPreferredSize(new Dimension(300,200));
		boutonUtilisateurs.setMaximumSize(new Dimension(300,200));
		boutonUtilisateurs.setFont(UtilitaireIhm.POLICE3);
		boutonUtilisateurs.addActionListener(this);
		center.add(boutonUtilisateurs);
		this.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,800));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());
		boutonRetour.setPreferredSize(new Dimension(300,50));
		boutonRetour.setMaximumSize(new Dimension(300,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonFrequentation){
			new FenetreFrequentationStationsAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonInterventions){
			new MenuInterventionsMaintenanceAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonUtilisateurs){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),true);
		}
	}
}