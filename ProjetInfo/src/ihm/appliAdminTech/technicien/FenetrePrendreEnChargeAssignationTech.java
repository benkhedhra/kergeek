package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.DemandeAssignation;
import metier.Garage;
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
	private JPanel panelVelos = new JPanel();
	private JButton boutonValider = new JButton("Confirmer les déplacements");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private ArrayList<String> listeIdVelos = new ArrayList<String>();
	private int diff;

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
	
	public int getDiff() {
		return diff;
	}
	
	public void setDiff(int diff) {
		this.diff = diff;
	}

	
	
	
	
	
	
	public FenetrePrendreEnChargeAssignationTech(Technicien t, DemandeAssignation d, ArrayList<String> l){
		System.out.println("Fenêtre pour prendre en charge une demande d'assignation");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Prendre en charge une assignation");
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

		int nbVelosADeplacer;

		try {
			this.setDiff(DAOVelo.getVelosByLieu(demande.getLieu()).size()-demande.getNombreVelosVoulusDansLieu());
			/* si diff<0 cela signifie qu'il faut ajouter des vélos depuis le garage vers la station
			 * si diff>0 c'est qu'il faudra en retirer de la station pour les mettre au garage
			 */

			nbVelosADeplacer = Math.abs(diff);

			labelMsg.setPreferredSize(new Dimension(600,50));
			labelMsg.setMaximumSize(new Dimension(600,50));
			labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);

			//a priori ne sert pas
			if(nbVelosADeplacer==0){
				labelMsg.setText("Il n'y a aucun vélo à déplacer. Le nombre de vélos dans la station est le nombre voulu. ");
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);
			}
			else{
				JPanel centerCenter = new JPanel();
				centerCenter.setPreferredSize(new Dimension(550,350));
				centerCenter.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

				panelVelos.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
				panelVelos.setLayout(new GridLayout(nbVelosADeplacer,2));

				if(this.getDiff()<0){
					labelMsg.setText("Veuillez entrer les identifiants des vélos affectés du garage à la station "+d.getLieu().getId());
					for (int i =0;i<nbVelosADeplacer;i++){
						JLabel labelVelo = new JLabel("");

						labelVelo.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
						labelVelo.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("Vélo "+(i+1));
						panelVelos.add(labelVelo);

						JTextField veloARemplir = new JTextField(l.get(i));
						veloARemplir.setPreferredSize(new Dimension(250,30));
						panelVelos.add(veloARemplir);
					}

				}
				else{
					labelMsg.setText("Veuillez entrer les identifiants des vélos affectés de la station "+d.getLieu().getId()+" au garage");

					for (int i =0;i<nbVelosADeplacer;i++){
						JLabel labelVelo = new JLabel("");

						labelVelo.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
						labelVelo.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("Vélo "+(i+1));
						panelVelos.add(labelVelo);

						JTextField veloARemplir = new JTextField();
						veloARemplir.setPreferredSize(new Dimension(250,30));
						panelVelos.add(veloARemplir);
					}
				}
				centerCenter.add(panelVelos);
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);
				center.add(centerCenter,BorderLayout.CENTER);

				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.setBackground(Color.CYAN);
				boutonValider.addActionListener(this);
				centerCenter.add(boutonValider);

				center.add(centerCenter, BorderLayout.CENTER);
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
			for(int i=0; i<panelVelos.getComponentCount(); i++){
				if(panelVelos.getComponent(i) instanceof JTextField){
					listeIdVelos.add(((JTextField)panelVelos.getComponent(i)).getText());
				}
			}
			ArrayList<String> nouvelleListeIdVelos = new ArrayList<String>();
			try {
				if(this.getDiff()<0){
					nouvelleListeIdVelos = UtilitaireIhm.verifieSiVelosPeuventEtreAssignation(listeIdVelos, demande.getLieu());
				}
				else{
					nouvelleListeIdVelos = UtilitaireIhm.verifieSiVelosPeuventEtreAssignation(listeIdVelos, Garage.getInstance());
				}
				if(nouvelleListeIdVelos.contains("")){
					new FenetrePrendreEnChargeAssignationTech(this.getTechnicien(), this.getDemande(), nouvelleListeIdVelos);
				}
				else{
					System.out.println("demande prise en charge");
					demande.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demande);
					System.out.println("demande maj dans la bdd");
					new FenetreConfirmation(this.getTechnicien().getCompte(),this);
				}
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg("Class Not Found Exception : " + e.getMessage());
			} catch (SQLException e) {
				MsgBox.affMsg("SQL Exception : " + e.getMessage());
				e.printStackTrace();
			}
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}