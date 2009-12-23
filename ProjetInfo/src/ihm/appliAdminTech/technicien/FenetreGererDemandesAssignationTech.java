package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.DemandeAssignation;
import metier.Lieu;
import metier.Technicien;

public class FenetreGererDemandesAssignationTech extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Demandes d'assignation");
	private DemandeAssignation demandeEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public FenetreGererDemandesAssignationTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour voir toutes les demandes d'assignation");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer les demandes d'assignation");
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

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(300,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,400));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelMsg);

		List<DemandeAssignation> listeDemandes;
		try {
			listeDemandes= DAODemandeAssignation.getAllDemandesAssignation();
			for (int i=0;i<listeDemandes.size();i++){
				DemandeAssignation demandei = listeDemandes.get(i);
				if(demandei.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(demandei.getLieu()).size() == 0){
					demandei.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demandei);
					listeDemandes.remove(i);
				}
				else if(demandei.getLieu().getId().equals(""+Lieu.ID_GARAGE) && demandei.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(demandei.getLieu()).size() < 0){
					demandei.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demandei);
					listeDemandes.remove(i);
				}
			}
			final String [] tableauDemandes = new String[listeDemandes.size()+1];
			tableauDemandes[0]="Sélectionnez une demande";
			for (int i=0;i<listeDemandes.size();i++){
				DemandeAssignation demandei = listeDemandes.get(i);
				tableauDemandes[i+1] = DAODemandeAssignation.ligne(demandei);
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauDemandes);

			JComboBox combo = new JComboBox(model);
			combo.setFont(UtilitaireIhm.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					try {
						String chaineSelectionnee = (String)(o);
						if(chaineSelectionnee.equals(tableauDemandes[0])){
							demandeEntree=null;
						}
						else{
							String idDemandeEntre="";
							int i=8;
							while(chaineSelectionnee.charAt(i)!=' '){
								idDemandeEntre=idDemandeEntre+chaineSelectionnee.charAt(i);
								i++;
							}
							System.out.println("id de la demande entrée : "+idDemandeEntre);
							demandeEntree = DAODemandeAssignation.getDemandeAssignationById(idDemandeEntre);
						}
					} catch (SQLException e) {
						MsgBox.affMsg(e.getMessage());
					} catch (ClassNotFoundException e) {
						MsgBox.affMsg(e.getMessage());
					}
					catch (ConnexionFermeeException e){
						MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
						new FenetreAuthentification(false);
					}
				}
			});
			center.add(combo);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}		
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if (arg0.getSource()==boutonValider){
				new FenetreGererUneDemandeAssignationTech(this.getTechnicien(),demandeEntree);
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
			try {
				new FenetreGererDemandesAssignationTech(this.getTechnicien());
			} catch (ConnexionFermeeException e1) {
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		} catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
			try {
				new FenetreGererDemandesAssignationTech(this.getTechnicien());
			} catch (ConnexionFermeeException e1) {
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
	}
}
