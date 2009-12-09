package ihm.appliAdminTech.technicien;

import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Intervention;
import metier.Technicien;

public class FenetreGererUneInterventionTech extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private Intervention intervention;
	private JLabel labelTech = new JLabel("");;
	private JLabel labelMsg = new JLabel("Intervention à traiter");
	private JLabel labelStation = new JLabel("Station concernée");
	private JLabel labelStationDemande;
	private JLabel labelVelo = new JLabel("Vélo concerné");
	private JLabel labelVeloDemande;
	private JLabel labelDate = new JLabel("Date de la déclaration du défaut");
	private JLabel labelDateDemande;
	private JButton boutonPrendreEnCharge = new JButton("Prendre en charge cette intervention");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public FenetreGererUneInterventionTech(Technicien t,Intervention i){

		System.out.println("Fenêtre pour gérer une intervention");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer une intervention");
		//Définit une taille pour celle-ci
		this.setPreferredSize(new Dimension(700,500));		
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
		this.setIntervention(i);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
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
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerWest.setLayout(new GridLayout(3,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelStation.setPreferredSize(new Dimension(150,30));
		labelStation.setMaximumSize(new Dimension(150,30));
		panel1.add(labelStation);
		centerWest.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelStationDemande.setPreferredSize(new Dimension(150,30));
		labelStationDemande.setMaximumSize(new Dimension(150,30));
		panel2.add(labelStationDemande);
		centerWest.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelVelo.setPreferredSize(new Dimension(150,30));
		labelVelo.setMaximumSize(new Dimension(150,30));
		panel3.add(labelVelo);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelVeloDemande.setPreferredSize(new Dimension(150,30));
		labelVeloDemande.setMaximumSize(new Dimension(150,30));
		panel4.add(labelVeloDemande);
		centerWest.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelDate.setPreferredSize(new Dimension(150,30));
		labelDate.setMaximumSize(new Dimension(150,30));
		panel5.add(labelDate);
		centerWest.add(panel5);	

		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelDateDemande.setPreferredSize(new Dimension(150,30));
		labelDateDemande.setMaximumSize(new Dimension(150,30));
		panel6.add(labelDateDemande);
		centerWest.add(panel6);			
		
		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(200,350));
		boutonPrendreEnCharge.setPreferredSize(new Dimension(150,40));
		boutonPrendreEnCharge.setMaximumSize(new Dimension(150,40));
		boutonPrendreEnCharge.setBackground(Color.CYAN);
		boutonPrendreEnCharge.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonPrendreEnCharge.addActionListener(this);
		centerEast.add(boutonPrendreEnCharge);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonPrendreEnCharge){
			new FenetrePrendreEnChargeInterventionTech(this.getTechnicien(),this.getIntervention());
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}		
}
