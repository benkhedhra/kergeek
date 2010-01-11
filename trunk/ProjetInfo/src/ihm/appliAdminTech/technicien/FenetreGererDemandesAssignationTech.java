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
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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

import metier.Administrateur;
import metier.DemandeAssignation;
import metier.Lieu;
import metier.Technicien;

/**
 * la classe {@link FenetreGererDemandesAssignationTech} hérite de {@link JFrame} et implémente l'interface {@link ActionListener}
 * <br>cette fenêtre apparaît lorsque le technicien a cliqué sur le bouton "Voir les demandes d'assignation" dans le {@link MenuPrincipalTech}
 * <br>lui sont présentées sous forme de menu déroulant toutes les {@link DemandeAssignation} émanant des constatations des {@link Administrateur}
 * @author KerGeek
 *
 */
public class FenetreGererDemandesAssignationTech extends JFrame implements ActionListener {


	/*
	 * liste des attributs privés de la fenêtre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Gérer les demandes d'assignation");
	private JLabel labelMsg = new JLabel("Demandes d'assignation");
	private DemandeAssignation demandeEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetreGererDemandesAssignationTech#technicien} de la {@link FenetreGererDemandesAssignationTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetreGererDemandesAssignationTech#technicien} de la {@link FenetreGererDemandesAssignationTech}
	 * @param tech
	 * le technicien connecté sur cette fenêtre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * constructeur de {@link FenetreGererDemandesAssignationTech}
	 * @param t
	 * le technicien connecté sur la {@link FenetreGererDemandesAssignationTech}
	 * @throws ConnexionFermeeException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JComboBox
	 */
	public FenetreGererDemandesAssignationTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour voir toutes les demandes d'assignation");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer les demandes d'assignation");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
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
		labelTech.setPreferredSize(new Dimension(1100,50));
		labelTech.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1200,100));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setPreferredSize(new Dimension(1100,100));
		center.add(labelMsg);

		List<DemandeAssignation> listeDemandes;
		try {
			listeDemandes= DAODemandeAssignation.getDemandesAssignationEnAttente();
			for (int i=0;i<listeDemandes.size();i++){
				DemandeAssignation demandei = listeDemandes.get(i);
				//à cet endroit il faut regarder si la demande d'assignation n'a pas été prise en charge indirectement, par les mouvements de vélos des utilisateurs (emprunts et rendus)
				//si le nombre de vélos voulu par l'administrateur s'avère être égal au nombre de vélos effectivement présents dans la station, on déclare la demande d'assignation prise en charge (elle ne figurera donc pas dans le menu déroulant)
				//l'administrateur n'avait entré qu'un nombre de vélos voulu, aussi une demande correspondant à un ajout de vélos peut se transformer en demande de retrait de vélos suite aux mouvements de vélos par les utilisateurs
				if(demandei.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(demandei.getLieu()).size() == 0){
					demandei.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demandei);
					listeDemandes.remove(i);
				}
				//dans le cas où la demande d'assignation concernait le garage, on imagine que la demande d'assignation est prise en charge si le nombre de vélos présent est supérieur au nombre de vélos voulu
				//en effet le "nombre de vélos voulu" est plus un nombre minimal, un seuil, pour pouvoir faire face aux différentes demandes d'assignation, et si le nombre de vélos au garage est supérieur l'objectif de l'administrateur est atteint pareillement
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
			combo.setPreferredSize(new Dimension(250,50));
			combo.setMaximumSize(new Dimension(250,50));
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
						repaint();
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
		boutonValider.setPreferredSize(new Dimension(250,50));
		boutonValider.setMaximumSize(new Dimension(250,50));
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel.add(boutonRetour);
		south.add(panel,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/**
	 * cette méthode est exécutée si le {@link Technicien} a cliqué sur l'un des boutons qui lui étaient proposés
	 * <br>s'il a cliqué sur le {@link FenetreGererDemandesAssignationTech#boutonValider} une fenêtre s'ouvre détaillant la demande d'assignation sélectionnée
	 * <br>s'il a cliqué sur le {@link FenetreGererDemandesAssignationTech#boutonRetour} il retourne à son menu principal
	 * @see FenetreGererUneDemandeAssignationTech#FenetreGererUneDemandeAssignationTech(Technicien, DemandeAssignation)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if (arg0.getSource()==boutonValider){
				if(demandeEntree==null){
					MsgBox.affMsg("Vous n'avez sélectionné aucune demande d'assignation");
					new FenetreGererDemandesAssignationTech(this.getTechnicien());
				}
				else{
					new FenetreGererUneDemandeAssignationTech(this.getTechnicien(),demandeEntree);
				}
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