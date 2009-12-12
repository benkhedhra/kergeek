package ihm.appliAdminTech.technicien;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

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

import metier.Technicien;
import metier.Velo;

public class FenetreEnregistrerVeloTech extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;

	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Voulez-vous ajouter un nouveau v�lo ? Il sera affect� au lieu : Garage");
	private JButton boutonValider = new JButton ("Oui");
	private JButton boutonRetour = new JButton ("Retour au Menu Principal");
	private Velo veloEntre;


	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}
	
	public Velo getVeloEntre() {
		return veloEntre;
	}

	public void setVeloEntre(Velo veloEntre) {
		this.veloEntre = veloEntre;
	}

	public FenetreEnregistrerVeloTech(Technicien t) throws SQLException, ClassNotFoundException{

		System.out.println("Ouverture d'une fen�tre d'enregistrement de v�lo du technicien");

		this.setTechnicien(t);
		this.setContentPane(new PanneauTech());

		//D�finit un titre pour votre fen�tre
		this.setTitle("Enregistrer un nouveau v�lo");
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

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());


		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentification.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,100));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		labelMsg.setPreferredSize(new Dimension(600,30));
		labelMsg.setMaximumSize(new Dimension(600,30));
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		center.add(labelMsg);	
		boutonValider.setPreferredSize(new Dimension(150,30));
		boutonValider.setMaximumSize(new Dimension(150,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if(arg0.getSource()==boutonValider){
				veloEntre = this.getTechnicien().enregistrerVelo();
				DAOVelo.createVelo(veloEntre);
				new FenetreConfirmation(this.getTechnicien().getCompte(),this);
			}
			else if(arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}
