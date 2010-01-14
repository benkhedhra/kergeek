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
import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.Station;

/**
 * FenetreVoirVelosDansLieuAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsque l'Administrateur a cliqué "voir la liste des vélos présents en un lieu" de son {@link MenuVoirEtatAdmin}
 * <br>elle permet à l'Administrateur de sélectionner le {@link Lieu} pour lequel il souhaite voir les vélos présents
 * <br>ce lieu peut être une {@link Station}, mais aussi le {@link Garage} ou encore la {@link Sortie}
 * @author KerGeek
 */
public class FenetreVoirVelosDansLieuAdmin extends JFrame implements ActionListener {
	
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
	private JLabel labelChemin = new JLabel("Menu principal > Voir l'état du parc > Liste des vélos présents dans un lieu");
	private JLabel labelMsg = new JLabel("Veuillez entrer le lieu (station ou garage) dont vous voulez voir les vélos");
	
	/**
	 * le lieu sélectionné par l'Administrateur
	 */
	private Lieu lieuEntre;
	
	/**
	 * 3 JButton permettant à l'Administrateur de valider le lieu sélectionné (Station ou Garage), de voir les vélos sortis (lieu Sortie), ou de retourner au menu principal
	 */
	private JButton boutonValider = new JButton("Valider le lieu sélectionné");
	private JButton boutonVelosSortis = new JButton("Voir les vélos sortis");
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
	 * attribut lieuEntre
	 */
	public Lieu getLieuEntre() {
		return lieuEntre;
	}

	public void setLieuEntre(Lieu lieuEntre) {
		this.lieuEntre = lieuEntre;
	}

	/**
	 * constructeur de {@link FenetreVoirVelosDansLieuAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 * @throws ConnexionFermeeException
	 * @see {@link DAOLieu#getStationsEtGarage()}
	 */
	public FenetreVoirVelosDansLieuAdmin(Administrateur a) throws ConnexionFermeeException{

		System.out.println("Fenêtre pour avoir la liste des vélos présents dans un lieu");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Voir la liste des vélos présents dans un lieu");
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
		north.setPreferredSize(new Dimension(1200,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,800));
		labelMsg.setPreferredSize(new Dimension(1200,100));
		center.add(labelMsg);

		List<Lieu> listeStations;
		try {
			listeStations = DAOLieu.getStationsEtGarage();
			System.out.println(listeStations);
			final String [] tableauStations = new String[listeStations.size()+1];
			tableauStations[0]="Sélectionnez un lieu";
			for (int i=0;i<listeStations.size();i++){
				tableauStations[i+1]=listeStations.get(i).toString();
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);
			JComboBox combo = new JComboBox(model);
			combo.setPreferredSize(new Dimension(300,40));
			combo.setFont(UtilitaireIhm.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String chaineSelectionnee = (String)(o);
					if(chaineSelectionnee.equals(tableauStations[0])){
						lieuEntre=null;
					}
					else{
						String idStationEntre = chaineSelectionnee.substring(0,1);
						try {
							lieuEntre = (Station) DAOLieu.getLieuById(idStationEntre);
						} catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
							new FenetreAuthentification(false);
						} catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
							new FenetreAuthentification(false);
						}
						catch (ConnexionFermeeException e){
							MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
							new FenetreAuthentification(false);
						}
					}
					repaint();
				}
			});
			center.add(combo);

			boutonValider.setPreferredSize(new Dimension(300,50));
			boutonValider.setMaximumSize(new Dimension(300,50));
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(UtilitaireIhm.POLICE3);
			boutonValider.addActionListener(this);
			center.add(boutonValider);

			boutonVelosSortis.setPreferredSize(new Dimension(300,50));
			boutonVelosSortis.setMaximumSize(new Dimension(300,50));
			boutonVelosSortis.setBackground(Color.CYAN);
			boutonVelosSortis.setFont(UtilitaireIhm.POLICE3);
			boutonVelosSortis.addActionListener(this);
			center.add(boutonVelosSortis);
			
			this.getContentPane().add(center,BorderLayout.CENTER);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

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
	 * méthode exécutée quand l'Administrateur a cliqué sur l'un des 3 boutons écoutés par la fenêtre
	 * @param arg0
	 * l'action source
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			//s'il a cliqué sur "Valider" après avoir sélectionné un lieu (ou non)
			if (arg0.getSource()==boutonValider){
				if(this.getLieuEntre()==null){
					MsgBox.affMsg("Vous n'avez sélectionné aucun lieu. ");
					new FenetreVoirVelosDansLieuAdmin(this.getAdministrateur());
				}
				else{
					//il a sélectionné un lieu valide (ici une station ou le garge)
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
				}
			}
			//s'il a cliqué sur "Voir les vélos sortis"
			else if (arg0.getSource()==boutonVelosSortis){
				//comme s'il avait sélectionné un lieu parmi les autres, ici la sortie
				//bouton à part pour plus de lisibilité
				this.setLieuEntre(Sortie.getInstance());
				new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
			}
			//s'il a cliqué sur "Retourner au menu principal"
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalAdmin(this.getAdministrateur());
			}
		} 
		catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		} 
		catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
			new MenuPrincipalAdmin(this.getAdministrateur());
		} 
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}