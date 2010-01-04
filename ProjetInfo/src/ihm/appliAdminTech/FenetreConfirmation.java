package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOTypeIntervention;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.administrateur.FenetreAffichageResultatsAdmin;
import ihm.appliAdminTech.administrateur.FenetreCreationCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreDemandeConfirmationAdmin;
import ihm.appliAdminTech.administrateur.FenetreEnvoyerDemandeAssignationAdmin;
import ihm.appliAdminTech.administrateur.FenetreEtatLieuAdmin;
import ihm.appliAdminTech.administrateur.FenetreExisteDejaDemandeAssignationAdmin;
import ihm.appliAdminTech.administrateur.FenetreModifCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreRechercherCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreStationsSurSousAdmin;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.FenetreEnregistrerArrivageVelosTech;
import ihm.appliAdminTech.technicien.FenetreGererDemandesAssignationTech;
import ihm.appliAdminTech.technicien.FenetreGererInterventionsTech;
import ihm.appliAdminTech.technicien.FenetrePrendreEnChargeAssignationTech;
import ihm.appliAdminTech.technicien.FenetrePrendreEnChargeInterventionTech;
import ihm.appliAdminTech.technicien.FenetreRetirerVeloDefectueuxTech;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliAdminTech.technicien.PanneauTech;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;
import metier.TypeIntervention;
import metier.Velo;

/**
 * FenetreConfirmation hérite de JFrame et implémente ActionListener
 * cette fenêtre permet d'afficher un message de confirmation adaptée en fonction de la fenêtre précédente
 * cette fenêtre est commune à l'administrateur et au technicien
 * @see MenuPrincipalAdmin
 * @see MenuPrincipalTech
 * @see FenetreChangerMotDePasse
 * @see FenetreCreationCompteAdmin
 * @see FenetreModifCompteAdmin
 * @see FenetreDemandeConfirmationAdmin
 * @see FenetreEnvoyerDemandeAssignationAdmin
 * @see FenetreExisteDejaDemandeAssignationAdmin
 * @see FenetreEnregistrerArrivageVelosTech
 * @see FenetreRetirerVeloDefectueuxTech
 * @see FenetrePrendreEnChargeInterventionTech
 * @see FenetrePrendreEnChargeAssignationTech
 * @author KerGeek
 */
