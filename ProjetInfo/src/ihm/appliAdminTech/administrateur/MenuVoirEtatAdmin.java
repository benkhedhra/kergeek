package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

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

public class MenuVoirEtatAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur admin;
	
	/**
	 * 1 JLabel permettant d'afficher l'id de l'administrateur connecté
	 */
	private JLabel labelAdmin = new JLabel("");
	
	/**
	 * 4 JButton proposant les 3 choix possibles à l'administrateur et lui permettant aussi de retourner au menu principal
	 */
	private JButton boutonStationsSurSous = new JButton("<html> <center>Stations<br>sur et sous-occupées</center></html>");
	private JButton boutonEtatLieu = new JButton("<html> <center>Voir l'état d'une station<br>ou du garage</center></html>");
	private JButton boutonVelos = new JButton("Liste des vélos par lieu");

	private JButton boutonRetour = new JButton("Retour au menu principal");

	//Accesseurs utiles
	
	/*
	 * attribut administrateur
	 */
	
	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	/**
	 * constructeur de {@link MenuVoirEtatAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 */
	public MenuVoirEtatAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu <voir état du parc> de l'administrateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu <voir état du parc> de l'administrateur");
		//Définit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre se redimensionne à chaque fois
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,300));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,600));
		boutonStationsSurSous.setPreferredSize(new Dimension(300,200));
		boutonStationsSurSous.setMaximumSize(new Dimension(300,200));
		boutonStationsSurSous.setFont(UtilitaireIhm.POLICE3);
		boutonStationsSurSous.addActionListener(this);
		center.add(boutonStationsSurSous);
		boutonEtatLieu.setPreferredSize(new Dimension(300,200));
		boutonEtatLieu.setMaximumSize(new Dimension(300,200));
		boutonEtatLieu.setFont(UtilitaireIhm.POLICE3);
		boutonEtatLieu.addActionListener(this);
		center.add(boutonEtatLieu);
		boutonVelos.setPreferredSize(new Dimension(300,200));
		boutonVelos.setMaximumSize(new Dimension(300,200));
		boutonVelos.setFont(UtilitaireIhm.POLICE3);
		boutonVelos.addActionListener(this);
		center.add(boutonVelos);
		this.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'administrateur a cliqué sur l'un des 4 boutons écoutés par la fenêtre
	 * @param arg0
	 * l'action source
	 * @see FenetreStationsSurSousAdmin#FenetreStationsSurSousAdmin(Administrateur)
	 * @see FenetreEtatLieuAdmin#FenetreEtatLieuAdmin(Administrateur)
	 * @see FenetreVoirVelosDansLieuAdmin#FenetreVoirVelosDansLieuAdmin(Administrateur)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqué sur "stations sur et sous-occupées"
		if (arg0.getSource()==boutonStationsSurSous){
			try {
				new FenetreStationsSurSousAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//s'il a cliqué sur "voir l'état d'une station ou du garage"
		else if (arg0.getSource()==boutonEtatLieu){
			try {
				new FenetreEtatLieuAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonVelos){
			try {
				new FenetreVoirVelosDansLieuAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}