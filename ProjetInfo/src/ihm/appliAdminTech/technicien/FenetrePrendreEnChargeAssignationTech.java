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
 * la classe {@link FenetrePrendreEnChargeAssignationTech} h�rite de {@link JFrame} et impl�mente l'interface {@link ActionListener}
 * <br>cette fen�tre appara�t lorsque le technicien a cliqu� sur "Prendre en charge" dans la {@link FenetreGererUneAssignationTech}
 * <br>elle demande au {@link Technicien} d'entrer les identifiants des v�los qui viennent d'�tre assign�s afin de traiter cette {@link DemandeAssignation}
 * <br>cette prise en charge s'accompagne d'une action physique qui a lieu quasiment simultan�ment (on peut imaginer que le technicien a acc�s � l'application depuis un ordinateur pr�sent dans son camion)
 * <br>cette fen�tre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetrePrendreEnChargeAssignationTech extends JFrame implements ActionListener {
	/*
	 * liste des attributs priv�s de la fen�tre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JLabel labelListeVelosAssignables = new JLabel("");
	private JPanel panelVelos = new JPanel();
	private JButton boutonValider = new JButton("Confirmer les d�placements");
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
	 * le technicien connect� sur cette fen�tre
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
	 * la demande d'assignation en train d'�tre prise en charge
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
	 * le nombre de v�los � ajouter ou � retirer
	 * <br>il sera calcul� en fonction du nombre de v�los actuellement en station et du nombre de v�los voulu par l'auteur de la demande d'assignation
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
	 * @param t : le technicien connect� sur la fen�tre
	 * @param d : la demande d'assignation en train d'�tre prise en charge
	 * @param l : la liste des identifiants de v�los pr�-entr�s dans les JTextField, avec des champs vides si rien n'a �t� entr� pr�c�demment ou si l'identifiant entr� n'�tait pas valide
	 * @param b : un bool�en valant false si dans la fen�tre pr�c�dente le {@link Technicien} a d�j� entr� une liste d'identifiants dont certains n'�taient pas valides
	 * @throws ConnexionFermeeException
	 * @see {@link FenetrePrendreEnChargeAssignationTech#setDiff(int)}
	 */
	public FenetrePrendreEnChargeAssignationTech(Technicien t, DemandeAssignation d, ArrayList<String> l, boolean b) throws ConnexionFermeeException{
		System.out.println("Fen�tre pour prendre en charge une demande d'assignation");
		this.setContentPane(new PanneauTech());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Prendre en charge une assignation");
		//D�finit une taille pour celle-ci
		this.setPreferredSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setTechnicien(t);
		this.setDemande(d);

		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
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
			/* si diff<0 cela signifie qu'il faut ajouter des v�los depuis le garage vers la station
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
				labelMsg.setText("Il n'y a aucun v�lo � d�placer. Le nombre de v�los dans la station est le nombre voulu. ");
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
					labelListeVelosAssignables.setText("<html><center>v�los disponibles au garage : "+listeIdVelosEnMarcheDansGarage+"</center></html>");

					if(b){
						labelMsg.setText("<html><center>Veuillez entrer les identifiants des v�los affect�s du garage � la station "+d.getLieu().getAdresse()+"</center></html>");
					}
					else{
						labelMsg.setText("<html><center>Certains v�los entr�s ne sont pas valides.<br> Veuillez entrer les identifiants des v�los affect�s du garage � la station "+d.getLieu().getId()+"</center></html>");
						labelMsg.setForeground(Color.RED);					
					}
					for (int i =0;i<nbVelosADeplacer;i++){
						JLabel labelVelo = new JLabel("");

						labelVelo.setBackground(UtilitaireIhm.TRANSPARENCE);
						labelVelo.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("V�lo "+(i+1));
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
					labelListeVelosAssignables.setText("v�los disponibles en station : "+listeIdVelosDansStation);

					if(b){
						labelMsg.setText("<html><center>Veuillez entrer les identifiants des v�los affect�s de la station "+d.getLieu().getId()+" au garage. </center></html>");
					}
					else{
						labelMsg.setText("<html><center>Certains identifiants n'�taient pas valides.<br> Veuillez entrer les identifiants des v�los affect�s de la station "+d.getLieu().getId()+" au garage</center></html>");
						labelMsg.setForeground(Color.RED);
					}


					for (int i=0;i<nbVelosADeplacer;i++){
						JLabel labelVelo = new JLabel("");

						labelVelo.setBackground(UtilitaireIhm.TRANSPARENCE);
						labelVelo.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("V�lo "+(i+1));
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
	 * cette m�thode est ex�cut�e quand le {@link Technicien} a cliqu� sur l'un des boutons qui se pr�sentaient � lui
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
					//on r�cup�re tous les ids de v�los entr�s dans listeIdVelos
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
					/* c'est que l'un des identifiants de v�los entr�s au moins n'�tait pas valide
					 * il faut donc r�-ouvrir la m�me fen�tre, en laissant pr�-entr�s dans les champs les ids entr�s qui �taient valides
					 */
					new FenetrePrendreEnChargeAssignationTech(this.getTechnicien(), this.getDemande(), nouvelleListeIdVelos, false);

				}
				else{
					//tous les id de v�los entr�s �taient valides
					for(String idVelo : nouvelleListeIdVelos){
						Velo velo = DAOVelo.getVeloById(idVelo);
						if(this.getDiff()<0){
							//il s'agit d'un ajout de v�los
							this.getTechnicien().rajouterVelo(velo,(Station) this.getDemande().getLieu());	
						}
						else{
							//il s'agit d'un retrait de v�los
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
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}