package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOLieu;
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
import metier.Compte;
import metier.Station;

public class FenetreEtatStationAdmin extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("Veuillez entrer la station dont vous voulez voir l'�tat");
	private Station stationEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public FenetreEtatStationAdmin(Administrateur a){

		System.out.println("Fen�tre pour avoir l'�tat d'une station");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Voir l'�tat d'une station");
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


		List<Station> listeStations;
		try {
			listeStations = DAOLieu.getAllStations();
			Station [] tableauStations = new Station[listeStations.size()];
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);
			
			JPanel center = new JPanel();
			center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			center.setPreferredSize(new Dimension(700,350));
			center.setLayout(new BorderLayout());

			JPanel centerNorth = new JPanel();
			centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
			centerNorth.add(labelMsg);
			center.add(centerNorth,BorderLayout.NORTH);
			
			JPanel centerWest = new JPanel();
			centerWest.setPreferredSize(new Dimension(550,350));
			centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			JComboBox combo = new JComboBox(model);
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					stationEntree = (Station)o;
					labelMsg.setText("Station s�lectionn�e : " + stationEntree.getId());
					labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
				}

			});
			centerWest.add(combo);

			center.add(centerWest,BorderLayout.WEST);

			JPanel centerEast = new JPanel();
			centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			centerEast.setPreferredSize(new Dimension(200,350));
			boutonValider.setPreferredSize(new Dimension(80,40));
			boutonValider.setMaximumSize(new Dimension(80,40));
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.addActionListener(this);
			centerEast.add(boutonValider);
			center.add(centerEast,BorderLayout.EAST);

			this.getContentPane().add(center,BorderLayout.CENTER);
			
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			new FenetreAffichageResultats(/*this,this.getAdministrateur().getCompte()*/);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}

	public static void main (String [] args){
		Administrateur ATEST = new Administrateur(new Compte(Compte.TYPE_ADMINISTRATEUR));
		new FenetreEtatStationAdmin(ATEST);
	}
}
