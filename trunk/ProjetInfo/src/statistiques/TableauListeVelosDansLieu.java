package statistiques;

import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.Velo;

/**
 * La classe TableauListeVelosDansLieu permet de cr�er le tableau listant v�los pr�sents dans un lieu donn�.
 * @author KerGeek
 */
public class TableauListeVelosDansLieu extends JPanel {

	private static final long serialVersionUID = 1L;

	
	//Constructeur
	
	/**
	 * Cr�ation d'un tableau listant les v�los pr�sents dans un lieu � partir d'un {@link TableauListeVelosDansLieu#lieu}.
	 * @param lieu
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 */
	public TableauListeVelosDansLieu(Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		super(new GridLayout(1,0));

		List<Velo> listeVelos;
		Object[][] donnees;
		final MonJTable table;		


		if(lieu.getId().equals(Lieu.ID_SORTIE)){
			//Noms des colonnes du tableau
			String[] nomsColonnes = {
					"Identifiant du v�lo",
					"Derni�re Station fr�quent�e",
					"Utilisateur",
					"Date de l'emprunt",
					"Heure de l'emprunt"
			};

			listeVelos = DAOVelo.getVelosByLieu(Sortie.getInstance());

			// cr�ation des donn�es
			donnees = new Object[listeVelos.size()][5];
			for(int k=0;k<listeVelos.size();k++){

				// affichage de l'identifiant du v�lo sorti dans la console
				System.out.println("V�lo " + listeVelos.get(k).getId());

				// d�claration d'un calendrier
				GregorianCalendar gCal = new GregorianCalendar();
				// initialise le calendrier � la date de l'emprunt en cours du v�lo concern�;
				gCal.setTime(listeVelos.get(k).getEmpruntEnCours().getDateEmprunt());

				// initialisation des donn�es du tableau
				donnees[k][0] = listeVelos.get(k).getId();
				donnees[k][1] = listeVelos.get(k).getEmpruntEnCours().getStationEmprunt().getAdresse();
				donnees[k][2] = listeVelos.get(k).getEmpruntEnCours().getUtilisateur().getCompte().getId();
				donnees[k][3] = gCal.get(Calendar.DAY_OF_MONTH)+"/"+ (gCal.get(Calendar.MONTH)+1);
				donnees[k][4] = gCal.get(Calendar.HOUR_OF_DAY) + "h" + gCal.get(Calendar.MINUTE);
			}
			//cr�ation du tableau
			table = new MonJTable(donnees, nomsColonnes);
		}

		else if(lieu.getId().equals(Lieu.ID_GARAGE)){
			//Noms des colonnes du tableau
			String[] nomsColonnes = {
					"Identifiant du v�lo",
					"En panne ? "
			};

			listeVelos = DAOVelo.getVelosByLieu(Garage.getInstance());

			// cr�ation des donn�es
			donnees = new Object[listeVelos.size()][2];
			for(int k=0;k<listeVelos.size();k++){
				// affichage de l'identifiant du v�lo au garage dans la console
				System.out.println("V�lo " + listeVelos.get(k).getId());

				// initialisation des donn�es du tableau
				donnees[k][0] = listeVelos.get(k).getId();
				String enPanne;
				if(listeVelos.get(k).isEnPanne()){
					enPanne="oui";
				}
				else{
					enPanne="non";
				}
				donnees[k][1] = enPanne;
			}
			//cr�ation du tableau
			table = new MonJTable(donnees, nomsColonnes);
		}

		else {
			//Noms des colonnes du tableau
			String[] columnNames = {
					"Identifiant du v�lo",
					"En panne ? "
			};

			listeVelos = DAOVelo.getVelosByLieu(lieu);
			
			// cr�ation des donn�es
			donnees = new Object[listeVelos.size()][2];
			for(int k=0;k<listeVelos.size();k++){
				// affichage de l'identifiant du v�lo parqu� dans cette station dans la console
				System.out.println("V�lo " + listeVelos.get(k).getId());
				
				// initialisation des donn�es du tableau
				donnees[k][0] = listeVelos.get(k).getId();
				String enPanne;
				if(listeVelos.get(k).isEnPanne()){
					enPanne="oui";
				}
				else{
					enPanne="non";
				}
				donnees[k][1] = enPanne;
			}
			//cr�ation du tableau
			table = new MonJTable(donnees, columnNames);
		}

		table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowCount()*16));
		table.setFillsViewportHeight(true);

		//cr�ation du d�filement (au cas ou le tableau serait trop grand)
		JScrollPane scrollPane = new JScrollPane(table);

		//ajout du d�filement
		add(scrollPane);
	}

}
