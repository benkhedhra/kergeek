package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.MsgBox;
import ihm.appliAdminTech.administrateur.FenetreEnvoyerDemandeAssignationAdmin;
import ihm.appliAdminTech.administrateur.FenetreEtatStationAdmin;
import ihm.appliAdminTech.administrateur.FenetreFrequentationStationsAdmin;
import ihm.appliAdminTech.administrateur.FenetreInfoCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreRechercherCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreStationsSurSousAdmin;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.PanneauTech;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Compte;
import statistiques.DiagrammeFreqStations;
import statistiques.DiagrammeNbEmpruntsUtilisateur;
import statistiques.DiagrammeNbInterventions;
import statistiques.DiagrammeNbVelosStation;
import statistiques.DiagrammeTxOccupationStation;
import exceptionsIhm.ChampIncorrectException;

public class FenetreAffichageResultats extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Compte compte;
	private JFrame fenetrePrecedente;
	private JLabel labelAdminTech = new JLabel("");
	private JButton bouton1 = new JButton("");
	private JButton bouton2 = new JButton("");
	private JButton bouton3 = new JButton("");
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

	public FenetreAffichageResultats(Compte c, JFrame fenetrePrec) throws SQLException, ClassNotFoundException, ChampIncorrectException{

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		this.setCompte(c);
		this.setFenetrePrecedente(fenetrePrec);

		this.setTitle("Affichage des résultats");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ c.getId());
		labelAdminTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdminTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,600));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,50));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		bouton1.setPreferredSize(new Dimension(250,40));
		bouton1.setMaximumSize(new Dimension(250,40));
		bouton1.setFont(FenetreAuthentificationUtil.POLICE3);
		bouton1.setBackground(Color.GREEN);
		
		//cas possibles pour l'administrateur
		if(fenetrePrec.getTitle().equals("Fréquentation des stations")){
			FenetreFrequentationStationsAdmin f = (FenetreFrequentationStationsAdmin) fenetrePrec;
			DiagrammeFreqStations diag = new DiagrammeFreqStations(f.getPeriodeEntree());
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
			bouton1.setText("Fréquentation pour une autre période");
			bouton1.addActionListener(this);
			south.add(bouton1);
		}

		else if(fenetrePrec.getTitle().equals("Informations sur un compte")){
			FenetreInfoCompteAdmin f = (FenetreInfoCompteAdmin) fenetrePrec;
			DiagrammeNbEmpruntsUtilisateur diag = new DiagrammeNbEmpruntsUtilisateur(DAOUtilisateur.getUtilisateurById(f.getCompte().getId()));
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
			center.add(lblChart);
			bouton1.setText("Afficher statistiques sur un autre utilisateur");
			bouton1.addActionListener(this);
			south.add(bouton1);
		}


		else if(fenetrePrec.getTitle().equals("Menu <interventions de maintenance> de l'administrateur")){
			DiagrammeNbInterventions diag = new DiagrammeNbInterventions();
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
		}


		else if(fenetrePrec.getTitle().equals("Historique d'un vélo")){
			JLabel labelMsg = new JLabel ("Ici on affichera l'historique du vélo sous forme de tableau");
			center.add(labelMsg);
		}

		else if((fenetrePrec.getTitle().equals("Voir l'état d'une station")) || (fenetrePrec.getTitle().equals("Stations sur et sous occupées"))){

			JLabel lblChart1 = new JLabel();
			JLabel lblChart2 = new JLabel();
			
			JPanel panel1 = new JPanel();
			panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			panel1.setPreferredSize(new Dimension(300,300));
			
			JPanel panel2 = new JPanel();
			panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			panel2.setPreferredSize(new Dimension(300,300));
			
			DiagrammeNbVelosStation diag1;
			DiagrammeTxOccupationStation diag2;

			FenetreEtatStationAdmin f1 = null;
			FenetreStationsSurSousAdmin f2 = null;
			if (fenetrePrec.getTitle().equals("Voir l'état d'une station")) {
				f1 = (FenetreEtatStationAdmin) fenetrePrec;
				diag1 = new DiagrammeNbVelosStation(f1.getStationEntree());
				lblChart1.setIcon(new ImageIcon(diag1.getImage()));
				diag2 = new DiagrammeTxOccupationStation(f1.getStationEntree());
				lblChart2.setIcon(new ImageIcon(diag2.getImage()));
			}
			if (fenetrePrec.getTitle().equals("Stations sur et sous occupées")) {
				f2 = (FenetreStationsSurSousAdmin) fenetrePrec;
				diag1 = new DiagrammeNbVelosStation(f2.getStationEntree());
				lblChart1.setIcon(new ImageIcon(diag1.getImage()));
				diag2 = new DiagrammeTxOccupationStation(f2.getStationEntree());
				lblChart2.setIcon(new ImageIcon(diag2.getImage()));
			}

			panel1.add(lblChart1);
			panel2.add(lblChart2);
			
			center.add(panel1);
			center.add(panel2);
			
			bouton1.setText("Voir l'état d'une autre station");
			bouton2.setText("Voir les stations sur et sous-occupées");
			bouton3.setText("Envoyer une demande d'assignation");

			bouton1.setPreferredSize(new Dimension(200,40));
			bouton1.setMaximumSize(new Dimension(200,40));
			bouton1.setFont(FenetreAuthentificationUtil.POLICE3);
			bouton1.setBackground(Color.GREEN);
			
			bouton2.setPreferredSize(new Dimension(200,40));
			bouton2.setMaximumSize(new Dimension(200,40));
			bouton2.setFont(FenetreAuthentificationUtil.POLICE3);
			bouton2.setBackground(Color.GREEN);
			bouton2.addActionListener(this);
			
			bouton3.setPreferredSize(new Dimension(350,40));
			bouton3.setMaximumSize(new Dimension(350,40));
			bouton3.setFont(FenetreAuthentificationUtil.POLICE3);
			bouton3.setBackground(Color.CYAN);
			bouton3.addActionListener(this);
			
			south.add(bouton1);
			south.add(bouton2);
			center.add(bouton3);
			
		}



		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
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
			// différents cas possibles pour l'administrateur
			if(arg0.getSource()==bouton1){
				if(this.getFenetrePrecedente().getTitle().equals("Fréquentation des stations")){
					new FenetreFrequentationStationsAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
				}
				else if(this.getFenetrePrecedente().getTitle().equals("Informations sur un compte")){
					new FenetreRechercherCompteAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()),true);
				}
				else if((this.getFenetrePrecedente().getTitle().equals("Voir l'état d'une station")) || (this.getFenetrePrecedente().getTitle().equals("Stations sur et sous occupées"))){
					new FenetreEtatStationAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
				}
			}
			else if(arg0.getSource()==bouton2){
				new FenetreStationsSurSousAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
			}
			else if(arg0.getSource()==bouton3){
				new FenetreEnvoyerDemandeAssignationAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
			}
			else if(arg0.getSource()==boutonRetour){
				new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
			}
		} catch (SQLException e) {
			MsgBox.affMsg("SQLException "+e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg("ClassNotFoundException "+e.getMessage());
		}
	}
}