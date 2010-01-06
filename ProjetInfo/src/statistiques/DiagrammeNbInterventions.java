package statistiques;


import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOTypeIntervention;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Image;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import metier.Intervention;

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

/**
 * La classe DiagrammeNbInterventions permet de créer le diagramme relatif aux nombres d'interventions par type d'intervention sur tous les vélos.
 * @see Intervention
 * @author KerGeek
 */
public class DiagrammeNbInterventions extends ApplicationFrame {

	//Attributs
	private static final long serialVersionUID = 1L;

	/**
	 * @see DiagrammeFreqStations#getImage()
	 */
	private JFreeChart chart;
	
	//Constructeur
	
	/**
	 * Création d'un diagramme des nombres d'interventions par type d'intervention.
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws ClassNotFoundExceptionException
	 * @see DiagrammeNbInterventions#createDataset()
	 * @see DiagrammeNbInterventions#createChart(CategoryDataset)
	 */
	public DiagrammeNbInterventions() throws ConnexionFermeeException, SQLException, ClassNotFoundException {
		
		super("");
		CategoryDataset dataset = createDataset();
		chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(800, 800));

		setContentPane(chartPanel);

	}

	
	//Méthodes
	
	/**
	 * @return l'{@link DiagrammeNbInterventions#chart} du diagramme sous forme d'image.
	 * @see JFreeChart#createBufferedImage(int, int)
	 */
	public Image getImage() {
		return this.chart.createBufferedImage(800, 800);
	}
	
	/**
	 * Création des données utiles dans le diagramme.
	 * @return dataset
	 * les données
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 * @see DAOTypeIntervention#getAllTypesIntervention()
	 * @see DAOIntervention#getNombresVelosParTypeIntervention(int)
	 */
	private static CategoryDataset createDataset() throws ConnexionFermeeException, SQLException, ClassNotFoundException {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// étiquettes des lignes...
		String series1 = "nombre d'interventions de ce type";
		
		// création des données
		Map<Integer, String> m = DAOTypeIntervention.getAllTypesIntervention();
		List <List <Integer>> liste = DAOIntervention.getNombresVelosParTypeIntervention(6);
		for (List <Integer> duo : liste){
			dataset.addValue(duo.get(1),series1,m.get(duo.get(0)));
			System.out.println(m.get(duo.get(0)));
		}
		
		return dataset;

	}

	/**
	 * Création du diagramme à partir des données.
	 * @param dataset
	 * les données à représenter
	 * @return chart
	 * le diagramme
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 */
	private static JFreeChart createChart(CategoryDataset dataset) {

		// créer le graphique
		JFreeChart chart = ChartFactory.createBarChart(
				"Nombre d'interventions par type ces six derniers mois", 	// titre du diagramme
				"Types d'intervention",               // nom de l'axe des abscisses
				"Nombre d'intervention",                  // nom de l'axe des coordonnées
				dataset,                  // données
				PlotOrientation.VERTICAL, // orientation
				true,                     // présenter la legende
				true,                     // tooltips?
				false                     // URLs?
		);


		
		// couleur de l'arrière plan du graphique
		chart.setBackgroundPaint(Color.white);

		// customisation de l'environnement du graphique
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// réglage de l'axe des abscisses pour qu'il ne présente que des entiers
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// désactivation des bar outlines
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// définitions de la couleur de la série
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 0, 64)
		);
		renderer.setSeriesPaint(0, gp0);

		// orientation de la légende
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 3.0)
		);
		
		// Réglage de la taille des battons à travers l'espace entre 2 batons
		domainAxis.setCategoryMargin(0.6);

		return chart;

	}
}