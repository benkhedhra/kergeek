package appliUtil;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import metier.Station;
import metier.Utilisateur;
import metier.Velo;

public class FenetreConfirmation extends JFrame implements ActionListener {

	public Utilisateur u;

	public Utilisateur getUtilisateur() {
		return u;
	}

	public void setUtilisateur(Utilisateur u) {
		this.u = u;
	}

	private final JLabel labelConfirm = new JLabel("");
	private final JButton boutonRetourMenuPpal = new JButton ("Retour au menu principal");
	private final JButton boutonRetourMenuLePlusProche = new JButton("");

	public FenetreConfirmation(Utilisateur u, String s,Velo v){
		//Définit un titre pour votre fenêtre
		this.setTitle("Ecran de confirmation");
		//Définit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
		this.setSize(700, 500);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setContentPane(new Panneau());	

		JLabel labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		this.add(labelUtil,BorderLayout.NORTH);

		JLabel labelConfirm = new JLabel (s);

		boutonRetourMenuPpal.addActionListener(this);

		this.add(labelConfirm,BorderLayout.CENTER);
		this.add(boutonRetourMenuPpal);


	}
	public void actionPerformed(ActionEvent arg0,Velo v) {
		try {
			u.emprunteVelo(v,(Station)(v.getLieu()));
			this.dispose();
			MenuUtilisateur m = new MenuUtilisateur(u);
			m.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}