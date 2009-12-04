package ihm.appliAdminTech.administrateur;

import ihm.appliAdminTech.FenetreChangerMotDePasse;
import ihm.appliAdminTech.FenetreConfirmation;
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

public class MenuPrincipalAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonDeconnexion = new JButton("D�connexion");
	private JButton boutonComptes = new JButton("G�rer les comptes");
	private JButton boutonStats = new JButton("Demander des statistiques");
	private JButton boutonEtat = new JButton("Voir l'�tat actuel des stations et des v�los");
	private JButton boutonChangeMdp = new JButton("Changer le mot de passe");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuPrincipalAdmin(Administrateur a){


		this.setAdministrateur(a);
		
		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage du menu principal de l'administrateur");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Menu principal de l'administrateur");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


			
		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,100));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		north.setPreferredSize(new Dimension(700,350));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonComptes.setPreferredSize(new Dimension(210,130));
		boutonComptes.setMaximumSize(new Dimension(210,130));
		boutonComptes.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonComptes.addActionListener(this);
		center.add(boutonComptes);
		boutonStats.setPreferredSize(new Dimension(210,130));
		boutonStats.setMaximumSize(new Dimension(210,130));
		boutonStats.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonStats.addActionListener(this);
		center.add(boutonStats);
		boutonEtat.setPreferredSize(new Dimension(210,130));
		boutonEtat.setMaximumSize(new Dimension(210,130));
		boutonEtat.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonEtat.addActionListener(this);
		center.add(boutonEtat);
		this.add(center, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,50));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonChangeMdp.setPreferredSize(new Dimension(150,30));
		boutonChangeMdp.setMaximumSize(new Dimension(150,30));
		boutonChangeMdp.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonChangeMdp.addActionListener(this);
		south.add(boutonChangeMdp);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//la suite est mise en commentaire car les classes correspondantes ne sont pas �crites

		if(arg0.getSource()==boutonComptes){
			new MenuGererComptesAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonStats){
			new MenuDemanderStatsAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonEtat){
			new MenuVoirEtatAdmin(this.getAdministrateur());
		}	
		else if (arg0.getSource()==boutonChangeMdp){
			new FenetreChangerMotDePasse(this.getAdministrateur().getCompte());
		}
		else if (arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
		}
	}
}