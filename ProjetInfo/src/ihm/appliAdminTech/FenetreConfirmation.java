package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOTechnicien;
import ihm.MsgBox;
import ihm.appliAdminTech.administrateur.FenetreCreationCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreEtatStationAdmin;
import ihm.appliAdminTech.administrateur.FenetreRechercherCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreStationsSurSousAdmin;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.FenetreEnregistrerVeloTech;
import ihm.appliAdminTech.technicien.FenetreGererAssignationsTech;
import ihm.appliAdminTech.technicien.FenetreGererInterventionsTech;
import ihm.appliAdminTech.technicien.FenetreRemettreVeloEnStationTech;
import ihm.appliAdminTech.technicien.FenetreRetirerVeloTech;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliAdminTech.technicien.PanneauTech;
import ihm.appliUtil.FenetreAuthentificationUtil;

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

import metier.Compte;

public class FenetreConfirmation extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Compte compte;
	private JFrame fenetrePrecedente;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelConfirm = new JLabel("");
	private JButton bouton1 = new JButton("");
	private JButton bouton2 = new JButton("");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public JFrame getFenetrePrecedente() {
		return fenetrePrecedente;
	}

	public void setFenetrePrecedente(JFrame fenetrePrecedente) {
		this.fenetrePrecedente = fenetrePrecedente;
	}

	public FenetreConfirmation(Compte c,JFrame fenetrePrec){

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		this.setCompte(c);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Ecran de confirmation");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,600));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		System.out.println(fenetrePrec.getTitle());
		if(fenetrePrec.getTitle().equals("Menu principal de l'administrateur") || fenetrePrec.getTitle().equals("Menu principal du technicien")){
			labelAdminTech = new JLabel("Vous êtes à présent déconnecté");
			labelAdminTech.setFont(FenetreAuthentificationUtil.POLICE4);
			labelAdminTech.setPreferredSize(new Dimension(400,30));
			bouton1.setText("Nouvelle authentification");
			bouton1.setPreferredSize(new Dimension(250,30));
			bouton1.setBackground(Color.MAGENTA);
			bouton1.setFont(FenetreAuthentificationUtil.POLICE4);
			bouton1.addActionListener(this);
			north.add(labelAdminTech);
			north.add(bouton1);

			labelConfirm.setText("Au revoir et à bientôt ! ");
			labelConfirm.setFont(FenetreAuthentificationUtil.POLICE2);
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
			labelAdminTech.setFont(FenetreAuthentificationUtil.POLICE4);
			labelAdminTech.setPreferredSize(new Dimension(500,30));

			JPanel south = new JPanel();
			south.setPreferredSize(new Dimension(700,40));
			south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			//south.setLayout(new GridLayout(1,3));

			bouton1.setPreferredSize(new Dimension(250,40));
			bouton1.setMaximumSize(new Dimension(250,40));
			bouton1.setFont(FenetreAuthentificationUtil.POLICE3);
			bouton1.setBackground(Color.GREEN);

			bouton2.setPreferredSize(new Dimension(250,40));
			bouton2.setMaximumSize(new Dimension(250,40));
			bouton2.setFont(FenetreAuthentificationUtil.POLICE3);
			bouton2.setBackground(Color.GREEN);

			boutonRetour.setPreferredSize(new Dimension(250,40));
			boutonRetour.setMaximumSize(new Dimension(250,40));
			boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonRetour.setBackground(Color.YELLOW);

			//situations possibles pour un administrateur
			if (fenetrePrec.getTitle().equals("Création d'un nouveau compte")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le nouveau compte a bien été créé. ");
				bouton1.setText("Créer un autre compte");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Modifier informations sur un compte")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("La modification a bien été enregistrée. ");
				bouton1.setText("Afficher informations sur un autre compte");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			else if(fenetrePrec.getTitle().equals("Fenêtre de demande de confirmation")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("La résiliation a bien été enregistrée. ");
				bouton1.setText("Afficher informations sur un autre compte");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			else if(fenetrePrec.getTitle().equals("Envoyer une demande d'assignation")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("La demande d'assignation a bien été envoyée. ");
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
			else if(fenetrePrec.getTitle().equals("Enregistrer un nouveau vélo")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText( "<html><center>Le vélo a bien été ajouté et affecté au garage. <br> Son identifiant est : "
						+ ((FenetreEnregistrerVeloTech) fenetrePrec).getVeloEntre().getId()+"</center></html>");
				bouton1.setText("Enregistrer un autre nouveau vélo");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Retirer un vélo défectueux d'une station")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le vélo défectueux a bien été retiré de la station et affecté au garage. ");
				bouton1.setText("Retirer un autre vélo défectueux");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Remettre un vélo réparé en station")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le changement de lieu du vélo a bien été enregistré. ");
				bouton1.setText("Remettre un autre vélo en station");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}		
			else if(fenetrePrec.getTitle().equals("Prendre en charge une assignation")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("Le déplacement de ces vélos a bien été confirmé. ");
				bouton1.setText("Gérer une autre demande d'assignation");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}
			else if(fenetrePrec.getTitle().equals("Prendre en charge une intervention")){
				north.add(labelAdminTech);
				this.getContentPane().add(north,BorderLayout.NORTH);
				labelConfirm.setText("L'intervention a bien été enregistrée. ");
				bouton1.setText("Gérer une autre demande d'intervention");
				bouton1.addActionListener(this);
				boutonRetour.addActionListener(this);
				JPanel panel = new JPanel();
				panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
				south.add(panel);
				south.add(bouton1);
				south.add(boutonRetour);
			}

			center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			labelConfirm.setFont(FenetreAuthentificationUtil.POLICE2);
			center.add(labelConfirm);
			this.getContentPane().add(center,BorderLayout.CENTER);
			this.getContentPane().add(south,BorderLayout.SOUTH);

			this.setVisible(true);
		}// fin du else si on n'est pas dans le cas d'une fenêtre de déconnexion
	}


	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(fenetrePrecedente.getTitle().equals("Création d'un nouveau compte")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreCreationCompteAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Modifier informations sur un compte") || fenetrePrecedente.getTitle().equals("Fenêtre de demande de confirmation")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreRechercherCompteAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()),false);
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}

		else if(fenetrePrecedente.getTitle().equals("Envoyer une demande d'assignation")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreEtatStationAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==bouton2){
				try {
					new FenetreStationsSurSousAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Enregistrer un nouveau vélo")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreEnregistrerVeloTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Retirer un vélo défectueux d'une station")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreRetirerVeloTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Remettre un vélo réparé en station")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreRemettreVeloEnStationTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Prendre en charge une assignation")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreGererAssignationsTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Prendre en charge une intervention")){
			if(arg0.getSource()==bouton1){
				try {
					new FenetreGererInterventionsTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				try {
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				}
			}
		}
		if(fenetrePrecedente.getTitle().equals("Menu principal de l'administrateur") || fenetrePrecedente.getTitle().equals("Menu principal du technicien")){
			if(arg0.getSource()==bouton1){
				new FenetreAuthentification(false);
			}
		}
	}
}