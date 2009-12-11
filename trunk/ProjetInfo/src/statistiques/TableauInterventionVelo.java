package statistiques;

import exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOVelo;

import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import metier.Velo;

public class TableauInterventionVelo extends JPanel {
	private boolean DEBUG = false;

	public TableauInterventionVelo(Velo v) {
		super(new GridLayout(1,0));

		String[] columnNames = {"Date d'entrée au garage",
				"Date de sortie du garage",
		"Type d'intervention"};

		Object[][] data = {
				{"tre", "Campione",
				"Snowboarding"}};

		final JTable table = new JTable(data, columnNames);
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

		System.out.println("Value of data: ");
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
	private static void createAndShowGUI(Velo v) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//Create and set up the window.
		JFrame frame = new JFrame("Interventions sur le vélo " + v.getId());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		TableauInterventionVelo newContentPane = new TableauInterventionVelo(DAOVelo.getVeloById("10"));
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
					createAndShowGUI(DAOVelo.getVeloById("10"));
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
