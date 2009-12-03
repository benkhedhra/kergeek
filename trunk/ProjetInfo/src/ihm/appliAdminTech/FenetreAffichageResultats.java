package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOUtilisateur;
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
import exception.ChampIncorrectException;

public class FenetreAffichageResultats extends JFrame implements ActionListener {

	private Compte compte;
	private JFrame fenetrePrecedente;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
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
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,450));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		//cas possibles pour l'administrateur
		if(fenetrePrec.getTitle().equals("Fréquentation des stations")){
			FenetreFrequentationStationsAdmin f = (FenetreFrequentationStationsAdmin) fenetrePrec;
			DiagrammeFreqStations diag = new DiagrammeFreqStations(f.getPeriodeEntree());
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
			bouton1.setText("Fréquentation d'une autre station");
			bouton1.addActionListener(this);
			south.add(bouton1);
		}

		if(fenetrePrec.getTitle().equals("Informations sur un compte")){
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


		if(fenetrePrec.getTitle().equals("Menu <interventions de maintenance> de l'administrateur")){
			DiagrammeNbInterventions diag = new DiagrammeNbInterventions();
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
		}


		if((fenetrePrec.getTitle().equals("Voir l'état d'une station")) || (fenetrePrec.getTitle().equals("Stations sur et sous occupées"))){
			FenetreEtatStationAdmin f1 = null;
			FenetreStationsSurSousAdmin f2 = null;
			if (fenetrePrec.getTitle().equals("Voir l'état d'une station")) {
				f1 = (FenetreEtatStationAdmin) fenetrePrec;
			}
			if (fenetrePrec.getTitle().equals("Stations sur et sous occupées")) {
				f2 = (FenetreStationsSurSousAdmin) fenetrePrec;
			}
			center.setLayout(new BorderLayout());
			DiagrammeNbVelosStation diag1 = new DiagrammeNbVelosStation(f1.getStationEntree());
			JLabel lblChart1 = new JLabel();
			lblChart1.setIcon(new ImageIcon(diag1.getImage()));
			JPanel centerWest = new JPanel();
			centerWest.setPreferredSize(new Dimension(300,300));
			centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			centerWest.add(lblChart1);
			DiagrammeTxOccupationStation diag2 = new DiagrammeTxOccupationStation(f2.getStationEntree());
			JLabel lblChart2 = new JLabel();
			lblChart2.setIcon(new ImageIcon(diag2.getImage()));
			center.add(lblChart2);
			JPanel centerEast = new JPanel();
			centerEast.setPreferredSize(new Dimension(300,300));
			centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			centerEast.add(lblChart1);

			center.add(centerWest,BorderLayout.WEST);
			center.add(centerEast,BorderLayout.EAST);
		}

		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		south.add(boutonRetour);

		this.getContentPane().add(center,BorderLayout.CENTER);
		this.getContentPane().add(south,BorderLayout.SOUTH);


		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		// différents cas possibles pour l'administrateur
		if(arg0.getSource()==bouton1){
			if(this.getFenetrePrecedente().getTitle().equals("Fréquentation des stations")){
				try {
					new FenetreFrequentationStationsAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(this.getFenetrePrecedente().getTitle().equals("Informations sur un compte")){
				try {
					new FenetreRechercherCompteAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()),true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(arg0.getSource()==boutonRetour){
			try {
				new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(this.getCompte().getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}