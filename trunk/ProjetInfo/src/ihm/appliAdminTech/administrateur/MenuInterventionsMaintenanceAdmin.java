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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Velo;

/**
 * MenuInterventionsMaintenanceAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsque l'Administrateur a cliqué "interventions de maintenance" de son {@link MenuDemanderStatsAdminc}
 * <br>elle propose à l'Administrateur 2 choix : voir l'historique des interventions effectuées sur un {@link Velo} ou afficher sous forme d'histogramme la fréquence des interventions par type
 * @author KerGeek
 */
public class MenuInterventionsMaintenanceAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur admin;
	
	/**
	 * 2 JLabel permettant d'afficher l'id de l'administrateur connecté et l'endroit de l'application où il se trouve
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Demander des statistiques > Maintenance");
	
	/**
	 * 3 JButton proposant les 2 choix possibles à l'administrateur et lui permettant aussi de retourner au menu principal
	 */

	private JButton boutonHistorique = new JButton("<html> <center>Historique des<br>interventions<br>sur un vélo</center></html>");
	private JButton boutonInterventions = new JButton("<html> <center>Nombre d'interventions<br>par type<br>sur les 6 derniers mois</center></html>");
	
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
	 * constructeur de {@link MenuInterventionsMaintenanceAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 */
	public MenuInterventionsMaintenanceAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu interventions de maintenance de l'administrateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu <interventions de maintenance> de l'administrateur");
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
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,300));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonHistorique.setPreferredSize(new Dimension(300,200));
		boutonHistorique.setMaximumSize(new Dimension(300,200));
		boutonHistorique.setFont(UtilitaireIhm.POLICE3);
		boutonHistorique.addActionListener(this);
		center.add(boutonHistorique);
		boutonInterventions.setPreferredSize(new Dimension(300,200));
		boutonInterventions.setMaximumSize(new Dimension(300,200));
		boutonInterventions.setFont(UtilitaireIhm.POLICE3);
		boutonInterventions.addActionListener(this);
		center.add(boutonInterventions);
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
	 * méthode exécutée quand l'administrateur a cliqué sur l'un des 3 boutons écoutés par la fenêtre
	 * @param arg0
	 * l'action source
	 * @see FenetreCreationCompteAdmin#FenetreCreationCompteAdmin(Administrateur)
	 * @see FenetreRechercherCompteAdmin#FenetreRechercherCompteAdmin(Administrateur, boolean)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqué sur "Voir l'historique des interventions sur un vélo"
		if (arg0.getSource()==boutonHistorique){
			new FenetreHistoriqueVeloAdmin(this.getAdministrateur());
		}
		//s'il a cliqué sur "Voir les interventions les plus fréquentes"
		else if (arg0.getSource()==boutonInterventions){
			try {
				new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//s'il a cliqué sur "Retour au milieu principal"
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}