public class FenetreConfirmation extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * attributs privés : composants de la fenêtre
	 */
	private Compte compte;
	private JFrame fenetrePrecedente;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelConfirm = new JLabel("");
	private JButton bouton1 = new JButton("");
	private JButton bouton2 = new JButton("");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link Administrateur#compte} de l'Administrateur connecté ou le {@link Technicien#compte} du Technicien connecté sur la FenetreConfirmation
	 */
	public Compte getCompte() {
		return compte;
	}

	/**
	 * Initialise le {@link Administrateur#compte} de la {@link FenetreConfirmation}
	 * @param compte
	 * le compte de l'individu connecté sur cette fenêtre
	 * @see Compte
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	/**
	 * @return	la {@link JFrame} sur laquelle l'individu était précédemment connecté
	 */
	public JFrame getFenetrePrecedente() {
		return fenetrePrecedente;
	}

	/**
	 * Initialise la {@link JFrame} de la {@link FenetreConfirmation}
	 * @param fenetrePrecedente
	 * la fenêtre sur laquelle l'individu était connecté à l'étape précédente
	 */
	public void setFenetrePrecedente(JFrame fenetrePrecedente) {
		this.fenetrePrecedente = fenetrePrecedente;
	}

	/**
	 * constructeur d'une FenetreConfirmation
	 * @param c
	 * le compte de l'individu connecté
	 * @param fenetrePrec
	 * la fenêtre précédente
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public FenetreConfirmation(Compte c,JFrame fenetrePrec) throws SQLException, ClassNotFoundException, ConnexionFermeeException{

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		this.setCompte(c);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Ecran de confirmation");
		Dimension d=getToolkit().getScreenSize();
		this.setPreferredSize(d);
		this.setSize(d);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,200));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1200,800));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		
		labelConfirm.setPreferredSize(new Dimension(300,800));

		System.out.println(fenetrePrec.getTitle());
		if(fenetrePrec.getTitle().equals("Menu principal de l'administrateur") || fenetrePrec.getTitle().equals("Menu principal du technicien")){
			labelAdminTech = new JLabel("Vous êtes à présent déconnecté");
			labelAdminTech.setFont(UtilitaireIhm.POLICE4);
			labelAdminTech.setPreferredSize(new Dimension(1000,50));
			bouton1.setText("Nouvelle authentification");
			bouton1.setPreferredSize(new Dimension(300,50));
			bouton1.setBackground(Color.MAGENTA);
			bouton1.setFont(UtilitaireIhm.POLICE4);
			bouton1.addActionListener(this);
			north.add(labelAdminTech);
			north.add(bouton1);

			labelConfirm.setText("Au revoir et à bientôt ! ");
			labelConfirm.setFont(UtilitaireIhm.POLICE2);
			center.add(labelConfirm);

			this.getContentPane().add(north,BorderLayout.NORTH);
			this.getContentPane().add(center,BorderLayout.CENTER);

			this.setVisible(true);
			// dans l'idée l'écran ne reste affiché que 3 secondes et la fenêtre d'authentification apparaît automatiquement au terme des 5 secondes
			/*try {
				Thread.sleep(5000);
				this.dispose();
				new FenetreAuthentification(false);
			} catch (InterruptedException e) {
				MsgBox.affMsg(e.getMessage());
			}*/
		}
		else {
			labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ c.getId());
			labelAdminTech.setFont(UtilitaireIhm.POLICE4);
			labelAdminTech.setPreferredSize(new Dimension(1000,50));

			JPanel south = new JPanel();
			south.setPreferredSize(new Dimension(1200,100));
			south.setBackground(UtilitaireIhm.TRANSPARENCE);
			//south.setLayout(new GridLayout(1,3));

			bouton1.setPreferredSize(new Dimension(300,50));
			bouton1.setMaximumSize(new Dimension(300,50));
			bouton1.setFont(UtilitaireIhm.POLICE3);
			bouton1.setBackground(Color.GREEN);

			bouton2.setPreferredSize(new Dimension(300,50));
			bouton2.setMaximumSize(new Dimension(300,50));
			bouton2.setFont(UtilitaireIhm.POLICE3);
			bouton2.setBackground(Color.GREEN);

			boutonRetour.setPreferredSize(new Dimension(300,50));
			boutonRetour.setMaximumSize(new Dimension(300,50));
			boutonRetour.setFont(UtilitaireIhm.POLICE3);
			boutonRetour.setBackground(Color.YELLOW);


			//situations possibles pour un administrateur

			if(fenetrePrec.getTitle().equals("Changer son mot de passe")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le mot de passe a bien été changé. ");
				center.add(labelConfirm);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);
				south.add(panel);
				south.add(boutonRetour);
			}

			else if (fenetrePrec.getTitle().equals("Création d'un nouveau compte")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le nouveau compte a bien été créé. ");
				center.add(labelConfirm);
				bouton1.setText("Créer un autre compte");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Modifier informations sur un compte")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("La modification a bien été enregistrée. ");
				center.add(labelConfirm);
				bouton1.setText("Afficher informations sur un autre compte");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			else if(fenetrePrec.getTitle().equals("Fenêtre de demande de confirmation")){
				FenetreDemandeConfirmationAdmin f = (FenetreDemandeConfirmationAdmin)fenetrePrec;
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				if(f.getFenetrePrecedente().getTitle().equals("Modifier informations sur un compte")){
					labelConfirm.setText("La résiliation a bien été enregistrée. ");
					bouton1.setText("Afficher informations sur un autre compte");
				}
				else{
					labelConfirm.setText("La suppression a bien été enregistrée. ");
					bouton1.setText("Revoir la liste des vélos actuellement sortis");
				}
				center.add(labelConfirm);
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			else if((fenetrePrec.getTitle().equals("Envoyer une demande d'assignation")) || (fenetrePrec.getTitle().equals("Demande d'assignation déjà existante pour cette station"))){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("La demande d'assignation a bien été envoyée. ");
				center.add(labelConfirm);
				bouton1.setText("Voir l'état d'une autre station");
				bouton1.addActionListener(this);
				bouton2.setText("Voir les stations sur et sous-occupées");
				bouton2.addActionListener(this);
				boutonRetour.addActionListener(this);
				south.add(bouton1);
				south.add(bouton2);
				south.add(boutonRetour);
			}

			//situations possibles pour un technicien
			else if(fenetrePrec.getTitle().equals("Enregistrer un nouvel arrivage de vélos")){
				FenetreEnregistrerArrivageVelosTech f = (FenetreEnregistrerArrivageVelosTech) fenetrePrec;
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText( "<html><center>"+f.getNbVelosEntre()+" vélo(s) ont bien été enregistré(s) et affecté(s) au garage. <br> Veuillez dès à présent leur apposer les identifiants suivants : "+"</center></html>");
				labelConfirm.setPreferredSize(new Dimension(100,800));
				center.add(labelConfirm);
				JPanel panelIds = new JPanel();
				panelIds.setBackground(UtilitaireIhm.TRANSPARENCE);
				panelIds.setLayout(new GridLayout(f.getNbVelosEntre(),2));
				for(int i=0;i<f.getNbVelosEntre();i++){
					JLabel labelVelo = new JLabel("Vélo "+i+1);
					labelVelo.setPreferredSize(new Dimension(600,40));
					Velo velo = f.getListeVelosCrees().get(i);
					JLabel labelIdVelo = new JLabel(velo.getId());
					labelIdVelo.setPreferredSize(new Dimension(400,40));
					labelIdVelo.setFont(UtilitaireIhm.POLICE2);
					panelIds.add(labelVelo);
					panelIds.add(labelIdVelo);
				}
				center.add(panelIds);
				bouton1.setText("Enregistrer un autre arrivage");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Gérer les demandes d'intervention ou constater un vélo défectueux")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le vélo défectueux a bien été retiré de la station et affecté au garage. ");
				center.add(labelConfirm);
				bouton1.setText("Retirer un autre vélo défectueux");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			else if(fenetrePrec.getTitle().equals("Prendre en charge une assignation")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le déplacement de ces vélos a bien été confirmé. ");
				center.add(labelConfirm);
				bouton1.setText("Gérer une autre demande d'assignation");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Prendre en charge une intervention")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				FenetrePrendreEnChargeInterventionTech f = (FenetrePrendreEnChargeInterventionTech) fenetrePrec;
				if(f.getTypeInterventionEntre()==DAOTypeIntervention.getTypeInterventionById(TypeIntervention.TYPE_DESTRUCTION)){
					labelConfirm.setText("La destruction du vélo a bien été enregistrée. ");
				}
				labelConfirm.setText("L'intervention a bien été enregistrée. ");
				center.add(labelConfirm);
				bouton1.setText("Gérer une autre demande d'intervention");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(UtilitaireIhm.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			center.setBackground(UtilitaireIhm.TRANSPARENCE);
			labelConfirm.setFont(UtilitaireIhm.POLICE2);
			this.getContentPane().add(center,BorderLayout.CENTER);
			this.getContentPane().add(south,BorderLayout.SOUTH);

			this.setVisible(true);
		}// fin du else si on n'est pas dans le cas d'une fenêtre de déconnexion
	}


	/**
	 * méthode exécutée sur l'individu a cliqué sur un des boutons qui se présentaient à lui
	 * <br>la fenêtre courante se ferme et laisse place à la fenêtre suivante adaptée à l'événement arg0
	 * @param arg0 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try{
			if(fenetrePrecedente.getTitle().equals("Changer son mot de passe") && (arg0.getSource()==boutonRetour)){
				FenetreChangerMotDePasse f = (FenetreChangerMotDePasse) fenetrePrecedente;
				if(f.getCompte().getType()==Compte.TYPE_ADMINISTRATEUR){
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
				else {
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Création d'un nouveau compte")){
				if(arg0.getSource()==bouton1){
					new FenetreCreationCompteAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Modifier informations sur un compte")){
				if(arg0.getSource()==bouton1){
					new FenetreRechercherCompteAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()),false);
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
			}

			else if (fenetrePrecedente.getTitle().equals("Fenêtre de demande de confirmation")){
				FenetreDemandeConfirmationAdmin f = (FenetreDemandeConfirmationAdmin)fenetrePrecedente;
				if(f.getFenetrePrecedente().getTitle().equals("Modifier informations sur un compte")){
					if(arg0.getSource()==bouton1){
						new FenetreRechercherCompteAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()),false);
					}
					else if (arg0.getSource()==boutonRetour){
						new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
					}				}
				else{
					if(arg0.getSource()==bouton1){
						new FenetreAffichageResultatsAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()),this);
					}
					else if (arg0.getSource()==boutonRetour){
						new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
					}
				}
			}


			else if(fenetrePrecedente.getTitle().equals("Envoyer une demande d'assignation")){
				if(arg0.getSource()==bouton1){
					new FenetreEtatLieuAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
				else if (arg0.getSource()==bouton2){
					new FenetreStationsSurSousAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Enregistrer un nouvel arrivage de vélos")){
				if(arg0.getSource()==bouton1){
					new FenetreEnregistrerArrivageVelosTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Gérer les demandes d'intervention ou constater un vélo défectueux")){
				if(arg0.getSource()==bouton1){
					new FenetreRetirerVeloDefectueuxTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Prendre en charge une assignation")){
				if(arg0.getSource()==bouton1){
					new FenetreGererDemandesAssignationTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Prendre en charge une intervention")){
				if(arg0.getSource()==bouton1){
					new FenetreGererInterventionsTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
				else if (arg0.getSource()==boutonRetour){
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
			else if(fenetrePrecedente.getTitle().equals("Menu principal de l'administrateur") || fenetrePrecedente.getTitle().equals("Menu principal du technicien")){
				if(arg0.getSource()==bouton1){
					new FenetreAuthentification(false);
				}
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
}