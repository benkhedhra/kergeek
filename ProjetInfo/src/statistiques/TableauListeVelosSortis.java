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

import metier.Sortie;
import metier.Velo;


public class TableauListeVelosSortis extends JPanel {
	private boolean DEBUG = false;


	public TableauListeVelosSortis() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		super(new GridLayout(1,0));


		String[] columnNames = {"Identifiant du vélo",
				"Dernière Station fréquentée",
				"Utilisateur",
				"Date de l'emprunt",
				"Heure de l'emprunt"};

		
		List<Velo> liste = DAOVelo.getVelosByLieu(Sortie.getInstance());
		
		Object[][] donnees = new Object[liste.size()][5];
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
		final MonJTable table = new MonJTable(donnees, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 100));
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
	private static void createAndShowGUI() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//Create and set up the window.
		JFrame frame = new JFrame("Liste des vélos sortis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		TableauListeVelosSortis newContentPane = new TableauListeVelosSortis();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws ConnexionFermeeException{
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConnexionFermeeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
