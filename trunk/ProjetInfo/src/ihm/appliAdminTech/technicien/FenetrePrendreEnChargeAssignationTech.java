package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliUtil.FenetreAuthentificationUtil;

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

import metier.DemandeAssignation;
import metier.Technicien;

public class FenetrePrendreEnChargeAssignationTech extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JLabel labelVelo = new JLabel("");
	private JTextField veloARemplir = new JTextField();
	private JButton boutonValider = new JButton("Confirmer les déplacements");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}


	public DemandeAssignation getDemande() {
		return demande;
	}

	public void setDemande(DemandeAssignation d) {
		this.demande = d;
	}

	public FenetrePrendreEnChargeAssignationTech(Technicien t, DemandeAssignation d){
		System.out.println("Fenêtre pour prendre en charge une demande d'assignation");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Prendre en charge une assignation");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setTechnicien(t);
		this.setDemande(d);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelTech.setPreferredSize(new Dimension(300,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		int nbVelosADeplacer;

		try {
			nbVelosADeplacer = Math.abs(demande.getNombreVelosVoulusDansStation()-DAOVelo.getVelosByLieu(demande.getLieu()).size());

			labelMsg.setPreferredSize(new Dimension(600,50));
			labelMsg.setMaximumSize(new Dimension(600,50));
			labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);

			if(nbVelosADeplacer==0){
				labelMsg.setText("Il n'y a aucun vélo à déplacer. Le nombre de vélos dans la station est le nombre voulu. ");
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);
			}
			else{
				labelMsg.setText("Veuillez entrer les identifiants des vélos affectés du garage à la station "+d.getLieu().getId());
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);

				JPanel centerWest = new JPanel();
				centerWest.setPreferredSize(new Dimension(550,350));
				centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

				centerWest.setLayout(new GridLayout(nbVelosADeplacer,2));

				labelVelo.setPreferredSize(new Dimension(200,30));
				veloARemplir.setPreferredSize(new Dimension(300,30));

				JPanel panel1 = new JPanel();
				panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
				JPanel panel2 = new JPanel();
				panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

				for (int i =0;i<nbVelosADeplacer;i++){
					labelVelo.setText("Vélo "+(i+1));
					panel1.add(labelVelo);
					centerWest.add(panel1);
					panel2.add(veloARemplir);
					centerWest.add(panel2);
				}
				center.add(centerWest,BorderLayout.WEST);
				
				JPanel centerSouth = new JPanel();
				centerSouth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.setBackground(Color.CYAN);
				boutonValider.addActionListener(this);
				centerSouth.add(boutonValider);
				center.add(centerSouth, BorderLayout.SOUTH);
				
			}

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}

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
		if(arg0.getSource()==boutonValider){
			demande.setPriseEnCharge(true);
			DAODemandeAssignation.updateDemandeAssignation(demande);
			new FenetreConfirmation(this.getTechnicien().getCompte(),this);
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}