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

import metier.DemandeAssignation;
import metier.Technicien;

public class FenetreGererUneDemandeTech extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	private JLabel labelTech = new JLabel("");;
	private JLabel labelMsg = new JLabel("Demande d'assignation à traiter");
	private JLabel labelStation = new JLabel("Station concernée");
	private JLabel labelStationDemande;
	private JLabel labelNbVelosSouhaite = new JLabel("Nombre de vélos souhaité");
	private JLabel labelNbVelosSouhaiteDemande;
	private JLabel labelNbVelosActuel = new JLabel("Nombre de vélos actuel");
	private JLabel labelNbVelosActuelDemande;
	private JLabel labelTypeDeplacement = new JLabel("Station concernée");
	private JLabel labelTypeDeplacementDemande;
	private JLabel labelNbVelosADeplacer = new JLabel("Station concernée");
	private JLabel labelNbVelosADeplacerDemande;
	private JButton boutonPrendreEnCharge = new JButton("Prendre en charge cette demande d'assignation");
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

	public FenetreGererUneDemandeTech(Technicien t,DemandeAssignation d){

		System.out.println("Fenêtre pour gérer une demande d'assignation");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer une demande d'assignation");
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
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerWest.setLayout(new GridLayout(5,2));

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
		labelNbVelosSouhaite.setPreferredSize(new Dimension(150,30));
		labelNbVelosSouhaite.setMaximumSize(new Dimension(150,30));
		panel3.add(labelNbVelosSouhaite);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelNbVelosSouhaiteDemande.setPreferredSize(new Dimension(150,30));
		labelNbVelosSouhaiteDemande.setMaximumSize(new Dimension(150,30));
		panel4.add(labelNbVelosSouhaiteDemande);
		centerWest.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNbVelosActuel.setPreferredSize(new Dimension(150,30));
		labelNbVelosActuel.setMaximumSize(new Dimension(150,30));
		panel5.add(labelNbVelosActuel);
		centerWest.add(panel5);	

		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNbVelosActuelDemande.setPreferredSize(new Dimension(150,30));
		labelNbVelosActuelDemande.setMaximumSize(new Dimension(150,30));
		panel6.add(labelNbVelosActuelDemande);
		centerWest.add(panel6);			

		JPanel panel7 = new JPanel();
		panel7.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelTypeDeplacement.setPreferredSize(new Dimension(150,30));
		labelTypeDeplacement.setMaximumSize(new Dimension(150,30));
		panel7.add(labelTypeDeplacement);
		centerWest.add(panel7);	

		JPanel panel8 = new JPanel();
		panel8.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelTypeDeplacementDemande.setPreferredSize(new Dimension(150,30));
		labelTypeDeplacementDemande.setMaximumSize(new Dimension(150,30));
		panel8.add(labelTypeDeplacementDemande);
		centerWest.add(panel8);	

		JPanel panel9 = new JPanel();
		panel9.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNbVelosADeplacer.setPreferredSize(new Dimension(150,30));
		labelNbVelosADeplacer.setMaximumSize(new Dimension(150,30));
		panel9.add(labelNbVelosADeplacer);
		centerWest.add(panel9);	

		JPanel panel10 = new JPanel();
		panel10.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNbVelosADeplacerDemande.setPreferredSize(new Dimension(150,30));
		labelNbVelosADeplacerDemande.setMaximumSize(new Dimension(150,30));
		panel10.add(labelNbVelosADeplacerDemande);
		centerWest.add(panel10);	

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
			new FenetrePrendreEnChargeAssignationTech(this.getTechnicien(),this.getDemande());
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}		
}
