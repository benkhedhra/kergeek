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
import appliAdminTech.FenetreConfirmation;
import appliUtil.FenetreAuthentificationUtil;

public class MenuPrincipalAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JButton boutonComptes = new JButton("Gérer les comptes");
	private JButton boutonStats = new JButton("Demander des statistiques");
	private JButton boutonEtat = new JButton("Voir l'état actuel des stations et des vélos");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuPrincipalAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage du menu de l'administrateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu de l'administrateur");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setAdministrateur(a);

			
		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonComptes.setPreferredSize(new Dimension(200,120));
		boutonComptes.setMaximumSize(new Dimension(200,120));
		boutonComptes.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonComptes.addActionListener(this);
		center.add(boutonComptes);
		boutonStats.setPreferredSize(new Dimension(200,120));
		boutonStats.setMaximumSize(new Dimension(200,120));
		boutonStats.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonStats.addActionListener(this);
		center.add(boutonStats);
		boutonEtat.setPreferredSize(new Dimension(200,120));
		boutonEtat.setMaximumSize(new Dimension(200,120));
		boutonEtat.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonEtat.addActionListener(this);
		center.add(boutonEtat);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//la suite est mise en commentaire car les classes correspondantes ne sont pas écrites

		if(arg0.getSource()==boutonComptes){
			MenuGererComptesAdmin m = new MenuGererComptesAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		else if (arg0.getSource()==boutonStats){
			MenuDemanderStatsAdmin m = new MenuDemanderStatsAdmin(this.getAdministrateur());
			m.setVisible(true);
		}
		else if (arg0.getSource()==boutonEtat){
			MenuVoirEtatAdmin m = new MenuVoirEtatAdmin(this.getAdministrateur());
			m.setVisible(true);
		}		
		/*else if (arg0.getSource()==boutonDeconnexion){
			FenetreConfirmation f = new FenetreConfirmation("Au revoir et à bientôt ! ");
			f.setVisible(true);
		}*/
	}
	
	public static void main (String [] args){
		Administrateur ATEST = new Administrateur(new Compte("administrateurTest","",Compte.TYPE_ADMINISTRATEUR));
		new MenuPrincipalAdmin(ATEST);
	}
}