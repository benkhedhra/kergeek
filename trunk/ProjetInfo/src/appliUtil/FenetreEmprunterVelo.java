package appliUtil;

import ihm.MsgBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Utilisateur u;   
	private JLabel labelUtil = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du vélo emprunté");
	private JTextField veloARemplir = new JTextField ("");
	private JButton boutonValider = new JButton ("Valider");

	public Utilisateur getUtilisateur() {
		return u;
	}

	public void setUtilisateur(Utilisateur u) {
		this.u = u;
	}

	public FenetreEmprunterVelo(Utilisateur u){

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


		boutonValider.addActionListener(this);
		labelVelo.setFont(FenetreAuthentificationUtil.POLICE2);
		labelVelo.setPreferredSize(new Dimension(350,30));
		veloARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		veloARemplir.setPreferredSize(new Dimension(150,30));
		veloARemplir.setForeground(Color.BLUE);
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelVelo);
		center.add(veloARemplir);
		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setPreferredSize(new Dimension(700,150));
		boutonValider.setPreferredSize(new Dimension(100,30));
		boutonValider.setMaximumSize(new Dimension(100,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		south.add(boutonValider);
		this.getContentPane().add(south,BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent arg0) {
		Velo velo;
		this.dispose();
		if(arg0.getSource()==boutonValider){
			try {
				velo = gestionBaseDeDonnees.DAOVelo.getVeloById(veloARemplir.getText());
				u.emprunteVelo(velo,(Station)(velo.getLieu()));
				FenetreConfirmationUtil f = new FenetreConfirmationUtil("Vous pouvez retirer le vélo "+velo.getId() +" de son emplacement. Merci et à bientôt ! ");
				f.setVisible(true);
				System.out.println("L'emprunt a bien été enregistré");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			}
		}
		else{
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Merci et à bientôt ! ");
			f.setVisible(true);
		}

	}
}