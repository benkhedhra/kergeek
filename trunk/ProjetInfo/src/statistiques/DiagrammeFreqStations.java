package statistiques;


import exceptionsIhm.ChampIncorrectException;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;

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

	public DiagrammeFreqStations(String periodeEntree) throws ChampIncorrectException {

		super("Fr�quentation des stations sur les "+periodeEntree);
		CategoryDataset dataset = createDataset(periodeEntree);
		chart = createChart(dataset,periodeEntree);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(550, 400));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(550,400);
	}

	private static CategoryDataset createDataset(String periodeEntree) throws ChampIncorrectException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// �tiquettes des lignes...
		String sortis = "V�los sortis";
		String entres = "V�los entr�s";

		// �tiquettes des colonnes...
		try {
			List<Station> stations = DAOLieu.getAllStations();
			ArrayList<String> category = new ArrayList<String>(stations.size());
			for (int i=0;i<stations.size();i++){
				category.add(DAOLieu.getLieuById(stations.get(i).getId()).getAdresse());
			}

			int nbJoursEntre;
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
			else {
				throw new ChampIncorrectException();
			}


			for(int i=0;i<category.size();i++){
				dataset.addValue(DAOEmprunt.NombreVelosSortis(DAOLieu.getLieuById(stations.get(i).getId()), nbJoursEntre), sortis, category.get(i));
				dataset.addValue(DAOEmprunt.NombreVelosRentres(DAOLieu.getLieuById(stations.get(i).getId()), nbJoursEntre), entres, category.get(i));
			}

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataset;

	}

	private static JFreeChart createChart(CategoryDataset dataset,String periodeEntree) {

		// cr�er le chart
		JFreeChart chart = ChartFactory.createBarChart(
				("Fr�quentation Stations sur les "+periodeEntree),
				"Stations",               // domain axis label
				"Nombre de v�los",        // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				true,                     // include legend
				true,                     // tooltips?
				false                     // URLs?
		);


		// couleur de l'arri�re plan du chart
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

	public static void main(final String[] args) throws ChampIncorrectException {
		final DiagrammeFreqStations demo = new DiagrammeFreqStations("30 derniers jours");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
