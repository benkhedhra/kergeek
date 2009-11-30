package ihm.appliAdminTech.technicien;

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

public class MenuPrincipalTech extends JFrame implements ActionListener {

	private Technicien tech;
	private JLabel labelTech = new JLabel("");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private JButton bouton1 = new JButton("Enregistrer \n"+"un nouveau vélo");
	private JButton bouton2 = new JButton("Retirer un vélo \n"+"défectueux \n"+" d'une station");
	private JButton bouton3 = new JButton("Remettre un vélo \n"+"réparé\n"+" dans une station");
	private JButton bouton4 = new JButton("Gérer les demandes\n"+" d'assignation");
	private JButton bouton5 = new JButton("Gérer les demandes\n"+" d'intervention");

	public Technicien getTechnicien() {
		return tech;
	}

	public void setTechnicien(Technicien tech) {
		this.tech = tech;
	}

	public MenuPrincipalTech(Technicien t){

		this.setTechnicien(t);
		this.setContentPane(new PanneauTech());
		System.out.println("Affichage du menu principal du technicien");
		//Définit un titre pour votre fenêtre
		this.setTitle("Menu principal du technicien");
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
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		bouton1.setPreferredSize(new Dimension(200,100));
		bouton1.setMaximumSize(new Dimension(200,100));
		bouton1.setFont(FenetreAuthentificationUtil.POLICE3);
		bouton1.addActionListener(this);
		center.add(bouton1);
		bouton2.setPreferredSize(new Dimension(200,100));
		bouton2.setMaximumSize(new Dimension(200,100));
		bouton2.setFont(FenetreAuthentificationUtil.POLICE3);
		bouton2.addActionListener(this);
		center.add(bouton2);
		bouton3.setPreferredSize(new Dimension(200,100));
		bouton3.setMaximumSize(new Dimension(200,100));
		bouton3.setFont(FenetreAuthentificationUtil.POLICE3);
		bouton3.addActionListener(this);
		center.add(bouton3);
		bouton4.setPreferredSize(new Dimension(200,100));
		bouton4.setMaximumSize(new Dimension(200,100));
		bouton4.setFont(FenetreAuthentificationUtil.POLICE3);
		bouton4.addActionListener(this);
		center.add(bouton4);
		bouton5.setPreferredSize(new Dimension(200,100));
		bouton5.setMaximumSize(new Dimension(200,100));
		bouton5.setFont(FenetreAuthentificationUtil.POLICE3);
		bouton5.addActionListener(this);
		center.add(bouton5);

		this.getContentPane().add(center,BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==bouton1){
			try {
				new FenetreEnregistrerVeloTech(this.getTechnicien());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
		}
		else if (arg0.getSource()==bouton2){
			try {
				new FenetreRetirerVeloTech(this.getTechnicien());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
		}
		else if (arg0.getSource()==bouton3){
			new FenetreRemettreVeloEnStationTech(this.getTechnicien());
		}
		else if (arg0.getSource()==bouton4){
			new FenetreGererAssignationsTech(this.getTechnicien());
		}
		else if (arg0.getSource()==bouton5){
			new FenetreGererInterventionsTech(this.getTechnicien());
		}	
		else if (arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmation(this.getTechnicien().getCompte(), this);
		}
	}
}