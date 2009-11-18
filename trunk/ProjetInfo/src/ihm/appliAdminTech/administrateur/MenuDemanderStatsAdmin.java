package ihm.appliAdminTech.administrateur;

import ihm.appliUtil.FenetreAuthentificationUtil;

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

public class MenuDemanderStatsAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private JButton boutonFrequentation = new JButton("Fr�quentation des stations");
	private JButton boutonInterventions = new JButton("Interventions de maintenance");
	private JButton boutonUtilisateurs = new JButton("Statistiques sur les utilisateurs");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuDemanderStatsAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu demander statistiques de l'administrateur");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Menu demander statistiques de l'administrateur");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);


		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonFrequentation.setPreferredSize(new Dimension(200,120));
		boutonFrequentation.setMaximumSize(new Dimension(200,120));
		boutonFrequentation.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonFrequentation.addActionListener(this);
		center.add(boutonFrequentation);
		boutonInterventions.setPreferredSize(new Dimension(200,120));
		boutonInterventions.setMaximumSize(new Dimension(200,120));
		boutonInterventions.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonInterventions.addActionListener(this);
		center.add(boutonInterventions);
		boutonUtilisateurs.setPreferredSize(new Dimension(200,120));
		boutonUtilisateurs.setMaximumSize(new Dimension(200,120));
		boutonUtilisateurs.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonUtilisateurs.addActionListener(this);
		center.add(boutonUtilisateurs);
		this.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,40));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonRetour){
			MenuPrincipalAdmin m = new MenuPrincipalAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		/*else if (arg0.getSource()==boutonFrequentation){
			FenetreFrequentationStationAdmin f = new FenetreFrequentationStationAdmin(this.getAdministrateur());
			f.setVisible(true);
		}*/
		else if (arg0.getSource()==boutonInterventions){
			MenuInterventionsMaintenanceAdmin m = new MenuInterventionsMaintenanceAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		/*else if (arg0.getSource()==boutonUtilisateurs){
			FenetreRechercherCompteAdmin f = new FenetreRechercherCompteAdmin(this.getAdministrateur());
			f.setVisible(true);
		}*/
	}

}
