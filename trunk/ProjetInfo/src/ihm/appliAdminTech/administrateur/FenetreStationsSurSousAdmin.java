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
 * FenetreStationsSurSousAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle intervient lorsque l'administrateur a voulu directement avoir acc�s � une liste des stations sur et sous-occup�es
 * <br>donc dont le taux d'occupation est inf�rieur au taux minimal (20%) ou sup�rieur au taux maximal (80%)
 * @author KerGeek
 */
public class FenetreStationsSurSousAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connect� sur la fen�tre
	 */
	private Administrateur administrateur;

	/**
	 * 3 JLabel permettant d'afficher l'id de l'{@link Administrateur} connect�n l'endroit o� il se trouve dans l'application et le message d'invitation � faire son choix
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Voir l'�tat du parc > Stations sur et sous occup�es");
	private JLabel labelMsg = new JLabel("Veuillez s�lectionner une station");

	/**
	 * la station s�lectionn�e parmi les statios sur et sous-occup�es
	 */
	Station stationEntree;

	/**
	 * 2 JButton proposant � l'Administrateur de valider son choix de station ou de retourner au menu principal
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
		System.out.println("Fen�tre pour voir les stations sur et sous occup�es");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Stations sur et sous occup�es");
		//D�finit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
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
			tableauStations[0] = (listeStationsSur.size()+listeStationsSous.size())+" stations sur ou sous-occup�es";
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
							MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
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
	 * m�thode ex�cut�e quand l'Administrateur a cliqu� sur l'un des deux boutons qui lui �taient propos�s
	 * @param arg0
	 * l'action source
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqu� sur "Valider"
		if (arg0.getSource()==boutonValider){
			try {
				if(this.getStationEntree()==null){
					MsgBox.affMsg("Vous n'avez s�lectionn� aucune station. ");
					new FenetreStationsSurSousAdmin(this.getAdministrateur());
				}
				else{
					//il a s�lectionn� une station valide
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//s'il a cliqu� sur "Retourner au menu principal"
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}