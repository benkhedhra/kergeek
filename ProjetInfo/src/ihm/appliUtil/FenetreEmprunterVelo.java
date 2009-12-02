package ihm.appliUtil;

import exception.CompteBloqueException;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOVelo;
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
	
	private Utilisateur u;
	private JLabel labelUtil = new JLabel("");
	private JButton boutonDeconnexion = new JButton("D�connexion");
	private JLabel labelVelo = new JLabel ("Veuillez entrer l'identifiant du v�lo emprunt�");
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
		System.out.println("Affichage de la fen�tre d'emprunt d'un v�lo");
		//D�finit un titre pour votre fen�tre
		this.setTitle("Emprunter un v�lo");
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

		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+u.getNom());
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


		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelVelo.setPreferredSize(new Dimension(600,30));
		center.add(labelVelo);
		veloARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		veloARemplir.setPreferredSize(new Dimension(150, 30));
		veloARemplir.setForeground(Color.BLUE);
		center.add(veloARemplir);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);
		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		Velo velo;
		this.dispose();
		if(arg0.getSource()==boutonValider){
			try {
				if(DAOVelo.estDisponible(veloARemplir.getText())){
					velo = gestionBaseDeDonnees.DAOVelo.getVeloById(veloARemplir.getText());
					u.emprunteVelo(velo,(Station)(velo.getLieu()));
					DAOEmprunt.createEmprunt(velo.getEmpruntEnCours());
					DAOVelo.updateVelo(velo);
					new FenetreConfirmationUtil("Vous pouvez retirer le v�lo "+velo.getId() +" de son emplacement. Merci et � bient�t ! ");
					System.out.println("L'emprunt a bien �t� enregistr�");
				}
				else {
					MsgBox.affMsg("Saisie incorrecte : le v�lo entr� n'existe pas ou n'est pas disponible");
					new FenetreEmprunterVelo(this.getUtilisateur());
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (CompteBloqueException e) {
				MsgBox.affMsg("CompteBloqueException : " +e.getMessage());
			}
		}
		else{
			new FenetreConfirmationUtil("Merci et � bient�t ! ");
		}

	}
}
