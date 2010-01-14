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
import metier.Compte;

/**
 * MenuGererComptesAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle intervient lorsque l'Administrateur a cliqu� "g�rer les comptes" de son {@link MenuPrincipalAdmin}
 * <br>elle propose � l'Administrateur 2 choix : cr�er un nouveau {@link Compte} ou afficher des informations sur un {@link Compte}, par exemple en vue de le modifier
 * @author KerGeek
 */
public class MenuGererComptesAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connect� sur la fen�tre
	 */
	private Administrateur admin;
	
	/**
	 * 2 JLabel permettant d'afficher l'id de l'administrateur connect� et l'endroit de l'application o� il se trouve
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > G�rer les comptes");
	
	/**
	 * 3 JButton proposant les 2 choix possibles � l'administrateur et lui permettant aussi de retourner au menu principal
	 */
	private JButton boutonCreation = new JButton("Cr�ation d'un nouveau compte");
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
	 * l'administrateur connect� � la fen�tre
	 */
	public MenuGererComptesAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu g�rer comptes de l'administrateur");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Menu g�rer comptes de l'administrateur");
		//D�finit une taille pour celle-ci
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
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
		boutonCreation.setPreferredSize(new Dimension(300,200));
		boutonCreation.setMaximumSize(new Dimension(300,200));
		boutonCreation.setFont(UtilitaireIhm.POLICE3);
		boutonCreation.addActionListener(this);
		center.add(boutonCreation);
		boutonInformations.setPreferredSize(new Dimension(300,200));
		boutonInformations.setMaximumSize(new Dimension(300,200));
		boutonInformations.setFont(UtilitaireIhm.POLICE3);
		boutonInformations.addActionListener(this);
		center.add(boutonInformations);
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
	 * m�thode ex�cut�e quand l'administrateur a cliqu� sur l'un des 4 boutons �cout�s par la fen�tre
	 * @param arg0
	 * l'action source
	 * @see FenetreCreationCompteAdmin#FenetreCreationCompteAdmin(Administrateur)
	 * @see FenetreRechercherCompteAdmin#FenetreRechercherCompteAdmin(Administrateur, boolean)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource()==boutonCreation){
			new FenetreCreationCompteAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonInformations){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),false);
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
		this.dispose();
	}

}
