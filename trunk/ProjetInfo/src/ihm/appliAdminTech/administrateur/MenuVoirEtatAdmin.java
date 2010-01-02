package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

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
	private JButton boutonStationsSurSous = new JButton("<html> <center>Stations<br>sur et sous-occup�es</center></html>");
	private JButton boutonEtatStation = new JButton("Etat d'un lieu");
	private JButton boutonVelos = new JButton("Liste des v�los par lieu");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuVoirEtatAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Menu <voir �tat du parc> de l'administrateur");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Menu <voir �tat du parc> de l'administrateur");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre se redimensionne � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);


		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonStationsSurSous.setPreferredSize(new Dimension(200,120));
		boutonStationsSurSous.setMaximumSize(new Dimension(200,120));
		boutonStationsSurSous.setFont(UtilitaireIhm.POLICE3);
		boutonStationsSurSous.addActionListener(this);
		center.add(boutonStationsSurSous);
		boutonEtatStation.setPreferredSize(new Dimension(200,120));
		boutonEtatStation.setMaximumSize(new Dimension(200,120));
		boutonEtatStation.setFont(UtilitaireIhm.POLICE3);
		boutonEtatStation.addActionListener(this);
		center.add(boutonEtatStation);
		boutonVelos.setPreferredSize(new Dimension(200,120));
		boutonVelos.setMaximumSize(new Dimension(200,120));
		boutonVelos.setFont(UtilitaireIhm.POLICE3);
		boutonVelos.addActionListener(this);
		center.add(boutonVelos);
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

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonRetour){
			MenuPrincipalAdmin m = new MenuPrincipalAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		else if (arg0.getSource()==boutonStationsSurSous){
			try {
				new FenetreStationsSurSousAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonEtatStation){
			try {
				new FenetreEtatLieuAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonVelos){
			try {
				new FenetreVoirVelosDansLieuAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
	}

}
