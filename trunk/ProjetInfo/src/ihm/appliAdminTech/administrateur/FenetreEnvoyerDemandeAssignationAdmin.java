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
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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

	//attributs priv�s : composants de la fen�tre : 6 JLabel, 1 TextFieldLimite, et 5 JButton
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez entrer les param�tres de la demande d'assignation");
	private JLabel labelStation = new JLabel("Lieu concern�");
	private JLabel labelStationConcernee = new JLabel ("");
	private JLabel labelNbVelos = new JLabel("Nombre de v�los souhait�");
	private TextFieldLimite nbVelosARemplir = new TextFieldLimite (4,"");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonEcranPrecedent = new JButton("Revenir � l'�cran pr�c�dent");
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
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
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
		
		labelChemin.setText("Menu principal > Voir l'�tat du parc > Etat d'un lieu > "+this.getLieuConcerne().getAdresse()+" > Envoyer une demande d'assignation");

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		labelAdmin.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,800));
		center.setMaximumSize(new Dimension(1200,800));

		labelMsg.setFont(UtilitaireIhm.POLICE2);
		labelMsg.setPreferredSize(new Dimension(1100,40));
		labelMsg.setMinimumSize(new Dimension(1100,40));
		center.add(labelMsg);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);
		panel1.setPreferredSize(new Dimension(600,40));
		panel1.setMinimumSize(new Dimension(600,40));
		labelStation.setPreferredSize(new Dimension(300,40));
		labelStation.setMinimumSize(new Dimension(300,40));
		panel1.add(labelStation);
		center.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
		panel2.setPreferredSize(new Dimension(600,40));
		panel2.setMinimumSize(new Dimension(600,40));
		labelStationConcernee.setText(l.getAdresse());
		labelStationConcernee.setPreferredSize(new Dimension(250,40));
		labelStationConcernee.setMinimumSize(new Dimension(250,40));
		panel2.add(labelStationConcernee);
		center.add(panel2);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);
		panel3.setPreferredSize(new Dimension(600,40));
		panel3.setMinimumSize(new Dimension(600,40));
		labelNbVelos.setPreferredSize(new Dimension(300,40));
		labelNbVelos.setMinimumSize(new Dimension(300,40));
		panel3.add(labelNbVelos);
		center.add(panel3);
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);
		panel4.setPreferredSize(new Dimension(600,40));
		panel4.setMinimumSize(new Dimension(600,40));
		nbVelosARemplir.setPreferredSize(new Dimension(250,40));
		nbVelosARemplir.setMinimumSize(new Dimension(250,40));
		panel4.add(nbVelosARemplir);
		center.add(panel4);

		JPanel panel5 = new JPanel();
		panel5.setBackground(UtilitaireIhm.TRANSPARENCE);
		panel5.setPreferredSize(new Dimension(1200,80));
		panel5.setMinimumSize(new Dimension(1200,80));
		boutonEcranPrecedent.setPreferredSize(new Dimension(300,50));
		boutonEcranPrecedent.setMaximumSize(new Dimension(300,50));
		boutonEcranPrecedent.setBackground(Color.CYAN);
		boutonEcranPrecedent.setFont(UtilitaireIhm.POLICE3);
		boutonEcranPrecedent.addActionListener(this);
		panel5.add(boutonEcranPrecedent);
		center.add(panel5);
		
		JPanel panel6 = new JPanel();
		panel6.setBackground(UtilitaireIhm.TRANSPARENCE);
		panel6.setPreferredSize(new Dimension(1200,80));
		panel6.setMinimumSize(new Dimension(1200,80));
		boutonValider.setPreferredSize(new Dimension(300,50));
		boutonValider.setMaximumSize(new Dimension(300,50));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		panel6.add(boutonValider);
		center.add(panel6);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);

		boutonEtatAutreStation.setPreferredSize(new Dimension(300,50));
		boutonEtatAutreStation.setMaximumSize(new Dimension(300,50));
		boutonEtatAutreStation.setFont(UtilitaireIhm.POLICE3);
		boutonEtatAutreStation.setBackground(Color.GREEN);
		boutonEtatAutreStation.addActionListener(this);
		south.add(boutonEtatAutreStation);

		boutonStationsSurSous.setPreferredSize(new Dimension(300,50));
		boutonStationsSurSous.setMaximumSize(new Dimension(300,50));
		boutonStationsSurSous.setFont(UtilitaireIhm.POLICE3);
		boutonStationsSurSous.setBackground(Color.GREEN);
		boutonStationsSurSous.addActionListener(this);
		south.add(boutonStationsSurSous);

		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
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
			else if (arg0.getSource()==boutonEcranPrecedent){
				new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
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