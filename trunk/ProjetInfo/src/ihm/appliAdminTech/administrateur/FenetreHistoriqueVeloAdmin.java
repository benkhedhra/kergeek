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
import javax.swing.JTextField;

import metier.Administrateur;
import metier.Velo;

public class FenetreHistoriqueVeloAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Administrateur a;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel ("Veuillez entrer l'identifiant du vélo");
	private JTextField idVeloARemplir = new JTextField ("");
	private JButton boutonAfficher = new JButton ("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private Velo veloEntre = new Velo();

	public Administrateur getAdministrateur() {
		return a;
	}

	public void setAdministrateur(Administrateur a) {
		this.a = a;
	}
	
	public Velo getVeloEntre() {
		return veloEntre;
	}

	public void setVeloEntre(Velo veloEntre) {
		this.veloEntre = veloEntre;
	}

	public FenetreHistoriqueVeloAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage de la fenêtre d'historique d'un vélo");
		//Définit un titre pour votre fenêtre
		this.setTitle("Historique d'un vélo");
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
		labelAdmin.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setPreferredSize(new Dimension(600,30));
		center.add(labelMsg);
		idVeloARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		idVeloARemplir.setPreferredSize(new Dimension(150, 30));
		idVeloARemplir.setForeground(Color.BLUE);
		center.add(idVeloARemplir);
		boutonAfficher.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonAfficher.setBackground(Color.CYAN);
		boutonAfficher.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonAfficher.addActionListener(this);
		center.add(boutonAfficher);
		this.getContentPane().add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonAfficher){
			//new FenetreAffichageResultats(this,this.getAdministrateur().getCompte());
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}
