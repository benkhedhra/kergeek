package ihm.appliUtil;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Utilisateur;

public class MenuUtilisateur extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur = LancerAppliUtil.UTEST;
	private JLabel labelUtil = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JButton boutonChoix = new JButton("");
	Boolean empruntEnCours=false;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public MenuUtilisateur (Utilisateur u){
		
		this.setContentPane(new Panneau());
		System.out.println("Affichage du menu de l'utilisateur");
		//Définit un titre pour notre fenêtre
		this.setTitle("Menu de l'utilisateur");
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

		this.setUtilisateur(u);
		
		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		empruntEnCours = (u.getEmpruntEnCours()!=null);
		boutonChoix.setPreferredSize(new Dimension(350,100));
		boutonChoix.setMaximumSize(new Dimension(350,100));
		boutonChoix.setBackground(Color.CYAN);
		boutonChoix.setFont(FenetreAuthentificationUtil.POLICE2);
				
		if (!empruntEnCours){
			boutonChoix.setText("Emprunter un vélo");
			}
		else {
			boutonChoix.setText("Rendre le vélo emprunté");
		}
		
		boutonChoix.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(boutonChoix);
		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
		}
		else if(!empruntEnCours){
			FenetreEmprunterVelo f = new FenetreEmprunterVelo(this.getUtilisateur());
			f.setVisible(true);
		}
		else if (empruntEnCours){
			FenetreRendreVelo f;
			try {
				f = new FenetreRendreVelo(this.getUtilisateur());
				f.setVisible(true);
			} catch (ConnexionFermeeException e3){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentificationUtil(false);
			}
			
		}

	}
}