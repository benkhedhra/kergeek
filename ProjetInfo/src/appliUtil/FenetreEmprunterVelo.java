package appliUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

	JLabel labelUtil = new JLabel("");
	JButton boutonDeconnexion = new JButton("D�connexion");
	JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du v�lo emprunt�");
	JTextField veloARemplir = new JTextField ("");
	JButton boutonValider = new JButton ("Valider");

	public FenetreEmprunterVelo(Utilisateur u){

		this.setUtilisateur(u);
		System.out.println("Affichage de la fen�tre d'emprunt d'un v�lo");
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
		this.setLayout(new BorderLayout());

		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		boutonValider.addActionListener(this);
		veloARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		veloARemplir.setPreferredSize(new Dimension(150, 30));
		veloARemplir.setForeground(Color.BLUE);
		JPanel center = new JPanel();
		center.add(labelVelo);
		center.add(veloARemplir);
		this.getContentPane().add(center,BorderLayout.CENTER);

		this.getContentPane().add(boutonValider,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		Velo velo;
		this.dispose();
		if(arg0.getSource()==boutonValider){
			try {
				velo = gestionBaseDeDonnees.DAOVelo.getVeloById(veloARemplir.getText());
				u.emprunteVelo(velo,(Station)(velo.getLieu()));
				FenetreConfirmationUtil f = new FenetreConfirmationUtil("Vous pouvez retirer le v�lo "+velo.getId() +" de son emplacement. Merci et � bient�t ! ");
				f.setVisible(true);
				System.out.println("L'emprunt a bien �t� enregistr�");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Merci et � bient�t ! ");
			f.setVisible(true);
		}

	}
}