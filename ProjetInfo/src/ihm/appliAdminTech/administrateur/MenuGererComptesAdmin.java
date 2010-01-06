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

/**
 * MenuGererComptesAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsque l'Administrateur a cliqué "gérer les comptes" de son {@link MenuPrincipalAdmin}
 * <br>elle propose à l'Administrateur 2 choix : créer un nouveau {@link Compte} ou afficher des informations sur un {@link Compte}, par exemple en vue de le modifier
 * @author KerGeek
 */
public class MenuGererComptesAdmin extends JFrame implements ActionListener {

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
	 * 3 JButton proposant les 2 choix possibles à l'administrateur et lui permettant aussi de retourner au menu principal
	 */
	private JButton boutonCreation = new JButton("Création d'un nouveau compte");
	private JButton boutonInformations = new JButton("Afficher informations sur un compte");
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
	 * constructeur de {@link MenuGererComptesAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 */
	public MenuGererComptesAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu gérer comptes de l'administrateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu gérer comptes de l'administrateur");
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
		boutonCreation.setPreferredSize(new Dimension(250,150));
		boutonCreation.setMaximumSize(new Dimension(250,150));
		boutonCreation.setFont(UtilitaireIhm.POLICE3);
		boutonCreation.addActionListener(this);
		center.add(boutonCreation);
		boutonInformations.setPreferredSize(new Dimension(250,150));
		boutonInformations.setMaximumSize(new Dimension(250,150));
		boutonInformations.setFont(UtilitaireIhm.POLICE3);
		boutonInformations.addActionListener(this);
		center.add(boutonInformations);
		this.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,40));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'administrateur a cliqué sur l'un des 4 boutons écoutés par la fenêtre
	 * @param arg0
	 * l'action source
	 * @see FenetreCreationCompteAdmin#FenetreCreationCompteAdmin(Administrateur)
	 * @see FenetreRechercherCompteAdmin#FenetreRechercherCompteAdmin(Administrateur, boolean)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonCreation){
			new FenetreCreationCompteAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonInformations){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),false);
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}

}
