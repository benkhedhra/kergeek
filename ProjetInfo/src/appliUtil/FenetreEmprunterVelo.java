package appliUtil;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import metier.Station;
import metier.Utilisateur;
import metier.Velo;

public class FenetreEmprunterVelo extends JFrame implements ActionListener {

	public Utilisateur u;

	public Utilisateur getUtilisateur() {
		return u;
	}

	public void setUtilisateur(Utilisateur u) {
		this.u = u;
	}

	JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du v�lo emprunt�");
	JTextField veloARemplir = new JTextField ("");
	JButton boutonValider = new JButton ("Valider");

	public FenetreEmprunterVelo(Utilisateur u){
		//D�finit un titre pour votre fen�tre
		this.setTitle("Emprunter un v�lo");
		//D�finit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
		this.setSize(700, 500);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setContentPane(new Panneau());	

		JLabel labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		this.add(labelUtil,BorderLayout.NORTH);

		JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du v�lo emprunt�");
		JTextField veloARemplir = new JTextField ("");
		JButton boutonValider = new JButton ("Valider");
		boutonValider.addActionListener(this);

		this.add(labelVelo);
		this.add(veloARemplir);
		this.add(boutonValider);

	}

	public void actionPerformed(ActionEvent arg0) {
		Velo velo;
		try {
			velo = gestionBaseDeDonnees.DAOVelo.getVeloById(veloARemplir.getText());
			u.emprunteVelo(velo,(Station)(velo.getLieu()));
			this.dispose();
			FenetreConfirmation f = new FenetreConfirmation(u,"Vous pouvez retirer le v�lo de la station. Merci et � bient�t ! ",velo);
			f.setVisible(true);
			System.out.println("l'emprunt a bien �t� enregistr�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}