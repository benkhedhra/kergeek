package ihm.appliAdminTech.technicien;

import exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Station;
import metier.Technicien;
import metier.Velo;

public class FenetreRemettreVeloEnStationTech extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez remplir les champs suivants");
	private JLabel labelVelo = new JLabel("identifiant du vélo");
	private JTextField idARemplir = new JTextField("");
	private JLabel labelStation = new JLabel("station");
	private Station stationEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public FenetreRemettreVeloEnStationTech(Technicien t) throws ConnexionFermeeException {

		System.out.println("Fenêtre pour remettre un vélo réparé en station");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Remettre un vélo réparé en station");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setTechnicien(t);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentification.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,100));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerCenter = new JPanel();
		centerCenter.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerCenter.setLayout(new GridLayout(2,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelVelo.setPreferredSize(new Dimension(250,40));
		labelVelo.setMaximumSize(new Dimension(250,40));
		panel1.add(labelVelo);
		centerCenter.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		idARemplir.setPreferredSize(new Dimension(250,40));
		idARemplir.setMaximumSize(new Dimension(250,40));
		panel2.add(idARemplir);
		centerCenter.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelStation.setPreferredSize(new Dimension(250,40));
		labelStation.setMaximumSize(new Dimension(250,40));
		panel3.add(labelStation);
		centerCenter.add(panel3);

		try {
			List<Station> listeStations = DAOLieu.getAllStations();
			String [] tableauStations = new String[listeStations.size()];
			for (int i=0;i<listeStations.size();i++){
				tableauStations[i]=listeStations.get(i).toString();
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);
			center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			JComboBox combo = new JComboBox(model);
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String chaineSelectionnee = (String)(o);
					String idStationEntre = chaineSelectionnee.substring(0,1);
					try {
						stationEntree = (Station) DAOLieu.getLieuById(idStationEntre);
					} catch (SQLException e) {
						MsgBox.affMsg(e.getMessage());
					} catch (ClassNotFoundException e) {
						MsgBox.affMsg(e.getMessage());
					}
					catch (ConnexionFermeeException e){
						MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
						new FenetreAuthentification(false);
					}
					labelMsg.setText("Station sélectionnée : " + stationEntree.getAdresse());
					labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
				}

			});
			JPanel panel4 = new JPanel();
			panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			panel4.add(combo);
			centerCenter.add(panel4);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		center.add(centerCenter,BorderLayout.CENTER);

		JPanel centerSouth = new JPanel();
		centerSouth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonValider.setPreferredSize(new Dimension(150,40));
		boutonValider.setMaximumSize(new Dimension(150,40));
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.addActionListener(this);
		centerSouth.add(boutonValider);
		center.add(centerSouth, BorderLayout.SOUTH);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if (arg0.getSource()==boutonValider){
				Velo velo;
				velo = DAOVelo.getVeloById(idARemplir.getText());
				if(UtilitaireIhm.verifieSiPlaceDisponibleDansStation(stationEntree)){
					velo.setLieu(stationEntree);
					velo.setEnPanne(false);
					DAOVelo.updateVelo(velo);
					new FenetreConfirmation(this.getTechnicien().getCompte(),this);
				}
				else{
					MsgBox.affMsg("<html><center>Il n'y a plus de places disponibles dans cette station. <br> Merci de trouver une autre station <center></html>");
					new MenuPrincipalTech(this.getTechnicien());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
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