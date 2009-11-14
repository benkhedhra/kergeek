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
import metier.Compte;
import appliUtil.FenetreAuthentificationUtil;

public class MenuInterventionsMaintenanceAdmin extends JFrame implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Administrateur admin;
		private JLabel labelAdmin = new JLabel("");
		private JButton boutonRetour = new JButton("Retour au menu principal");
		private JButton boutonHistorique = new JButton("Historique des interventions sur un vélo");
		private JButton boutonInterventions = new JButton("Interventions les plus fréquentes");

		public Administrateur getAdministrateur() {
			return admin;
		}

		public void setAdministrateur(Administrateur admin) {
			this.admin = admin;
		}

		public MenuInterventionsMaintenanceAdmin(Administrateur a){

			this.setContentPane(new PanneauAdmin());
			System.out.println("Menu interventions de maintenance de l'administrateur");
			//Définit un titre pour notre fenêtre
			this.setTitle("Menu <interventions de maintenance> de l'administrateur");
			//Définit une taille pour celle-ci
			this.setSize(new Dimension(700,500));		
			this.setMinimumSize(new Dimension(700,500));
			//Terminer le processus lorsqu'on clique sur "Fermer"
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//Nous allons maintenant dire à notre objet de se positionner au centre
			this.setLocationRelativeTo(null);
			//pour que la fenêtre ne se redimensionne pas à chaque fois
			this.setResizable(false);
			//pour que la fenêtre soit toujours au premier plan
			this.setAlwaysOnTop(true);

			// on définit un BorderLayout
			this.getContentPane().setLayout(new BorderLayout());

			this.setAdministrateur(a);

				
			labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
			labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
			labelAdmin.setPreferredSize(new Dimension(500,30));
			JPanel north = new JPanel();
			north.setPreferredSize(new Dimension(700,150));
			north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			north.add(labelAdmin);
			this.getContentPane().add(north,BorderLayout.NORTH);

			JPanel center = new JPanel();
			center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			boutonHistorique.setPreferredSize(new Dimension(250,150));
			boutonHistorique.setMaximumSize(new Dimension(250,150));
			boutonHistorique.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonHistorique.addActionListener(this);
			center.add(boutonHistorique);
			boutonInterventions.setPreferredSize(new Dimension(250,150));
			boutonInterventions.setMaximumSize(new Dimension(250,150));
			boutonInterventions.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonInterventions.addActionListener(this);
			center.add(boutonInterventions);
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
		/*else if (arg0.getSource()==boutonHistorique){
			new FenetreHistoriqueVeloAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonInterventions){
			new FenetreAffichageResultats(this);
		}*/
	}

}
