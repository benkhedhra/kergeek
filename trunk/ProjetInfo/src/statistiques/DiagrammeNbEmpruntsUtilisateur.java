package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import metier.Utilisateur;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


	public class DiagrammeNbEmpruntsUtilisateur extends ApplicationFrame{
	
		private JFreeChart chart;

		
		public DiagrammeNbEmpruntsUtilisateur(Utilisateur u) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

			super("Nombre d'emprunts de l'utilisateur demandé pour les six derniers mois");
			CategoryDataset dataset = createDataset(u);
			chart = createChart(dataset,u);
			ChartPanel chartPanel = new ChartPanel(chart, false);
			chartPanel.setPreferredSize(new Dimension(550, 400));
			this.setContentPane(chartPanel);

		}

		public Image getImage() {
			return this.chart.createBufferedImage(550,400);
		}

		private static CategoryDataset createDataset(Utilisateur u) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

			// étiquettes des lignes
			String emprunts = "Nombre d'emprunts";


			// étiquettes des colonnes
			Calendar calendar = Calendar.getInstance(); 
			int moisencours = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, -1);
			int moisent1 = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, -1);
			int moisent2 = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, -1);
			int moisent3 = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, -1);
			int moisent4 = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, -1);
			int moisent5 = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, -1);
			int moisent6 = calendar.get(Calendar.MONTH);
			
			
			ResourceBundle resourceBundle = ResourceBundle.getBundle("statistiques/utils",Locale.FRENCH);
			String nomMoisEnCours = resourceBundle.getString("mois."+moisencours);
			
			
			String nomMoisEnCours1 = resourceBundle.getString("mois."+moisent1);
			String nomMoisEnCours2 = resourceBundle.getString("mois."+moisent2);
			String nomMoisEnCours3 = resourceBundle.getString("mois."+moisent3);
			String nomMoisEnCours4 = resourceBundle.getString("mois."+moisent4);
			String nomMoisEnCours5 = resourceBundle.getString("mois."+moisent5);
			String nomMoisEnCours6 = resourceBundle.getString("mois."+moisent6);
			
			

			// créer la dataset...
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			dataset.addValue(DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 6).get(5), emprunts, nomMoisEnCours6);
			dataset.addValue(DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 5).get(4), emprunts, nomMoisEnCours5);
			dataset.addValue(DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 4).get(3), emprunts, nomMoisEnCours4);
			dataset.addValue(DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 3).get(2), emprunts, nomMoisEnCours3);
			dataset.addValue(DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 2).get(1), emprunts, nomMoisEnCours2);
			dataset.addValue(DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 1).get(0), emprunts, nomMoisEnCours1);

			return dataset;

		}

		private static JFreeChart createChart(CategoryDataset dataset,Utilisateur u) {

			// create the chart
			JFreeChart chart = ChartFactory.createBarChart(
					("Nombre d'emprunts de l'utilisateur "+ u.getPrenom()+" " +u.getNom() + " pour les six derniers mois"),
					"6 derniers mois",               // domain axis label
					"Nombre d' emprunts",        // range axis label
					dataset,                  // data
					PlotOrientation.VERTICAL, // orientation
					true,                     // include legend
					true,                     // tooltips?
					false                     // URLs?
			);


			// set the background color for the chart...
			chart.setBackgroundPaint(Color.white);

			// get a reference to the plot for further customisation...
			CategoryPlot plot = chart.getCategoryPlot();
			plot.setBackgroundPaint(Color.lightGray);
			plot.setDomainGridlinePaint(Color.white);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinePaint(Color.white);

			// set the range axis to display integers only...
			final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			// disable bar outlines...
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setDrawBarOutline(false);

			// set up gradient paints for series...
			GradientPaint gp0 = new GradientPaint(
					0.0f, 0.0f, Color.blue, 
					0.0f, 0.0f, new Color(0, 0, 64)
			);

			renderer.setSeriesPaint(0, gp0);

			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setCategoryLabelPositions(
					CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
			);

			return chart;

		}
		
		public static void main(final String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
			final DiagrammeNbEmpruntsUtilisateur demo = new DiagrammeNbEmpruntsUtilisateur(DAOUtilisateur.getUtilisateurById("u1"));
			demo.pack();
			RefineryUtilities.centerFrameOnScreen(demo);
			demo.setVisible(true);

		}

	}


