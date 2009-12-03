package statistiques;


import gestionBaseDeDonnees.DAOIntervention;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Image;
import java.sql.SQLException;

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

public class DiagrammeNbInterventions extends ApplicationFrame {

	/**
	 * Creates a new demo instance.
	 *
	 * @param title  the frame title.
	 */
	
	private JFreeChart chart;
	
	public DiagrammeNbInterventions() {
		
		super("");
		CategoryDataset dataset = createDataset();
		chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(550, 400);
	}
	
	private static CategoryDataset createDataset() {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// �tiquettes des lignes...
		String series1 = "nombre d'intervention de ce type";


		// �tiquettes des colonnes...
		String category1 = "pneu crev�";
		String category2 = "selle manquante";
		String category3 = "p�dale cass�e";
		String category4 = "d�raillement";
		String category5 = "frein";
		String category6 = "autres";


		// cr�er la dataset...
		/*TODO v�rifier que le.get(i) corresponde au bon type d'intervention*/
		

		try {
			dataset.addValue(DAOIntervention.getNombresVelosParTypeIntervention(6).get(0), series1, category1);
			dataset.addValue(DAOIntervention.getNombresVelosParTypeIntervention(6).get(1), series1, category2);
			dataset.addValue(DAOIntervention.getNombresVelosParTypeIntervention(6).get(2), series1, category3);
			dataset.addValue(DAOIntervention.getNombresVelosParTypeIntervention(6).get(3), series1, category4);
			dataset.addValue(DAOIntervention.getNombresVelosParTypeIntervention(6).get(4), series1, category5);
			dataset.addValue(DAOIntervention.getNombresVelosParTypeIntervention(6).get(5), series1, category6);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		



		return dataset;

	}

	private static JFreeChart createChart(CategoryDataset dataset) {

		// cr�er la chart...
		JFreeChart chart = ChartFactory.createBarChart(
				"Nombre d'interventions par type ces six derniers mois", 	// chart title
				"Types d'intervention",               // domain axis label
				"Nombre d'intervention",                  // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				true,                     // include legend
				true,                     // tooltips?
				false                     // URLs?
		);



		// couleur de l'arrri�re plan du chart
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
	
	public static void main(final String[] args) {
		final DiagrammeNbInterventions demo = new DiagrammeNbInterventions();
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}
	

}

