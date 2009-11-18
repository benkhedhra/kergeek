package ihm.appliUtil;

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

public class FenetreEmpruntCourt extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur = LancerAppliUtil.UTEST;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (Déconnexion)");

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public FenetreEmpruntCourt (Utilisateur u){

		this.setContentPane(new Panneau());
		System.out.println("Le temps d'emprunt a été très court");
		//Définit un titre pour notre fenêtre
		this.setTitle("Temps d'emprunt < 2 minutes");
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
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelUtil);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setText("Vous avez emprunté ce vélo pendant un laps de temps très court. Souhaitez-vous signaler un défaut sur celui-ci ? ");
		labelMsg.setPreferredSize(new Dimension(650,30));
		boutonOui.setPreferredSize(new Dimension(150,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(150,50));
		boutonNon.setBackground(Color.CYAN);
		
		boutonOui.addActionListener(this);
		boutonNon.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelMsg);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonOui){
			FenetreDefautDeclare f = new FenetreDefautDeclare(utilisateur);
			f.setVisible(true);
		}
		else if (arg0.getSource()==boutonNon){
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
			f.setVisible(true);
		}
	}
	
	public static void main (String[]args){
		new FenetreEmpruntCourt(LancerAppliUtil.UTEST);
	}
}
