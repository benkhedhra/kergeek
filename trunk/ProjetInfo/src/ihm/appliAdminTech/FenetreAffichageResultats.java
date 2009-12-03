package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.appliAdminTech.administrateur.FenetreEtatStationAdmin;
import ihm.appliAdminTech.administrateur.FenetreFrequentationStationsAdmin;
import ihm.appliAdminTech.administrateur.FenetreInfoCompteAdmin;
import ihm.appliAdminTech.administrateur.FenetreStationsSurSousAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.PanneauTech;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
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

	public FenetreAffichageResultats(Compte c, JFrame fenetrePrec) throws SQLException, ClassNotFoundException, ChampIncorrectException{

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		compte=c;
		fenetrePrecedente=fenetrePrec;

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
		center.setPreferredSize(new Dimension(700,40));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	

		//cas possibles pour l'administrateur
		if(fenetrePrec.getTitle().equals("Fréquentation des stations")){
			FenetreFrequentationStationsAdmin f = (FenetreFrequentationStationsAdmin) fenetrePrec;
			DiagrammeFreqStations diag = new DiagrammeFreqStations(f.getPeriodeEntree());
			JLabel lblChart = new JLabel();
			lblChart.setIcon(new ImageIcon(diag.getImage()));
			center.add(lblChart);
			bouton1.setText("Fréquentation d'une autre station");
			bouton1.addActionListener(this);
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
		
		this.getContentPane().add(center,BorderLayout.CENTER);


		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}