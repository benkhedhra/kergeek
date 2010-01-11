package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOLieu;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Station;

/**
 * FenetreStationsSurSousAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsque l'administrateur a voulu directement avoir accès à une liste des stations sur et sous-occupées
 * <br>donc dont le taux d'occupation est inférieur au taux minimal (20%) ou supérieur au taux maximal (80%)
 * @author KerGeek
 */
public class FenetreStationsSurSousAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur administrateur;

	/**
	 * 3 JLabel permettant d'afficher l'id de l'{@link Administrateur} connectén l'endroit où il se trouve dans l'application et le message d'invitation à faire son choix
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Voir l'état du parc > Stations sur et sous occupées");
	private JLabel labelMsg = new JLabel("Veuillez sélectionner une station");

	/**
	 * la station sélectionnée parmi les statios sur et sous-occupées
	 */
	Station stationEntree;

	/**
	 * 2 JButton proposant à l'Administrateur de valider son choix de station ou de retourner au menu principal
	 */
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	//Accesseurs utiles

	/*
	 * attribut administrateur
	 */
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	/*
	 * attribut stationEntree
	 */
	public Station getStationEntree() {
		return stationEntree;
	}

	public void setStationEntree(Station stationEntree) {
		this.stationEntree = stationEntree;
	}

	/**
	 * constructeur de {@link FenetreStationsSurSousAdmin}
	 * @param a
	 * l'administrateur 
	 * @throws ConnexionFermeeException
	 * @see {@link DAOLieu#getStationsSurSous()}
	 */
	public FenetreStationsSurSousAdmin (Administrateur a) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour voir les stations sur et sous occupées");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Stations sur et sous occupées");
		//Définit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		labelAdmin.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1200,800));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setPreferredSize(new Dimension(1100,100));
		center.add(labelMsg);

		List<Station> listeStationsSur;
		List<Station> listeStationsSous;
		try {
			listeStationsSur = DAOLieu.getStationsSurSous().get(0);
			listeStationsSous = DAOLieu.getStationsSurSous().get(1);
			final String [] tableauStations = new String[(listeStationsSur.size()+listeStationsSous.size())+1];
			tableauStations[0] = (listeStationsSur.size()+listeStationsSous.size())+" stations sur ou sous-occupées";
			for (int i=0;i<listeStationsSur.size();i++){
				Station stationi = listeStationsSur.get(i);
				tableauStations[i+1]=DAOLieu.ligneStationSur(stationi);
			}
			for (int i=0;i<listeStationsSous.size();i++){
				Station stationi = listeStationsSous.get(i);
				tableauStations[i+1+listeStationsSur.size()]=DAOLieu.ligneStationSous(stationi);
			}

			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);

			JComboBox tableau = new JComboBox(model);
			tableau.setPreferredSize(new Dimension(350,40));
			tableau.setFont(UtilitaireIhm.POLICE3);
			tableau.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String chaineSelectionnee = (String)(o);
					if(chaineSelectionnee.equals(tableauStations[0])){
						stationEntree=null;
					}
					else{
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
					}
					repaint();
				}
			});
			center.add(tableau);
			boutonValider.setFont(UtilitaireIhm.POLICE3);
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setPreferredSize(new Dimension(250,50));
			boutonValider.addActionListener(this);
			center.add(boutonValider);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'Administrateur a cliqué sur l'un des deux boutons qui lui étaient proposés
	 * @param arg0
	 * l'action source
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqué sur "Valider"
		if (arg0.getSource()==boutonValider){
			try {
				if(this.getStationEntree()==null){
					MsgBox.affMsg("Vous n'avez sélectionné aucune station. ");
					new FenetreStationsSurSousAdmin(this.getAdministrateur());
				}
				else{
					//il a sélectionné une station valide
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
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
		//s'il a cliqué sur "Retourner au menu principal"
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}