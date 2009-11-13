package statistiques;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

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

public class DiagrammeNbIntervention extends ApplicationFrame {

	/**
	 * Creates a new demo instance.
	 *
	 * @param title  the frame title.
	 */
	
	private JFreeChart chart;
	
	public DiagrammeNbIntervention(String title, JFreeChart chart) {
		
		super(title);
		CategoryDataset dataset = createDataset();
		this.setChart(createChart(dataset));
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);

	}

	
	
	public JFreeChart getChart() {
		return chart;
	}



	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}



	/**
	 * Returns a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private static CategoryDataset createDataset() {
		
		
		// row keys...
		String series1 = "nombre d'intervention de ce type";


		// column keys...
		String category1 = "pneu crevé";
		String category2 = "selle";
		String category3 = "pédale";
		String category4 = "déraillement";
		String category5 = "frein";
		String category6 = "autres";


		// create the dataset...
		
		/**TODO changer les valeurs!!!
		 * 
		 */
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(5.0, series1, category1);
		dataset.addValue(11.0, series1, category2);
		dataset.addValue(12.0, series1, category3);
		dataset.addValue(3.0, series1, category4);
		dataset.addValue(8.0, series1, category5);
		dataset.addValue(10.0, series1, category6);



		return dataset;

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset  the dataset.
	 * 
	 * @return The chart.
	 */
	private static JFreeChart createChart(CategoryDataset dataset) {

		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart(
				"Nombre d'interventions ces six derniers mois", 	// chart title
				"Types d'intervention",               // domain axis label
				"Nombre d'intervention",                  // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				true,                     // include legend
				true,                     // tooltips?
				false                     // URLs?
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

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
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}
	
	

     // Starting point for the demonstration application.

   /*public static void main(String[] args) {

    	DiagrammeNbIntervention demo = new DiagrammeNbIntervention("", );
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }*/


}

