package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import metier.Garage;
import metier.Station;
import metier.Technicien;
import metier.Velo;

/**
 * la classe {@link FenetrePrendreEnChargeAssignationTech} hérite de {@link JFrame} et implémente l'interface {@link ActionListener}
 * <br>cette fenêtre apparaît lorsque le technicien a cliqué sur "Prendre en charge" dans la {@link FenetreGererUneAssignationTech}
 * <br>elle demande au {@link Technicien} d'entrer les identifiants des vélos qui viennent d'être assignés afin de traiter cette {@link DemandeAssignation}
 * <br>cette prise en charge s'accompagne d'une action physique qui a lieu quasiment simultanément (on peut imaginer que le technicien a accès à l'application depuis un ordinateur présent dans son camion)
 * <br>cette fenêtre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetrePrendreEnChargeAssignationTech extends JFrame implements ActionListener {
	/*
	 * liste des attributs privés de la fenêtre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JLabel labelListeVelosAssignables = new JLabel("");
	private JPanel panelVelos = new JPanel();
	private JButton boutonValider = new JButton("Confirmer les déplacements");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private ArrayList<String> listeIdVelos = new ArrayList<String>();
	private int diff;

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetrePrendreEnChargeAssignationTech#technicien} de la {@link FenetrePrendreEnChargeAssignationTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetrePrendreEnChargeAssignationTech#technicien} de la {@link FenetrePrendreEnChargeAssignationTech}
	 * @param tech
	 * le technicien connecté sur cette fenêtre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * @return	la {@link FenetrePrendreEnChargeAssignationTech#demande} de la {@link FenetrePrendreEnChargeAssignationTech}
	 */
	public DemandeAssignation getDemande() {
		return demande;
	}

	/**
	 * Initialise la {@link FenetrePrendreEnChargeAssignationTech#demande} de la {@link FenetrePrendreEnChargeAssignationTech}
	 * @param d
	 * la demande d'assignation en train d'être prise en charge
	 * @see DemandeAssignation
	 */
	public void setDemande(DemandeAssignation d) {
		this.demande = d;
	}

	/**
	 * @return	la {@link FenetrePrendreEnChargeAssignationTech#diff} de la {@link FenetrePrendreEnChargeAssignationTech}
	 */
	public int getDiff() {
		return diff;
	}

	/**
	 * Initialise la {@link FenetrePrendreEnChargeAssignationTech#diff} de la {@link FenetrePrendreEnChargeAssignationTech}
	 * @param diff
	 * le nombre de vélos à ajouter ou à retirer
	 * <br>il sera calculé en fonction du nombre de vélos actuellement en station et du nombre de vélos voulu par l'auteur de la demande d'assignation
	 */
	public void setDiff(int diff) {
		this.diff = diff;
	}

	public ArrayList<String> getListeIdVelos() {
		return listeIdVelos;
	}

	public void setListeIdVelos(ArrayList<String> listeIdVelos) {
		this.listeIdVelos = listeIdVelos;
	}

	/**
	 * 
	 * @param t : le technicien connecté sur la fenêtre
	 * @param d : la demande d'assignation en train d'être prise en charge
	 * @param l : la liste des identifiants de vélos pré-entrés dans les JTextField, avec des champs vides si rien n'a été entré précédemment ou si l'identifiant entré n'était pas valide
	 * @param b : un booléen valant false si dans la fenêtre précédente le {@link Technicien} a déjà entré une liste d'identifiants dont certains n'étaient pas valides
	 * @throws ConnexionFermeeException
	 * @see {@link FenetrePrendreEnChargeAssignationTech#setDiff(int)}
	 */
	public FenetrePrendreEnChargeAssignationTech(Technicien t, DemandeAssignation d, ArrayList<String> l, boolean b) throws ConnexionFermeeException{
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
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setTechnicien(t);
		this.setDemande(d);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,400));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setPreferredSize(new Dimension(700,60));
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);

		int nbVelosADeplacer;

		try {
			this.setDiff(DAOVelo.getVelosByLieu(demande.getLieu()).size()-demande.getNombreVelosVoulusDansLieu());
			/* si diff<0 cela signifie qu'il faut ajouter des vélos depuis le garage vers la station
			 * si diff>0 c'est qu'il faudra en retirer de la station pour les mettre au garage
			 */

			nbVelosADeplacer = Math.abs(diff);

			labelMsg.setPreferredSize(new Dimension(600,50));
			labelMsg.setMaximumSize(new Dimension(600,50));
			labelMsg.setFont(UtilitaireIhm.POLICE2);

			labelListeVelosAssignables.setPreferredSize(new Dimension(700,50));
			labelListeVelosAssignables.setMaximumSize(new Dimension(700,50));
			labelListeVelosAssignables.setFont(UtilitaireIhm.POLICE2);

			//a priori ne sert pas
			if(nbVelosADeplacer==0){
				labelMsg.setText("Il n'y a aucun vélo à déplacer. Le nombre de vélos dans la station est le nombre voulu. ");
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);
			}
			else{
				JPanel centerCenter = new JPanel();
				centerCenter.setPreferredSize(new Dimension(550,350));
				centerCenter.setBackground(UtilitaireIhm.TRANSPARENCE);

				panelVelos.setBackground(UtilitaireIhm.TRANSPARENCE);
				panelVelos.setLayout(new GridLayout(nbVelosADeplacer,2));

				if(this.getDiff()<0){

					List<Velo> listeVelosDansGarage = DAOVelo.getVelosByLieu(Garage.getInstance());
					List<String> listeIdVelosEnMarcheDansGarage = new ArrayList<String>();
					for (Velo velo : listeVelosDansGarage){
						if(!velo.isEnPanne()){
							listeIdVelosEnMarcheDansGarage.add(velo.getId());
						}
					}
					labelListeVelosAssignables.setText("<html><center>vélos disponibles au garage : "+listeIdVelosEnMarcheDansGarage+"</center></html>");

					if(b){
						labelMsg.setText("<html><center>Veuillez entrer les identifiants des vélos affectés du garage à la station "+d.getLieu().getAdresse()+"</center></html>");
					}
					else{
						labelMsg.setText("<html><center>Certains vélos entrés ne sont pas valides.<br> Veuillez entrer les identifiants des vélos affectés du garage à la station "+d.getLieu().getId()+"</center></html>");
						labelMsg.setForeground(Color.RED);					
					}
					for (int i =0;i<nbVelosADeplacer;i++){
						JLabel labelVelo = new JLabel("");

						labelVelo.setBackground(UtilitaireIhm.TRANSPARENCE);
						labelVelo.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("Vélo "+(i+1));
						panelVelos.add(labelVelo);

						TextFieldLimite veloARemplir = new TextFieldLimite(4,l.get(i));
						veloARemplir.setPreferredSize(new Dimension(250,30));
						panelVelos.add(veloARemplir);
					}
				}
				else{
					List<Velo> listeVelosDansStation = DAOVelo.getVelosByLieu(this.getDemande().getLieu());
					List<String> listeIdVelosDansStation = new ArrayList<String>();
					for (Velo velo : listeVelosDansStation){
						listeIdVelosDansStation.add(velo.getId());
					}
					labelListeVelosAssignables.setText("vélos disponibles en station : "+listeIdVelosDansStation);

					if(b){
						labelMsg.setText("<html><center>Veuillez entrer les identifiants des vélos affectés de la station "+d.getLieu().getId()+" au garage. </center></html>");
					}
					else{
						labelMsg.setText("<html><center>Certains identifiants n'étaient pas valides.<br> Veuillez entrer les identifiants des vélos affectés de la station "+d.getLieu().getId()+" au garage</center></html>");
						labelMsg.setForeground(Color.RED);
					}


					for (int i=0;i<nbVelosADeplacer;i++){
						JLabel labelVelo = new JLabel("");

						labelVelo.setBackground(UtilitaireIhm.TRANSPARENCE);
						labelVelo.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("Vélo "+(i+1));
						panelVelos.add(labelVelo);

						TextFieldLimite veloARemplir = new TextFieldLimite(4,l.get(i));
						veloARemplir.setPreferredSize(new Dimension(250,30));
						panelVelos.add(veloARemplir);
					}
				}
				centerCenter.add(panelVelos);
				centerNorth.setLayout(new GridLayout(2,1));
				centerNorth.add(labelMsg);
				centerNorth.add(labelListeVelosAssignables);
				center.add(centerNorth,BorderLayout.NORTH);
				center.add(centerCenter,BorderLayout.CENTER);

				boutonValider.setFont(UtilitaireIhm.POLICE3);
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
		south.setPreferredSize(new Dimension(700,50));
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

	/**
	 * @override
	 * cette méthode est exécutée quand le {@link Technicien} a cliqué sur l'un des boutons qui se présentaient à lui
	 * @param arg0
	 * @see UtilitaireIhm#verifieSiVelosPeuventEtreAssignes(ArrayList, metier.Lieu)
	 * @see Technicien#rajouterVelo(Velo, Station)
	 * @see Technicien#retirerVelo(Velo)
	 * @see FenetreConfirmation
	 * @see MenuPrincipalTech
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonValider){
			for(int i=0; i<panelVelos.getComponentCount(); i++){
				if(panelVelos.getComponent(i) instanceof TextFieldLimite){
					//on récupère tous les ids de vélos entrés dans listeIdVelos
					listeIdVelos.add(((TextFieldLimite)panelVelos.getComponent(i)).getText());
				}
			}
			ArrayList<String> nouvelleListeIdVelos = new ArrayList<String>();
			try {
				if(this.getDiff()<0){
					nouvelleListeIdVelos = UtilitaireIhm.verifieSiVelosPeuventEtreAssignes(listeIdVelos, Garage.getInstance());
				}
				else{
					nouvelleListeIdVelos = UtilitaireIhm.verifieSiVelosPeuventEtreAssignes(listeIdVelos, demande.getLieu());
				}
				System.out.println(nouvelleListeIdVelos);
				if(nouvelleListeIdVelos.contains("")){
					/* c'est que l'un des identifiants de vélos entrés au moins n'était pas valide
					 * il faut donc ré-ouvrir la même fenêtre, en laissant pré-entrés dans les champs les ids entrés qui étaient valides
					 */
					new FenetrePrendreEnChargeAssignationTech(this.getTechnicien(), this.getDemande(), nouvelleListeIdVelos, false);

				}
				else{
					//tous les id de vélos entrés étaient valides
					for(String idVelo : nouvelleListeIdVelos){
						Velo velo = DAOVelo.getVeloById(idVelo);
						if(this.getDiff()<0){
							//il s'agit d'un ajout de vélos
							this.getTechnicien().rajouterVelo(velo,(Station) this.getDemande().getLieu());	
						}
						else{
							//il s'agit d'un retrait de vélos
							this.getTechnicien().retirerVelo(velo);	
						}
						DAOVelo.updateVelo(velo);
					}
					System.out.println("demande prise en charge");
					demande.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demande);
					new FenetreConfirmation(this.getTechnicien().getCompte(),this);
				}
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg("Class Not Found Exception : " + e.getMessage());
			} catch (SQLException e) {
				MsgBox.affMsg("SQL Exception : " + e.getMessage());
				e.printStackTrace();
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}