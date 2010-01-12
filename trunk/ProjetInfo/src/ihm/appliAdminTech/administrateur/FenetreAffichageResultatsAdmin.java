package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOUtilisateur;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.Station;
import metier.Velo;
import statistiques.DiagrammeFreqStations;
import statistiques.DiagrammeNbEmpruntsUtilisateur;
import statistiques.DiagrammeNbInterventions;
import statistiques.DiagrammeNbVelosStation;
import statistiques.DiagrammeTxOccupationStation;
import statistiques.TableauInterventionVelo;
import statistiques.TableauListeVelosDansLieu;


/** 
 * FenetreAffichageResultatsAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle affiche les statistiques adéquates en fonction du contexte dans lequel on se trouve et propose à l'administrateur les choix adaptés de retour à une ou plusieurs fenêtres
 * @author KerGeek
 * @see DiagrammeFreqStations
 * @see DiagrammeNbEmpruntsUtilisateur
 * @see DiagrammeNbInterventions
 * @see DiagrammeNbVelosStation
 * @see DiagrammeTxOccupationStation
 * @see TableauInterventionVelo
 * @see TableauListeVelosDansLieu
 */
public class FenetreAffichageResultatsAdmin extends JFrame implements ActionListener {

	/**
	 * serial version ID par défaut
	 */
	private static final long serialVersionUID = 1L;

	//Attributs
	/**
	 * Cette fenêtre a un attribut Administrateur, un attribut JFrame, un attribut JLabel, et 4 attributs JButton
	 * administrateur est l'administrateur actuellement connecté
	 * FenetreAffichageResultatsAdmin dépend presque entièrement de la fenêtre précédente, d'où l'attribut fenetrePrecedente
	 * fenetrePrecedente sera castée en fenêtre adéquate selon le cas pour récupérer les attributs dont on aura besoin pour construire les graphiques
	 * labelMsg est le label indiquant quoi faire à l'administrateur connecté
	 * bouton1, bouton2 et bouton3 sont les différents choix qui seront proposés à l'admin, selon le cas
	 * boutonRetour est le bouton de retour au menu principal
	 */
	private Administrateur administrateur;
	private JFrame fenetrePrecedente;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelChemin=new JLabel("");
	private JButton bouton1 = new JButton("");
	private JButton bouton2 = new JButton("");
	private JButton bouton3 = new JButton("");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	//Accesseurs et Mutateurs
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public JFrame getFenetrePrecedente() {
		return fenetrePrecedente;
	}

	public void setFenetrePrecedente(JFrame fenetrePrecedente) {
		this.fenetrePrecedente = fenetrePrecedente;
	}

