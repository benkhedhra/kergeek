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

public class DiagrammeFreqStations extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFreeChart chart;

	public DiagrammeFreqStations(String periodeEntree) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		super("Fr�quentation des stations sur les " + periodeEntree);
		CategoryDataset dataset = createDataset(periodeEntree);
		chart = createChart(dataset,periodeEntree);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(800, 800));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(800,800);
	}

	private static CategoryDataset createDataset(String periodeEntree) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// �tiquettes des s�ries
		String sortis = "V�los sortis";
		String entres = "V�los entr�s";

		// �tiquettes des abscisses
		List<Station> stations = DAOLieu.getAllStations();
		ArrayList<String> category = new ArrayList<String>(stations.size());
		for (int i=0;i<stations.size();i++){
			category.add(DAOLieu.getLieuById(stations.get(i).getId()).getAdresse());
		}
		
		// cr�ation des donn�es
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
			dataset.addValue(DAOEmprunt.NombreVelosRendusJours(stations.get(i), nbJoursEntre), entres, category.get(i));
			dataset.addValue(DAOEmprunt.NombreVelosSortisJours(stations.get(i), nbJoursEntre), sortis, category.get(i));

		}
		return dataset;

	}

	private static JFreeChart createChart(CategoryDataset dataset,String periodeEntree) {

		// cr�er le graphique
		JFreeChart chart = ChartFactory.createBarChart(
				("Fr�quentation Stations sur les "+periodeEntree),
				"Stations",               // nom de l'axe des abscisses
				"Nombre de v�los",        // nom de l'axe des ordonn�es
				dataset,                  // donn�es
				PlotOrientation.VERTICAL, // orientation
				true,                     // pr�senter la l�gende
				true,                     // tooltips 
				false                     // URLs
		);


		// couleur de l'arri�re plan du graphique
		chart.setBackgroundPaint(Color.white);

		// customisation de l'environnement du graphique
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// r�glage de l'axe des abscisses pour qu'il ne pr�sente que des entiers
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// d�sactivation des bar outlines
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		
		// suppression de l'espace entre les 2 bars des 2 s�ries pour une m�me station
		renderer.setItemMargin(0.0);

		//  d�finitions des couleurs des 2 s�ries
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0)
		);
		GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 0, 64)
		);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);

		// orientation de la l�gende
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
		);

		return chart;

	}

}
