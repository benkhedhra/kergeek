package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

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

import metier.Administrateur;
import metier.DemandeAssignation;
import metier.Lieu;
import metier.Station;

public class FenetreEnvoyerDemandeAssignationAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private Lieu lieuConcerne;
	private DemandeAssignation demande;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez entrer les paramètres de la demande d'assignation");
	private JLabel labelStation = new JLabel("Station");
	private JLabel labelStationConcernee = new JLabel ("");
	private JLabel labelNbVelos = new JLabel("Nombre de vélos souhaité");
	private TextFieldLimite nbVelosARemplir = new TextFieldLimite (4,"");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonEtatAutreStation = new JButton ("Voir l'état d'une autre station");
	private JButton boutonStationsSurSous = new JButton ("Voir les stations sur et sous occupées");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public Lieu getLieuConcerne() {
		return lieuConcerne;
	}

	public void setLieuConcerne(Lieu lieuConcerne) {
		this.lieuConcerne = lieuConcerne;
	}

	public DemandeAssignation getDemande() {
		return demande;
	}

	public void setDemande(DemandeAssignation demande) {
		this.demande = demande;
	}

	public FenetreEnvoyerDemandeAssignationAdmin (Administrateur a,Lieu l){

		System.out.println("Fenêtre pour envoyer une demande d'assignation");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Envoyer une demande d'assignation");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
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

		this.setAdministrateur(a);
		this.setLieuConcerne(l);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));

		labelMsg.setFont(UtilitaireIhm.POLICE2);
		labelMsg.setPreferredSize(new Dimension (600,30));
		center.add(labelMsg);

		center.setPreferredSize(new Dimension(550,350));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);

		labelStation.setPreferredSize(new Dimension (250,30));
		center.add(labelStation);

		labelStationConcernee.setText(l.getAdresse());
		labelStationConcernee.setPreferredSize(new Dimension (250,30));
		center.add(labelStationConcernee);
		labelNbVelos.setPreferredSize(new Dimension (250,30));
		center.add(labelNbVelos);
		nbVelosARemplir.setPreferredSize(new Dimension (250,30));
		center.add(nbVelosARemplir);

		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(200,350));
		boutonValider.setPreferredSize(new Dimension(200,40));
		boutonValider.setMaximumSize(new Dimension(200,40));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);

		boutonEtatAutreStation.setPreferredSize(new Dimension(250,40));
		boutonEtatAutreStation.setMaximumSize(new Dimension(250,40));
		boutonEtatAutreStation.setFont(UtilitaireIhm.POLICE3);
		boutonEtatAutreStation.setBackground(Color.GREEN);
		boutonEtatAutreStation.addActionListener(this);
		south.add(boutonEtatAutreStation);

		boutonStationsSurSous.setPreferredSize(new Dimension(250,40));
		boutonStationsSurSous.setMaximumSize(new Dimension(250,40));
		boutonStationsSurSous.setFont(UtilitaireIhm.POLICE3);
		boutonStationsSurSous.setBackground(Color.GREEN);
		boutonStationsSurSous.addActionListener(this);
		south.add(boutonStationsSurSous);

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
		try{
			if (arg0.getSource()==boutonValider){
				List<DemandeAssignation> listeDemandes = DAODemandeAssignation.getDemandesAssignationEnAttente();
				List<Station> listeStations = new ArrayList<Station>();
				for(int i=0;i<listeDemandes.size();i++){
					listeStations.add((Station)listeDemandes.get(i).getLieu());
				}
				int nbVelos = 0;
				nbVelos = Integer.parseInt(nbVelosARemplir.getText());

				if (UtilitaireIhm.verifieParametresAssignation(nbVelos,this.getLieuConcerne())){
					this.setDemande(new DemandeAssignation(nbVelos,this.getLieuConcerne()));
					System.out.println("station concernée : "+this.getLieuConcerne().getAdresse());
					if(listeStations.contains(this.getLieuConcerne())){
						DemandeAssignation ancienneDemande = listeDemandes.get(listeStations.indexOf(this.getLieuConcerne()));
						new FenetreExisteDejaDemandeAssignationAdmin(this.getAdministrateur(),ancienneDemande,this.getDemande());
					}
					else if(DAODemandeAssignation.createDemandeAssignation(this.getDemande())){
						new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
						System.out.println("Une demande d'assignation a bien été envoyée pour "+this.getLieuConcerne().getAdresse()+" : "+nbVelos+" vélos demandés");
					}
				}
				else{
					MsgBox.affMsg("Le nombre de vélos entré est incorrect. La capacité de "+this.getLieuConcerne().getAdresse()+" est de "+this.getLieuConcerne().getCapacite()+" vélos. ");
					new FenetreEnvoyerDemandeAssignationAdmin(this.getAdministrateur(),this.getLieuConcerne());
				}
			}
			else if (arg0.getSource()==boutonEtatAutreStation){
				new FenetreEtatStationAdmin(this.getAdministrateur());
			}
			else if (arg0.getSource()==boutonStationsSurSous){
				new FenetreStationsSurSousAdmin(this.getAdministrateur());
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalAdmin(this.getAdministrateur());
			}
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
	}
}