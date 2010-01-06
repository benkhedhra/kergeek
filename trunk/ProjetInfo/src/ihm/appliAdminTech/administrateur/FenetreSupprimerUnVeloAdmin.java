package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Lieu;
import metier.UtilitaireDate;
import metier.Velo;

/**
 * FenetreSupprimerUnVeloAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsque l'Administrateur a voulu déclarer comme supprimé un vélo emprunté
 * <br>pour pouvoir être supprimé, un vélo doit être emprunté depuis plus de 30 jours
 * <br>la suppression d'un vélo étant une action irréversible, une confirmation de la suppression sera demandée
 * @author KerGeek
 */
public class FenetreSupprimerUnVeloAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur a;
	
	/**
	 * vélo à supprimer entré par l'Administrateur
	 */
	private Velo veloEntre = new Velo();
	
	/**
	 * 2 JLabel permettant d'afficher l'id de l'administrateur connecté et le message introduisant le contenu de la fenêtre
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel ("Veuillez entrer l'identifiant du vélo");
	
	/**
	 * TextFieldLimite pour permettre à l'administrateur d'entrer l'identifiant du vélo à supprimer
	 */
	private TextFieldLimite idVeloARemplir = new TextFieldLimite(4,"");
	
	/**
	 * 2 JButton permettant à l'Administrateur de valider la suppression du vélo ou de retourner au menu principal
	 */
	private JButton boutonValider = new JButton ("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	//Accesseurs utiles
	
	/*
	 * attribut administrateur
	 */
	public Administrateur getAdministrateur() {
		return a;
	}

	public void setAdministrateur(Administrateur a) {
		this.a = a;
	}

	/*
	 * attribut veloEntre
	 */
	public Velo getVeloEntre() {
		return veloEntre;
	}

	public void setVeloEntre(Velo veloEntre) {
		this.veloEntre = veloEntre;
	}

	/**
	 * constructeur de {@link FenetreSupprimerUnVeloAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 */
	public FenetreSupprimerUnVeloAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage de la fenêtre pour supprimer un vélo");
		//Définit un titre pour votre fenêtre
		this.setTitle("Suppression d'un vélo");
		//Définit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setPreferredSize(new Dimension(800,50));
		center.add(labelMsg);
		idVeloARemplir.setFont(UtilitaireIhm.POLICE3);
		idVeloARemplir.setPreferredSize(new Dimension(500,40));
		idVeloARemplir.setForeground(Color.BLUE);
		center.add(idVeloARemplir);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);
		this.getContentPane().add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(300,40));
		boutonRetour.setMaximumSize(new Dimension(300,40));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}


	/**
	 * méthode exécutée quand l'administrateur a cliqué sur "Valider" ou sur "Retour au menu principal"
	 * @param arg0
	 * l'action source
	 * @see FenetreDemandeConfirmationAdmin#FenetreDemandeConfirmationAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//il a cliqué sur "Valider"
		if(arg0.getSource()==boutonValider){
			try {
				if(!DAOVelo.existe(idVeloARemplir.getText())){
					//le vélo n'existe pas (pas dans la bdd ou a existé mais a été supprimé)
					System.out.println("id du vélo entré : "+idVeloARemplir.getText());
					MsgBox.affMsg("Le vélo que vous avez entré n'existe pas. ");
					new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
				}
				else{
					//il existe bien (quelque part dans le parc)
					Velo veloEntre = DAOVelo.getVeloById(idVeloARemplir.getText());
					this.setVeloEntre(veloEntre);
					if(!this.getVeloEntre().getLieu().getId().equals(Lieu.ID_SORTIE)){
						//le lieu du vélo n'est pas "sortie"
						MsgBox.affMsg("<html><center>Le vélo que vous avez entré n'est pas en sortie. <br>Il est actuellement au lieu : "+this.getVeloEntre().getLieu().getAdresse()+". </center></html>");
						new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
					}
					else if(this.getVeloEntre().getEmpruntEnCours().getDateEmprunt().after(UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(),30))){
						//le vélo est bien sorti, donc en cours d'emprunt, mais pas depuis plus de 30 jours
						MsgBox.affMsg("<html><center>Le vélo que vous avez entré n'est pas sorti depuis plus de 30 jours. <br>C'est la durée minimale pour avoir le droit de supprimer définitivement un vélo. </center></html>");
						new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
					}
					else{
						// le vélo est dans la nature depuis plus de 30 jours : l'administrateur a la possibilité de le supprimer
						new FenetreDemandeConfirmationAdmin(this.getAdministrateur(),this);
					}
				}
			} 
			catch (SQLException e) {
				MsgBox.affMsg("SQLException"+e.getMessage());
			} 
			catch (ClassNotFoundException e) {
				MsgBox.affMsg("ClassNotFoundException"+e.getMessage());
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//il a cliqué sur "Retour au menu principal"
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}