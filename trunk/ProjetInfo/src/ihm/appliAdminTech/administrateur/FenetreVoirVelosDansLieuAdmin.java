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
 * FenetreVoirVelosDansLieuAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle intervient lorsque l'Administrateur a cliqu� "voir la liste des v�los pr�sents en un lieu" de son {@link MenuVoirEtatAdmin}
 * <br>elle permet � l'Administrateur de s�lectionner le {@link Lieu} pour lequel il souhaite voir les v�los pr�sents
 * <br>ce lieu peut �tre une {@link Station}, mais aussi le {@link Garage} ou encore la {@link Sortie}
 * @author KerGeek
 */
public class FenetreVoirVelosDansLieuAdmin extends JFrame implements ActionListener {
	
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
	private JLabel labelChemin = new JLabel("Menu principal > Voir l'�tat du parc > Liste des v�los pr�sents dans un lieu");
	private JLabel labelMsg = new JLabel("Veuillez entrer le lieu (station ou garage) dont vous voulez voir les v�los");
	
	/**
	 * le lieu s�lectionn� par l'Administrateur
	 */
	private Lieu lieuEntre;
	
	/**
	 * 3 JButton permettant � l'Administrateur de valider le lieu s�lectionn� (Station ou Garage), de voir les v�los sortis (lieu Sortie), ou de retourner au menu principal
	 */
	private JButton boutonValider = new JButton("Valider le lieu s�lectionn�");
	private JButton boutonVelosSortis = new JButton("Voir les v�los sortis");
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
	 * l'administrateur connect� � la fen�tre
	 * @throws ConnexionFermeeException
	 * @see {@link DAOLieu#getStationsEtGarage()}
	 */
	public FenetreVoirVelosDansLieuAdmin(Administrateur a) throws ConnexionFermeeException{

		System.out.println("Fen�tre pour avoir la liste des v�los pr�sents dans un lieu");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Voir la liste des v�los pr�sents dans un lieu");
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
			tableauStations[0]="S�lectionnez un lieu";
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
							MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
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
	 * m�thode ex�cut�e quand l'Administrateur a cliqu� sur l'un des 3 boutons �cout�s par la fen�tre
	 * @param arg0
	 * l'action source
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			//s'il a cliqu� sur "Valider" apr�s avoir s�lectionn� un lieu (ou non)
			if (arg0.getSource()==boutonValider){
				if(this.getLieuEntre()==null){
					MsgBox.affMsg("Vous n'avez s�lectionn� aucun lieu. ");
					new FenetreVoirVelosDansLieuAdmin(this.getAdministrateur());
				}
				else{
					//il a s�lectionn� un lieu valide (ici une station ou le garge)
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
				}
			}
			//s'il a cliqu� sur "Voir les v�los sortis"
			else if (arg0.getSource()==boutonVelosSortis){
				//comme s'il avait s�lectionn� un lieu parmi les autres, ici la sortie
				//bouton � part pour plus de lisibilit�
				this.setLieuEntre(Sortie.getInstance());
				new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
			}
			//s'il a cliqu� sur "Retourner au menu principal"
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
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}