	/**
	 * Constructeur de {@link FenetreAffichageResultatsAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 * @param fenetrePrec
	 * la fenêtre sur laquelle l'administrateur était connecté à l'étape précédente (sera castée selon son type)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public FenetreAffichageResultatsAdmin(Administrateur a, JFrame fenetrePrec) throws SQLException, ClassNotFoundException, ConnexionFermeeException{

		this.setContentPane(new PanneauAdmin());
		this.setAdministrateur(a);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Affichage des résultats");
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1280,850));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);	

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1280,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);

		bouton1.setPreferredSize(new Dimension(350,50));
		bouton1.setMaximumSize(new Dimension(350,50));
		bouton1.setFont(UtilitaireIhm.POLICE3);
		bouton1.setBackground(Color.GREEN);

		//si à la fenêtre précédente il a choisi une période sur laquelle il voulait voir la fréquentation des stations
		if(fenetrePrec.getTitle().equals("Fréquentation des stations")){
			//on caste la fenêtre
			FenetreFrequentationStationsAdmin f = (FenetreFrequentationStationsAdmin) fenetrePrec;
			//on attribue le bon chemin
			labelChemin.setText("Menu principal > Demander des statistiques > Fréquentation des stations > "+f.getPeriodeEntree());
			//création de l'objet diag en fonction de la période choisie que l'on récupère
			DiagrammeFreqStations diag = new DiagrammeFreqStations(f.getPeriodeEntree());
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
			bouton1.setText("<html> <center>Fréquentation pour<br>une autre période</center></html>");
			bouton1.addActionListener(this);
			south.add(bouton1);
		}

		//si à la fenêtre précédente il a choisi un compte pour lequel il voulait voir les statistiques
		else if(fenetrePrec.getTitle().equals("Informations sur un compte")){
			//on caste la fenêtre pour pouvoir récupérer le compte choisi et tracer l'histogramme en fonction
			FenetreInfoCompteAdmin f = (FenetreInfoCompteAdmin) fenetrePrec;
			//on attribue le bon chemin
			labelChemin.setText("Menu principal > Demander des statistiques > Statistiques sur utilisateurs > "+f.getCompte().getId());

			DiagrammeNbEmpruntsUtilisateur diag = new DiagrammeNbEmpruntsUtilisateur(DAOUtilisateur.getUtilisateurById(f.getCompte().getId()));
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
			center.add(lblChart);
			bouton1.setText("<html> <center>Afficher statistiques<br>sur un autre utilisateur</center></html>");
			bouton1.addActionListener(this);
			south.add(bouton1);
		}

		//si à la fenêtre précédente il a choisi de voir les interventions les plus fréquentes
		else if(fenetrePrec.getTitle().equals("Menu <interventions de maintenance> de l'administrateur")){
			//on attribue le bon chemin
			labelChemin.setText("Menu principal > Demander des statistiques > Maintenance > Fréquence des interventions");

			DiagrammeNbInterventions diag = new DiagrammeNbInterventions();
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
		}

		//si à la fenêtre précédente il a sélectionné un vélo pour lequel il souhaite voir l'historique des interventions de maintenance
		else if(fenetrePrec.getTitle().equals("Historique d'un vélo")){
			FenetreHistoriqueVeloAdmin f = (FenetreHistoriqueVeloAdmin) fenetrePrec;

			//on attribue le bon chemin
			labelChemin.setText("Menu principal > Demander des statistiques > Maintenance > Historique d'un vélo > vélo "+f.getVeloEntre().getId());

			JLabel labelMsg = new JLabel ("Historique du vélo "+f.getVeloEntre().getId());
			labelMsg.setPreferredSize(new Dimension(600,100));
			center.add(labelMsg);
			TableauInterventionVelo tableau = new TableauInterventionVelo(f.getVeloEntre().getId());
			center.add(tableau);
			bouton1.setText("<html> <center>Afficher historique<br>pour un autre vélo</center></html>");
			bouton1.addActionListener(this);
			south.add(bouton1);			
		}

		//si à la fenêtre précédente il a confirmé la suppression d'un vélo dans la nature depuis trop longtemps et qu'il souhaite voir à nouveau la liste des vélos sortis
		else if(fenetrePrec.getTitle().equals("Ecran de confirmation")){
			JLabel labelMsg = new JLabel ("Liste de tous les vélos actuellement empruntés");
			//on attribue le bon chemin
			labelChemin.setText("Menu principal > Voir l'état du parc > Liste des vélos dans un lieu > Vélos en cours d'emprunt");

			labelMsg.setPreferredSize(new Dimension(1200,100));
			center.add(labelMsg);
			TableauListeVelosDansLieu tableau = new TableauListeVelosDansLieu(Sortie.getInstance());
			center.add(tableau);
			bouton1.setText("<html> <center>Déclarer comme supprimé<br>un vélo perdu</center></html>");
			bouton1.addActionListener(this);
			south.add(bouton1);
		}

		//si à la fenêtre précédente il a choisi un lieu pour lequel il souhaite voir la liste des vélos présents
		else if(fenetrePrec.getTitle().equals("Voir la liste des vélos présents dans un lieu")){
			//on caste la fenêtre
			FenetreVoirVelosDansLieuAdmin f = (FenetreVoirVelosDansLieuAdmin)fenetrePrec;
			//on peut maintenant récupérer le lieu sélectionné dans la fenêtre précédente
			Lieu lieu = f.getLieuEntre();
			//si ce lieu est la sortie, c'est que l'on veut voir tous les vélos actuellement empruntés
			if(lieu.getId().equals(Lieu.ID_SORTIE)){
				JLabel labelMsg = new JLabel ("Liste de tous les vélos actuellement empruntés");
				//on attribue le bon chemin
				labelChemin.setText("Menu principal > Voir l'état du parc > Liste des vélos dans un lieu > Vélos en cours d'emprunt");

				labelMsg.setPreferredSize(new Dimension(1200,100));
				center.add(labelMsg);
				TableauListeVelosDansLieu tableau = new TableauListeVelosDansLieu(Sortie.getInstance());
				center.add(tableau);
				bouton1.setText("<html> <center>Déclarer comme supprimé<br>un vélo perdu</center></html>");
				bouton1.addActionListener(this);
				south.add(bouton1);
			}
			//sinon c'est que ce lieu est une station ou le pool de vélos
			else{
				JLabel labelMsg = new JLabel ("Liste de tous les vélos actuellement au lieu : "+lieu.getAdresse());
				labelMsg.setPreferredSize(new Dimension(1200,100));

				//on attribue le bon chemin
				labelChemin.setText("Menu principal > Voir l'état du parc > Liste des vélos dans un lieu > "+lieu.getAdresse());

				center.add(labelMsg);
				TableauListeVelosDansLieu tableau = new TableauListeVelosDansLieu(lieu);
				center.add(tableau);
				bouton1.setText("<html> <center>Voir la liste des vélos<br>pour un autre lieu</center></html>");
				bouton1.addActionListener(this);
				south.add(bouton1);
			}
		}

		//si à la fenêtre précédente l'administrateur a souhaité voir l'état d'un lieu, soit directement, soit en passant par l'onglet "voir les stations sur et sous-occupées"
		else if((fenetrePrec.getTitle().equals("Voir l'état d'un lieu")) || (fenetrePrec.getTitle().equals("Stations sur et sous occupées")) || (fenetrePrec.getTitle().equals("Envoyer une demande d'assignation"))){

			JLabel lblChart1 = new JLabel();
			JLabel lblChart2 = new JLabel();

			JPanel panel1 = new JPanel();
			panel1.setBackground(UtilitaireIhm.TRANSPARENCE);
			panel1.setPreferredSize(new Dimension(600,650));

			JPanel panel2 = new JPanel();
			panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
			panel2.setPreferredSize(new Dimension(600,650));

			DiagrammeNbVelosStation diag1;
			DiagrammeTxOccupationStation diag2;

			FenetreEtatLieuAdmin f1 = null;
			FenetreStationsSurSousAdmin f2 = null;
			FenetreEnvoyerDemandeAssignationAdmin f3=null;
			//s'il a directement sélectionné un lieu pour voir son état
			if (fenetrePrec.getTitle().equals("Voir l'état d'un lieu")) {
				f1 = (FenetreEtatLieuAdmin) fenetrePrec;

				//on attribue le bon chemin
				labelChemin.setText("Menu principal > Voir l'état du parc > Etat d'un lieu > "+f1.getLieuEntre().getAdresse());

				//System.out.println(!f1.getLieuEntre().getId().equals(""+Lieu.ID_GARAGE));
				if(!f1.getLieuEntre().getId().equals(Lieu.ID_GARAGE)){
					//on trace le graphe indiquant le nombre de vélos présents
					diag1 = new DiagrammeNbVelosStation( (Station) f1.getLieuEntre());
					lblChart1.setIcon(new ImageIcon(diag1.getImage()));
					panel1.add(lblChart1);
					
					//on trace le graphe des taux d'occupation
					//calculer le taux d'occupation du garage n'a pas de sens car sa capacité est grande
					diag2 = new DiagrammeTxOccupationStation((Station)f1.getLieuEntre());
					lblChart2.setIcon(new ImageIcon(diag2.getImage()));
					panel2.add(lblChart2);
					
					center.add(panel1);
					center.add(panel2);
				}
				else{
					//il s'agit du garage
					JLabel label1 = new JLabel("Nombre de vélos présents au garage");
					List<Velo> listeVelos = DAOVelo.getVelosByLieu(Garage.getInstance());
					JLabel label2 = new JLabel(""+listeVelos.size());
					JLabel label3 = new JLabel("Nombre de vélos en panne au garage");
					int nbVelosEnPanne=0;
					for(Velo velo : listeVelos){
						if(velo.isEnPanne()){
							nbVelosEnPanne++;
						}
					}
					JLabel label4 = new JLabel(""+nbVelosEnPanne);
					JLabel label5 = new JLabel("Nombre de vélos au garage et assignables");
					JLabel label6 = new JLabel(""+(listeVelos.size()-nbVelosEnPanne));
					JLabel label7 = new JLabel("Capacité");
					JLabel label8 = new JLabel(""+Garage.getInstance().getCapacite());
					label1.setPreferredSize(new Dimension(500,40));
					label2.setPreferredSize(new Dimension(500,40));
					label3.setPreferredSize(new Dimension(500,40));
					label4.setPreferredSize(new Dimension(500,40));
					label5.setPreferredSize(new Dimension(500,40));
					label6.setPreferredSize(new Dimension(500,40));
					label7.setPreferredSize(new Dimension(500,40));
					label8.setPreferredSize(new Dimension(500,40));
					center.add(label1);
					center.add(label2);
					center.add(label3);
					center.add(label4);
					center.add(label5);
					center.add(label6);
					center.add(label7);
					center.add(label8);
					
				}
			}
			//s'il est passé par la fenêtre "voir les stations sur et sous-occupées" (idem mais le cast est différent)
			else if (fenetrePrec.getTitle().equals("Stations sur et sous occupées")) {
				f2 = (FenetreStationsSurSousAdmin) fenetrePrec;
				//on attribue le bon chemin
				labelChemin.setText("Menu principal > Voir l'état du parc > Stations sur et sous occupées > "+f2.getStationEntree().getAdresse());

				diag1 = new DiagrammeNbVelosStation(f2.getStationEntree());
				panel1.add(lblChart1);
				lblChart1.setIcon(new ImageIcon(diag1.getImage()));
				diag2 = new DiagrammeTxOccupationStation(f2.getStationEntree());
				lblChart2.setIcon(new ImageIcon(diag2.getImage()));
				panel2.add(lblChart2);
				
				center.add(panel1);
				center.add(panel2);
			}
			
			else if (fenetrePrec.getTitle().equals("Envoyer une demande d'assignation")){
				f3 = (FenetreEnvoyerDemandeAssignationAdmin) fenetrePrec;
				//on attribue le bon chemin
				labelChemin.setText("Menu principal > Voir l'état du parc > Stations sur et sous occupées > "+f3.getLieuConcerne().getAdresse());

				//System.out.println(!f1.getLieuEntre().getId().equals(""+Lieu.ID_GARAGE));
				if(!f3.getLieuConcerne().getId().equals(Lieu.ID_GARAGE)){
					//on trace le graphe indiquant le nombre de vélos présents
					diag1 = new DiagrammeNbVelosStation( (Station) f3.getLieuConcerne());
					lblChart1.setIcon(new ImageIcon(diag1.getImage()));
					panel1.add(lblChart1);
					
					//on trace le graphe des taux d'occupation
					//calculer le taux d'occupation du garage n'a pas de sens car sa capacité est grande
					diag2 = new DiagrammeTxOccupationStation((Station)f3.getLieuConcerne());
					lblChart2.setIcon(new ImageIcon(diag2.getImage()));
					panel2.add(lblChart2);
					
					center.add(panel1);
					center.add(panel2);
				}
				else{
					//il s'agit du garage
					JLabel label1 = new JLabel("Nombre de vélos présents au garage");
					List<Velo> listeVelos = DAOVelo.getVelosByLieu(Garage.getInstance());
					JLabel label2 = new JLabel(""+listeVelos.size());
					JLabel label3 = new JLabel("Nombre de vélos en panne au garage");
					int nbVelosEnPanne=0;
					for(Velo velo : listeVelos){
						if(velo.isEnPanne()){
							nbVelosEnPanne++;
						}
					}
					JLabel label4 = new JLabel(""+nbVelosEnPanne);
					JLabel label5 = new JLabel("Nombre de vélos au garage et assignables");
					JLabel label6 = new JLabel(""+(listeVelos.size()-nbVelosEnPanne));
					JLabel label7 = new JLabel("Capacité");
					JLabel label8 = new JLabel(""+Garage.getInstance().getCapacite());
					label1.setPreferredSize(new Dimension(500,40));
					label2.setPreferredSize(new Dimension(500,40));
					label3.setPreferredSize(new Dimension(500,40));
					label4.setPreferredSize(new Dimension(500,40));
					label5.setPreferredSize(new Dimension(500,40));
					label6.setPreferredSize(new Dimension(500,40));
					label7.setPreferredSize(new Dimension(500,40));
					label8.setPreferredSize(new Dimension(500,40));
					center.add(label1);
					center.add(label2);
					center.add(label3);
					center.add(label4);
					center.add(label5);
					center.add(label6);
					center.add(label7);
					center.add(label8);
					
				}
			}

			bouton1.setText("<html> <center>Voir l'état<br>d'une autre station</center></html>");
			bouton2.setText("<html> <center>Voir les stations<br>sur et sous-occupées</center></html>");
			bouton3.setText("<html> <center>Envoyer une<br>demande d'assignation</center></html>");

			bouton1.setPreferredSize(new Dimension(200,50));
			bouton1.setMaximumSize(new Dimension(200,50));
			bouton1.setFont(UtilitaireIhm.POLICE3);
			bouton1.setBackground(Color.GREEN);
			bouton1.addActionListener(this);

			bouton2.setPreferredSize(new Dimension(200,50));
			bouton2.setMaximumSize(new Dimension(200,50));
			bouton2.setFont(UtilitaireIhm.POLICE3);
			bouton2.setBackground(Color.GREEN);
			bouton2.addActionListener(this);

			bouton3.setPreferredSize(new Dimension(350,50));
			bouton3.setMaximumSize(new Dimension(350,50));
			bouton3.setFont(UtilitaireIhm.POLICE3);
			bouton3.setBackground(Color.CYAN);
			bouton3.addActionListener(this);

			south.add(bouton1);
			south.add(bouton2);
			center.add(bouton3);

		}
		
		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdminTech.setFont(UtilitaireIhm.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(1100,50));
		labelAdminTech.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdminTech);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);
		
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);

		this.getContentPane().add(center,BorderLayout.CENTER);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try{
			// différents cas possibles pour le bouton 1
			if(arg0.getSource()==bouton1){
				if(this.getFenetrePrecedente().getTitle().equals("Fréquentation des stations")){
					new FenetreFrequentationStationsAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()));
				}
				else if(this.getFenetrePrecedente().getTitle().equals("Informations sur un compte")){
					new FenetreRechercherCompteAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()),true);
				}
				else if((this.getFenetrePrecedente().getTitle().equals("Voir l'état d'un lieu")) || (this.getFenetrePrecedente().getTitle().equals("Stations sur et sous occupées"))){
					new FenetreEtatLieuAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()));
				}
				else if(this.getFenetrePrecedente().getTitle().equals("Historique d'un vélo")){
					new FenetreHistoriqueVeloAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()));
				}
				else if(this.getFenetrePrecedente().getTitle().equals("Ecran de confirmation")){
					new FenetreSupprimerUnVeloAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()));
				}
				else if(this.getFenetrePrecedente().getTitle().equals("Voir la liste des vélos présents dans un lieu")){
					JButton boutonSource = (JButton) arg0.getSource();
					if (boutonSource.getText().equals("<html> <center>Déclarer comme supprimé<br>un vélo perdu</center></html>")){
						new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
					}
					else {
						new FenetreVoirVelosDansLieuAdmin(this.getAdministrateur());
					}
				}
			}
			else if(arg0.getSource()==bouton2){
				//il ne peut s'agir que du cas où l'administrateur souhaite voir les stations sur et sous-occupées
				new FenetreStationsSurSousAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()));
			}
			else if(arg0.getSource()==bouton3){
				//il ne peut s'agir que du cas où l'administrateur a souhaité envoyer une demande d'assignation
				// mais il faut faire 3 casts différents car il y a deux types de fenêtres précédentes possibles
				if(this.getFenetrePrecedente().getTitle().equals("Voir l'état d'un lieu")){
					FenetreEtatLieuAdmin f = (FenetreEtatLieuAdmin) fenetrePrecedente;
					new FenetreEnvoyerDemandeAssignationAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()),f.getLieuEntre());
				}
				if(this.getFenetrePrecedente().getTitle().equals("Envoyer une demande d'assignation")){
					FenetreEnvoyerDemandeAssignationAdmin f = (FenetreEnvoyerDemandeAssignationAdmin) fenetrePrecedente;
					new FenetreEnvoyerDemandeAssignationAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()),f.getLieuConcerne());
				}
				else{
					FenetreStationsSurSousAdmin f = (FenetreStationsSurSousAdmin) fenetrePrecedente;
					new FenetreEnvoyerDemandeAssignationAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()),f.getStationEntree());
				}
			}
			else if(arg0.getSource()==boutonRetour){
				//l'administrateur retourne à son menu principal
				new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(this.getAdministrateur().getCompte().getId()));
			}
		} 
		catch (SQLException e) {
			MsgBox.affMsg("SQLException "+e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		} 
		catch (ClassNotFoundException e) {
			MsgBox.affMsg("ClassNotFoundException "+e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}