package statistiques;

import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.Velo;


public class TableauListeVelosDansLieu extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private boolean DEBUG = false;


	public TableauListeVelosDansLieu(Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		super(new GridLayout(1,0));

		List<Velo> liste;
		Object[][] donnees;
		final MonJTable table;		
		
		if(lieu.getId().equals(Lieu.ID_SORTIE)){
			String[] columnNames = {"Identifiant du vélo",
					"Dernière Station fréquentée",
					"Utilisateur",
					"Date de l'emprunt",
			"Heure de l'emprunt"};
			
			liste = DAOVelo.getVelosByLieu(Sortie.getInstance());
			
			donnees = new Object[liste.size()][5];
			for(int k=0;k<liste.size();k++){

				// déclaration d'un calendrier
				GregorianCalendar gCal = new GregorianCalendar();
				// initialise le calendrier à la date courante;
				System.out.println("Vélo " + liste.get(k).getId());
				gCal.setTime(liste.get(k).getEmpruntEnCours().getDateEmprunt());

				donnees[k][0] = liste.get(k).getId();
				donnees[k][1] = liste.get(k).getEmpruntEnCours().getLieuEmprunt().getAdresse();
				donnees[k][2] = liste.get(k).getEmpruntEnCours().getUtilisateur().getCompte().getId();
				donnees[k][3] = gCal.get(Calendar.DAY_OF_MONTH)+"/"+ (gCal.get(Calendar.MONTH)+1);
				donnees[k][4] = gCal.get(Calendar.HOUR_OF_DAY) + "h" + gCal.get(Calendar.MINUTE);
			}
			table = new MonJTable(donnees, columnNames);
		}

		else if(lieu.getId().equals(Lieu.ID_GARAGE)){
			String[] columnNames = {"Identifiant du vélo",
					"En panne ? "};
			
			liste = DAOVelo.getVelosByLieu(Garage.getInstance());
			
			donnees = new Object[liste.size()][2];
			for(int k=0;k<liste.size();k++){
				System.out.println("Vélo " + liste.get(k).getId());
				donnees[k][0] = liste.get(k).getId();
				String enPanne;
				if(liste.get(k).isEnPanne()){
					enPanne="oui";
				}
				else{
					enPanne="non";
				}
				donnees[k][1] = enPanne;
			}
			table = new MonJTable(donnees, columnNames);
		}
		
		else {
			String[] columnNames = {"Identifiant du vélo",
					"En panne ? "};
			
				liste = DAOVelo.getVelosByLieu(lieu);
				
				donnees = new Object[liste.size()][2];
				for(int k=0;k<liste.size();k++){
					System.out.println("Vélo " + liste.get(k).getId());

					donnees[k][0] = liste.get(k).getId();
					String enPanne;
					if(liste.get(k).isEnPanne()){
						enPanne="oui";
					}
					else{
						enPanne="non";
					}
					donnees[k][1] = enPanne;
				}
				table = new MonJTable(donnees, columnNames);
			
		}

		liste = DAOVelo.getVelosByLieu(Sortie.getInstance());

		table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowCount()*16));
		table.setFillsViewportHeight(true);


		if (DEBUG) {
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					printDebugData(table);
				}
			});
		}


		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane);
	}


	private void printDebugData(JTable table) {
		int numRows = table.getRowCount();
		int numCols = table.getColumnCount();
		javax.swing.table.TableModel model = table.getModel();

		System.out.println("Value : ");
		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + model.getValueAt(i, j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws ConnexionFermeeException 
	 */
	private static void createAndShowGUI(Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//Create and set up the window.
		JFrame frame = new JFrame("Liste des vélos dans un lieu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		TableauListeVelosDansLieu newContentPane = new TableauListeVelosDansLieu(lieu);
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}


}
