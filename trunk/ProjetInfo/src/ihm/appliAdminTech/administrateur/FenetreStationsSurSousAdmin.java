package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAffichageResultats;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import metier.Administrateur;
import metier.Station;

public class FenetreStationsSurSousAdmin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez s�lectionner une station");
	Station stationEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public Station getStationEntree() {
		return stationEntree;
	}

	public void setStationEntree(Station stationEntree) {
		this.stationEntree = stationEntree;
	}

	public FenetreStationsSurSousAdmin (Administrateur a){
		System.out.println("Fen�tre pour voir les stations sur et sous occup�es");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Stations sur et sous occup�es");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,400));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelMsg);
		JLabel labelLegende = new JLabel();
		labelLegende.setText("Station         Nombre de v�los         Etat de la station");	
		labelLegende.setFont(FenetreAuthentificationUtil.POLICE2);
		center.add(labelLegende);

		List<Station> listeStationsSur;
		List<Station> listeStationsSous;
		try {
			listeStationsSur = DAOLieu.getStationsSurSous().get(0);
			listeStationsSous = DAOLieu.getStationsSurSous().get(1);
			
			String [][] tableauStations = new String[(listeStationsSur.size()+listeStationsSous.size())][3];
			for (int i=0;i<listeStationsSur.size();i++){
				Station stationi = listeStationsSur.get(i);
				String[] lignei = tableauStations[i];
				lignei[0] = "Station " + stationi.getId();
				lignei[1] = ""+DAOVelo.getVelosByLieu(stationi).size();
				lignei[2] = "sur-occup�e";
				}
			for (int i=0;i<listeStationsSous.size();i++){
				Station stationi = listeStationsSous.get(i);
				String[] lignei = tableauStations[i];
				lignei[0] = "Station " + stationi.getId();
				lignei[1] = ""+DAOVelo.getVelosByLieu(stationi).size();
				lignei[2] = "sous-occup�e";
				}
			
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);

			JComboBox tableau = new JComboBox(model);
			tableau.setFont(FenetreAuthentificationUtil.POLICE3);
			tableau.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					try {
						stationEntree = (Station) DAOLieu.getLieuById((String)o);
					} catch (SQLException e) {
						MsgBox.affMsg(e.getMessage());
					} catch (ClassNotFoundException e) {
						MsgBox.affMsg(e.getMessage());
					}
				}
			});
			center.add(tableau);
			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.addActionListener(this);
			center.add(boutonValider);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		
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
		if (arg0.getSource()==boutonValider){
			new FenetreAffichageResultats(this.getAdministrateur().getCompte(),this);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}