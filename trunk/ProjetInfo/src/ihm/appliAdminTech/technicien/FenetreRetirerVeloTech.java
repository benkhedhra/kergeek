package ihm.appliAdminTech.technicien;

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
import javax.swing.JTextField;

import metier.Garage;
import metier.Lieu;
import metier.Technicien;
import metier.Velo;

public class FenetreRetirerVeloTech extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;

	private JLabel labelTech = new JLabel("");
	private JLabel labelId = new JLabel("Entrer l’identifiant du vélo retiré de sa station");
	private JTextField idARemplir = new JTextField("");
	private JButton boutonValider = new JButton ("Valider");
	private JButton boutonRetour = new JButton ("Retour au Menu Principal");


	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	public FenetreRetirerVeloTech(Technicien t) throws SQLException, ClassNotFoundException{

		System.out.println("Ouverture d'une fenêtre pour retirer un vélo défectueux");

		this.setTechnicien(t);
		this.setContentPane(new PanneauTech());

		//Définit un titre pour votre fenêtre
		this.setTitle("Retirer un vélo défectueux d'une station");
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

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());


		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentification.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,100));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(70,350));
		labelId.setFont(FenetreAuthentificationUtil.POLICE2);
		labelId.setPreferredSize(new Dimension(600,100));
		center.add(labelId);
		idARemplir.setFont(FenetreAuthentificationUtil.POLICE2);
		idARemplir.setPreferredSize(new Dimension(200,40));
		center.add(idARemplir);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
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
				Velo velo;

				velo = DAOVelo.getVeloById(idARemplir.getText());
				if (velo.getLieu().getId()!= Lieu.ID_GARAGE && velo.getLieu().getId()!= Lieu.ID_SORTIE){
					velo.setLieu(Garage.getInstance());
					velo.setEnPanne(true);
				}
				else{
					MsgBox.affMsg("Mauvais identifiant du vélo");
					new FenetreRetirerVeloTech(this.getTechnicien());
				}
				try {
					DAOVelo.updateVelo(velo);
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
				new FenetreConfirmation(this.getTechnicien().getCompte(),this);
			}
			else if(arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (SQLException e1) {
			MsgBox.affMsg(e1.getMessage());
		} catch (ClassNotFoundException e2) {
			MsgBox.affMsg(e2.getMessage());
		}

	}
}
