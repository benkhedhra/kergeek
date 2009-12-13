package statistiques;


import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.Station;

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

public class DiagrammeFreqStations extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFreeChart chart;

	public DiagrammeFreqStations(String periodeEntree) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		super("Fréquentation des stations sur les "+periodeEntree);
		CategoryDataset dataset = createDataset(periodeEntree);
		chart = createChart(dataset,periodeEntree);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(550, 400));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(550,400);
	}

	private static CategoryDataset createDataset(String periodeEntree) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// étiquettes des lignes...
		String sortis = "Vélos sortis";
		String entres = "Vélos entrés";

		// étiquettes des colonnes...
			List<Station> stations = DAOLieu.getAllStations();
			ArrayList<String> category = new ArrayList<String>(stations.size());
			for (int i=0;i<stations.size();i++){
				category.add(DAOLieu.getLieuById(stations.get(i).getId()).getAdresse());
			}

			int nbJoursEntre=365;
			if (periodeEntree.equals("30 derniers jours")){
				nbJoursEntre = 30;
			}
			else if (periodeEntree.equals("60 derniers jours")){
				nbJoursEntre = 60;
			}
			else if (periodeEntree.equals("6 derniers mois")){
				nbJoursEntre = 183;
			}
			else if (periodeEntree.equals("365 derniers jours")){
				nbJoursEntre = 365;
			}


			for(int i=0;i<category.size();i++){
				dataset.addValue(DAOEmprunt.NombreVelosSortis(DAOLieu.getLieuById(stations.get(i).getId()), nbJoursEntre), sortis, category.get(i));
				dataset.addValue(DAOEmprunt.NombreVelosRentres(DAOLieu.getLieuById(stations.get(i).getId()), nbJoursEntre), entres, category.get(i));
			}
		return dataset;

	}

	private static JFreeChart createChart(CategoryDataset dataset,String periodeEntree) {

		// créer le chart
		JFreeChart chart = ChartFactory.createBarChart(
				("Fréquentation Stations sur les "+periodeEntree),
				"Stations",               // domain axis label
				"Nombre de vélos",        // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				true,                     // include legend
				true,                     // tooltips?
				false                     // URLs?
		);


		// couleur de l'arrière plan du chart
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
		GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0)
		);

		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
		);

		return chart;

	}

	public static void main(final String[] args) throws ConnexionFermeeException, SQLException, ClassNotFoundException {
		final DiagrammeFreqStations demo = new DiagrammeFreqStations("30 derniers jours");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
