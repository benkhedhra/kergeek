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
import metier.Garage;
import metier.Lieu;

/**
 * FenetreEnvoyerDemandeAssignationAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle demande � l'administateur d'entrer le nombre v�los souhait� pour un lieu donn�
 * <br>on peut envoyer une demande d'assignation sur une {@link Station}, mais aussi sur le {@link Garage}, puisqu'on peut avoir besoin d'un nombre de v�los suffisant au garage pour faire face aux demandes d'assignation et pallier les destructions de v�los
 * @author KerGeek
 */
public class FenetreEnvoyerDemandeAssignationAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connect� sur la fen�tre
	 */
	private Administrateur administrateur;
	/**
	 * le lieu (station ou garage) concern� par la demande d'assignation
	 */
	private Lieu lieuConcerne;
	/**
	 * la demande d'assignation en train d'�tre cr��e
	 */
	private DemandeAssignation demande;

	//attributs priv�s : composants de la fen�tre : 5 JLabel, 1 TextFieldLimite, et 4 JButton
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez entrer les param�tres de la demande d'assignation");
	private JLabel labelStation = new JLabel("Station");
	private JLabel labelStationConcernee = new JLabel ("");
	private JLabel labelNbVelos = new JLabel("Nombre de v�los souhait�");
	private TextFieldLimite nbVelosARemplir = new TextFieldLimite (4,"");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonEtatAutreStation = new JButton ("Voir l'�tat d'une autre station");
	private JButton boutonStationsSurSous = new JButton ("Voir les stations sur et sous occup�es");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	//Accesseurs utiles
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

	/**
	 * constructeur de {@link FenetreEnvoyerDemandeAssignationAdmin}
	 * @param a
	 * l'administrateur connect� sur la fen�tre
	 * @param l
	 * le lieu concern� par la demande d'assignation (d�termin� par la {@link FenetreAffichageResultatsAdmin} pr�c�dente)
	 */
	public FenetreEnvoyerDemandeAssignationAdmin (Administrateur a,Lieu l){

		System.out.println("Fen�tre pour envoyer une demande d'assignation");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Envoyer une demande d'assignation");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
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

		this.setAdministrateur(a);
		this.setLieuConcerne(l);

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
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

	/**
	 * m�thode ex�cut�e quand l'administrateur a cliqu� sur {@link FenetreEnvoyerDemandeAssignationAdmin#boutonValider} ou un autre bouton
	 * @param arg0
	 * @see UtilitaireIhm#verifieParametresAssignation(int, Lieu)
	 * @see Administrateur#demanderAssignation(int, Lieu)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try{
			//s'il veut envoyer cette demande d'assignation
			if (arg0.getSource()==boutonValider){
				//listeDemandes est la liste des demandes d'assignation d�j� existantes et non prises en charge
				List<DemandeAssignation> listeDemandes = DAODemandeAssignation.getDemandesAssignationEnAttente();
				//listeLieux est la liste des lieux concern�s par les demandes d'assignation non prises en charge
				List<Lieu> listeLieux = new ArrayList<Lieu>();
				for(int i=0;i<listeDemandes.size();i++){
					listeLieux.add(listeDemandes.get(i).getLieu());
				}
				//nbVelos est le nombre de v�los souhait� entr� par l'administrateur
				int nbVelos = -1;
				nbVelos = Integer.parseInt(nbVelosARemplir.getText());

				//il faut v�rifier que le nombre de v�los entr� et le lieu sont bien corrects
				if (UtilitaireIhm.verifieParametresAssignation(nbVelos,this.getLieuConcerne())){
					this.setDemande(new DemandeAssignation(nbVelos,this.getLieuConcerne()));
					System.out.println("station concern�e : "+this.getLieuConcerne().getAdresse());
					if(listeLieux.contains(this.getLieuConcerne())){
						//il existe d�j� une demande d'assignation non prise en charge pour ce lieu (probablement formul�e par un autre administrateur)
						DemandeAssignation ancienneDemande = listeDemandes.get(listeLieux.indexOf(this.getLieuConcerne()));
						new FenetreExisteDejaDemandeAssignationAdmin(this.getAdministrateur(),ancienneDemande,this.getDemande());
					}
					else if(DAODemandeAssignation.createDemandeAssignation(this.getDemande())){
						//il n'existe pas de demande d'assignation non prise en charge pour ce lieu
						new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
						System.out.println("Une demande d'assignation a bien �t� envoy�e pour "+this.getLieuConcerne().getAdresse()+" : "+nbVelos+" v�los demand�s");
					}
				}
				else{
					MsgBox.affMsg("<html> <center>Le nombre de v�los entr� est incorrect. <br>La capacit� de "+this.getLieuConcerne().getAdresse()+" est de "+this.getLieuConcerne().getCapacite()+" v�los. </center></html>");
					new FenetreEnvoyerDemandeAssignationAdmin(this.getAdministrateur(),this.getLieuConcerne());
				}
			}
			else if (arg0.getSource()==boutonEtatAutreStation){
				new FenetreEtatLieuAdmin(this.getAdministrateur());
			}
			else if (arg0.getSource()==boutonStationsSurSous){
				new FenetreStationsSurSousAdmin(this.getAdministrateur());
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalAdmin(this.getAdministrateur());
			}
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
	}
}