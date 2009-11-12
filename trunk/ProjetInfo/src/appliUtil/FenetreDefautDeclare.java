package appliUtil;

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

public class FenetreDefautDeclare extends JFrame implements ActionListener {

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

	public FenetreDefautDeclare (Utilisateur u) {

		this.setUtilisateur(u);
		System.out.println("Le temps d'emprunt a été très court");
		this.setTitle("Temps d'emprunt < 2 minutes");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.setContentPane(new Panneau());	
		this.getContentPane().setLayout(new BorderLayout());

		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		this.getContentPane().add(labelUtil,BorderLayout.NORTH);

		labelMsg.setText("La présence d’un défaut a bien été signalée. Souhaitez-vous emprunter un autre vélo ? ");
		boutonOui.setPreferredSize(new Dimension(200,150));
		boutonNon.setPreferredSize(new Dimension(200,150));
		
		boutonOui.addActionListener(this);
		boutonNon.addActionListener(this);
		JPanel center = new JPanel();
		center.add(labelMsg);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonOui){
			FenetreEmprunterVelo f = new FenetreEmprunterVelo(utilisateur);
			f.setVisible(true);
		}
		else if (arg0.getSource()==boutonNon){
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Au revoir et à bientôt ! ");
			f.setVisible(true);
		}
	}
}