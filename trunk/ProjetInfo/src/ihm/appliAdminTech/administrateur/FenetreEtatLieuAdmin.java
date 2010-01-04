package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

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
import metier.Lieu;
import metier.Station;

/**
 * FenetreEtatLieuAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle demande à l'administrateur connecté de sélectionner une station parmi les stations existantes afin d'obtenir des graphiques sur son état actuel
 * @author KerGeek
 */
public class FenetreEtatLieuAdmin extends JFrame implements ActionListener {
	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur administrateur;
	/**
	 * le lieu (station ou garage) sélectionné par l'administrateur
	 */
	private Lieu lieuEntre;
	
	/**
	 * 2 JLabel permettant d'afficher l'id de l'{@link Administrateur} connecté et le message d'invitation à sélectionner un {@link Lieu}
	 */
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("Veuillez entrer le lieu (station ou garage) dont vous voulez voir l'état");

	/**
	 * 2 JButton permettant de valider le lieu sélectionné ou de retourner au menu principal
	 */
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("<html><center>Retour au<br>menu principal</center></html>");

	// Accesseurs utiles
	
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public Lieu getLieuEntre() {
		return lieuEntre;
	}

	public void setLieuEntre(Lieu lieuEntre) {
		this.lieuEntre = lieuEntre;
	}

	/**
	 * constructeur de {@link FenetreEtatLieuAdmin}
	 * <br>appelé lorsque l'administrateur a cliqué sur "Voir état d'un lieu" dans son {@link MenuVoirEtatAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 * @throws ConnexionFermeeException
	 */
	public FenetreEtatLieuAdmin(Administrateur a) throws ConnexionFermeeException{

		System.out.println("Fenêtre pour avoir l'état d'une station");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Voir l'état d'un lieu");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
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
		labelAdmin.setPreferredSize(new Dimension(500,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));

		JPanel centerWest = new JPanel();
		centerWest.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerWest.setPreferredSize(new Dimension(500,350));
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
							repaint();
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
			});
			centerWest.add(labelMsg);
			centerWest.add(combo);

			boutonValider.setPreferredSize(new Dimension(200,40));
			boutonValider.setMaximumSize(new Dimension(200,40));
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(UtilitaireIhm.POLICE3);
			boutonValider.addActionListener(this);
			centerWest.add(boutonValider);

			center.add(centerWest,BorderLayout.WEST);

			this.getContentPane().add(center,BorderLayout.CENTER);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}


		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'administrateur a cliqué sur l'un des deux boutons qui lui étaient proposés
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			try {
				if(this.getLieuEntre()==null){
					MsgBox.affMsg("Vous n'avez sélectionné aucun lieu. ");
					new FenetreEtatLieuAdmin(this.getAdministrateur());
				}
				else{
					// l'administrateur a bien sélectionné un lieu de la liste puis a validé
					// FenetreAffichageResultatsAdmin affichera les graphiques adéquats en fonction de la fenêtre en cours
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
				}
			} 
			catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} 
			catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}