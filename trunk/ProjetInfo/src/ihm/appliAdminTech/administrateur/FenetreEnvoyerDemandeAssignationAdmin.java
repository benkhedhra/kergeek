package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOLieu;
import ihm.MsgBox;
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

import metier.Administrateur;
import metier.DemandeAssignation;
import metier.Station;
import metier.UtilitaireDate;

public class FenetreEnvoyerDemandeAssignationAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez entrer les paramètres de la demande d'assignation");
	private JLabel labelStation = new JLabel("Station");
	private Station stationEntree;
	private JLabel labelNbVelos = new JLabel("Nombre de vélos souhaité");
	private JTextField nbVelosARemplir = new JTextField ("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonEtatAutreStation = new JButton ("Voir l'état d'une autre station");
	private JButton boutonStationsSurSous = new JButton ("Voir les stations sur et sous occupées");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public FenetreEnvoyerDemandeAssignationAdmin (Administrateur a){

		System.out.println("Fenêtre pour envoyer une demande d'assignation");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Envoyer une demande d'assignation");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));

		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		labelMsg.setPreferredSize(new Dimension (600,30));
		center.add(labelMsg);

		center.setPreferredSize(new Dimension(550,350));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelStation.setPreferredSize(new Dimension (250,30));
		center.add(labelStation);
		
		List<Station> listeStations;
		try {
			listeStations = DAOLieu.getAllStations();
			String [] tableauStations = new String[listeStations.size()+1];
			tableauStations[0]="Sélectionnez une station";
			for (int i=0;i<listeStations.size();i++){
				tableauStations[i+1]=listeStations.get(i).toString();
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);
			JComboBox combo = new JComboBox(model);
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					try{
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
					}
					catch (Exception  e){
						System.out.println("Erreur dans la sélection de la station");
					}
				}
			});

			combo.setPreferredSize(new Dimension (250,30));
			center.add(combo);
			labelNbVelos.setPreferredSize(new Dimension (250,30));
			center.add(labelNbVelos);
			nbVelosARemplir.setPreferredSize(new Dimension (250,30));
			center.add(nbVelosARemplir);

			center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			center.setPreferredSize(new Dimension(200,350));
			boutonValider.setPreferredSize(new Dimension(200,40));
			boutonValider.setMaximumSize(new Dimension(200,40));
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.addActionListener(this);
			center.add(boutonValider);

			this.getContentPane().add(center,BorderLayout.CENTER);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}


		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		boutonEtatAutreStation.setPreferredSize(new Dimension(250,40));
		boutonEtatAutreStation.setMaximumSize(new Dimension(250,40));
		boutonEtatAutreStation.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonEtatAutreStation.setBackground(Color.GREEN);
		boutonEtatAutreStation.addActionListener(this);
		south.add(boutonEtatAutreStation);

		boutonStationsSurSous.setPreferredSize(new Dimension(250,40));
		boutonStationsSurSous.setMaximumSize(new Dimension(250,40));
		boutonStationsSurSous.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonStationsSurSous.setBackground(Color.GREEN);
		boutonStationsSurSous.addActionListener(this);
		south.add(boutonStationsSurSous);

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
			try{
				int nbVelos = 0;
				nbVelos = Integer.parseInt(nbVelosARemplir.getText());
				new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				new DemandeAssignation(nbVelos,stationEntree);
			}catch(Exception e){
				MsgBox.affMsg("Champ entré incorret");
			}
		}
		else if (arg0.getSource()==boutonEtatAutreStation){
			new FenetreEtatStationAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonStationsSurSous){
			new FenetreStationsSurSousAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}

