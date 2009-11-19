package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAOVelo;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import metier.Technicien;
import metier.Velo;

public class FenetreEnregistrerVeloTech extends JFrame implements ActionListener{

	private Technicien technicien;

	// définition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 18);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 16);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,14);

	private JLabel labelId = new JLabel("Le nouveau vélo est enregistré avec l'identifiant");
	private JLabel labelTech = new JLabel("");
	private JLabel id = new JLabel("");
	private JButton boutonRetour = new JButton ("Retour au Menu Principal");


	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	public FenetreEnregistrerVeloTech(Technicien tech) throws SQLException, ClassNotFoundException{
		System.out.println("Ouverture d'une fenêtre d'enregistrement de velo du technicien");
		this.setTitle("Enregistrement du nouveau velo");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		

		labelTech = new JLabel("Vous êtes connecté en tant que "+ tech.getCompte().getId());
		labelTech.setFont(FenetreAuthentification.POLICE4);
		this.getContentPane().add(labelTech,BorderLayout.NORTH);
		
		Velo velo = new Velo();
		DAOVelo.createVelo(velo);

		id.setFont(POLICE1);
		id.setText(velo.getId());
		this.getContentPane().add(id,BorderLayout.CENTER);

		labelId.setFont(POLICE3);
		this.getContentPane().add(labelId,BorderLayout.CENTER);

		boutonRetour.setPreferredSize(new Dimension(50,50));
		this.getContentPane().add(boutonRetour,BorderLayout.SOUTH);
		//On ajoute notre Fenetre à la liste des auditeurs de notre Bouton
		boutonRetour.addActionListener(this);

		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==boutonRetour){
			MenuPrincipalTech menuPrincip = new MenuPrincipalTech(this.getTechnicien());
			menuPrincip.setVisible(true);
		}
	}
}
