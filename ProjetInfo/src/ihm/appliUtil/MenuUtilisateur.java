package ihm.appliUtil;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;

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
	private JButton boutonDeconnexion = new JButton("D�connexion");
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
		//D�finit un titre pour notre fen�tre
		this.setTitle("Menu de l'utilisateur");
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

		this.setUtilisateur(u);
		
		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(UtilitaireIhm.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(UtilitaireIhm.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		empruntEnCours = (u.getEmpruntEnCours()!=null);
		boutonChoix.setPreferredSize(new Dimension(350,100));
		boutonChoix.setMaximumSize(new Dimension(350,100));
		boutonChoix.setBackground(Color.CYAN);
		boutonChoix.setFont(UtilitaireIhm.POLICE2);
				
		if (!empruntEnCours){
			boutonChoix.setText("Emprunter un v�lo");
			}
		else {
			boutonChoix.setText("Rendre le v�lo emprunt�");
		}
		
		boutonChoix.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(boutonChoix);
		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmationUtil("Au revoir et � bient�t ! ");
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
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentificationUtil(false);
			}
			
		}

	}
}