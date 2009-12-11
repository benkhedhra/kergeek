package ihm.appliAdminTech.technicien;

import exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.DemandeAssignation;
import metier.Technicien;
import metier.Velo;

public class FenetreGererUneDemandeAssignationTech extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Demande d'assignation à traiter");
	private JLabel labelStation = new JLabel("Station concernée");
	private JLabel labelStationDemande = new JLabel("");
	private JLabel labelNbVelosSouhaite = new JLabel("Nombre de vélos souhaité");
	private JLabel labelNbVelosSouhaiteDemande = new JLabel("");
	private JLabel labelNbVelosActuel = new JLabel("Nombre de vélos actuel");
	private JLabel labelNbVelosActuelDemande = new JLabel("");
	private JLabel labelOperation = new JLabel("Opération à effectuer");
	private JLabel labelOperationDemande = new JLabel("");

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

	public FenetreGererUneDemandeAssignationTech(Technicien t,DemandeAssignation d) throws SQLException, ClassNotFoundException, ConnexionFermeeException{

		System.out.println("Fenêtre pour gérer une demande d'assignation");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer une demande d'assignation");
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
		this.setDemande(d);

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

		JPanel centerCenter = new JPanel();
		centerCenter.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelStation.setPreferredSize(new Dimension(250,30));
		labelStation.setMaximumSize(new Dimension(250,30));
		panel1.add(labelStation);
		centerCenter.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		System.out.println(DAODemandeAssignation.ligne(d));
		System.out.println(d.getLieu().getAdresse());
		labelStationDemande.setText(d.getLieu().getAdresse());
		labelStationDemande.setPreferredSize(new Dimension(250,30));
		labelStationDemande.setMaximumSize(new Dimension(250,30));
		panel2.add(labelStationDemande);
		centerCenter.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNbVelosSouhaite.setPreferredSize(new Dimension(250,30));
		labelNbVelosSouhaite.setMaximumSize(new Dimension(250,30));
		panel3.add(labelNbVelosSouhaite);
		centerCenter.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelNbVelosSouhaiteDemande.setText(""+d.getNombreVelosVoulusDansLieu());
		labelNbVelosSouhaiteDemande.setPreferredSize(new Dimension(250,30));
		labelNbVelosSouhaiteDemande.setMaximumSize(new Dimension(250,30));
		panel4.add(labelNbVelosSouhaiteDemande);
		centerCenter.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNbVelosActuel.setPreferredSize(new Dimension(250,30));
		labelNbVelosActuel.setMaximumSize(new Dimension(250,30));
		panel5.add(labelNbVelosActuel);
		centerCenter.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		List<Velo> velos = DAOVelo.getVelosByLieu(d.getLieu());
		labelNbVelosActuelDemande.setText(""+velos.size());
		labelNbVelosActuelDemande.setPreferredSize(new Dimension(250,30));
		labelNbVelosActuelDemande.setMaximumSize(new Dimension(250,30));
		panel6.add(labelNbVelosActuelDemande);
		centerCenter.add(panel6);			

		JPanel panel7 = new JPanel();
		panel7.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelOperation.setPreferredSize(new Dimension(250,30));
		labelOperation.setMaximumSize(new Dimension(250,30));
		labelOperation.setForeground(Color.RED);
		panel7.add(labelOperation);
		centerCenter.add(panel7);

		JPanel panel8 = new JPanel();
		panel8.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		String operation="";
		int diff = DAODemandeAssignation.getDiff(d);
		if(diff<0){
			operation=operation+"ajout";
		}
		else if (diff>0){
			operation=operation+"retrait";
		}
		operation=operation+" de "+Math.abs(diff)+" vélos";
		labelOperationDemande.setText(operation);
		labelOperationDemande.setPreferredSize(new Dimension(250,30));
		labelOperationDemande.setMaximumSize(new Dimension(250,30));
		labelOperationDemande.setForeground(Color.RED);
		panel8.add(labelOperationDemande);
		centerCenter.add(panel8);

		JPanel panel9 = new JPanel();
		panel9.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonPrendreEnCharge.setPreferredSize(new Dimension(350,40));
		boutonPrendreEnCharge.setMaximumSize(new Dimension(350,40));
		boutonPrendreEnCharge.setBackground(Color.CYAN);
		boutonPrendreEnCharge.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonPrendreEnCharge.addActionListener(this);
		panel9.add(boutonPrendreEnCharge);
		centerCenter.add(panel9);

		center.add(centerCenter,BorderLayout.CENTER);

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
			ArrayList<String> listeVide = new ArrayList<String>();
			int diff;
			try {
				diff = DAOVelo.getVelosByLieu(demande.getLieu()).size()-demande.getNombreVelosVoulusDansLieu();
				int nbVelosADeplacer = Math.abs(diff);
				for(int i=0;i<nbVelosADeplacer;i++){
					listeVide.add("");
				}
				new FenetrePrendreEnChargeAssignationTech(this.getTechnicien(),this.getDemande(),listeVide,true);

			} catch (SQLException e) {
				MsgBox.affMsg("SQL Exception : " + e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg("Class Not Found Exception : " + e.getMessage());
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}		
}
