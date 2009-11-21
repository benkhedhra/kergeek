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

public class MenuVoirEtatAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private JButton boutonStationsSurSous = new JButton("Stations sur et sous occupées");
	private JButton boutonEtatStation = new JButton("Etat d'une station");
	private JButton boutonVelosSortis = new JButton("Liste des vélos sortis");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuVoirEtatAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu demander statistiques de l'administrateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu demander statistiques de l'administrateur");
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
		boutonStationsSurSous.setPreferredSize(new Dimension(200,120));
		boutonStationsSurSous.setMaximumSize(new Dimension(200,120));
		boutonStationsSurSous.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonStationsSurSous.addActionListener(this);
		center.add(boutonStationsSurSous);
		boutonEtatStation.setPreferredSize(new Dimension(200,120));
		boutonEtatStation.setMaximumSize(new Dimension(200,120));
		boutonEtatStation.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonEtatStation.addActionListener(this);
		center.add(boutonEtatStation);
		boutonVelosSortis.setPreferredSize(new Dimension(200,120));
		boutonVelosSortis.setMaximumSize(new Dimension(200,120));
		boutonVelosSortis.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonVelosSortis.addActionListener(this);
		center.add(boutonVelosSortis);
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
		/*else if (arg0.getSource()==boutonStationsSurSous){
			new FenetreStationsSurSousAdmin(MenuInterventionsMaintenanceAdmin );
		}
		else if (arg0.getSource()==boutonEtatStation){
			new FenetreEtatStationAdmin(MenuInterventionsMaintenanceAdmin );
		}
		else if (arg0.getSource()==boutonVelosSortis){
			new FenetreAffichageResultats(this);
		}*/
	}

}
