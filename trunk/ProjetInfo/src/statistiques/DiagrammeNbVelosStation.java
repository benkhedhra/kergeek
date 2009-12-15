package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Dimension;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import metier.Lieu;
import metier.Station;
import metier.UtilitaireDate;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class DiagrammeNbVelosStation extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFreeChart chart;

	public DiagrammeNbVelosStation(Lieu lieu) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		super("");
		chart = createChart(lieu);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(300, 300));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(300, 300);
	}

	JFreeChart createChart(Lieu lieu) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		// create subplot
		final XYSeriesCollection data1 = createDataset(lieu);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final NumberAxis axeVelo = new NumberAxis("nombre de vélos");
		final XYPlot subplot1 = new XYPlot(data1, null, axeVelo, renderer1);
		subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);



		// parent plot...
		NumberAxis axeHeures = new NumberAxis("Heure");
		axeHeures.setAutoRangeIncludesZero(false);
		axeHeures.setTickUnit(new NumberTickUnit(1));
		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(axeHeures);
		plot.setGap(10.0);
		// add the subplots...
		plot.add(subplot1, 1);
		plot.setOrientation(PlotOrientation.VERTICAL);

		// return a new chart containing the overlaid plot...
		return new JFreeChart("Nombre de vélos de la station " + lieu.getAdresse()+ " le " + UtilitaireDate.dateCourante(),
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	}


	private XYSeriesCollection createDataset(Lieu lieu) throws ConnexionFermeeException, SQLException, ClassNotFoundException {


		
		GregorianCalendar calendar = new GregorianCalendar(); 
		int heureencours = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure1 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure2 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure3 = calendar.get(Calendar.HOUR_OF_DAY);

		final XYSeries series = new XYSeries("Nombre de vélos");
			series.add(heureencours, DAOVelo.getVelosByLieu(lieu).size());
			series.add(heure1, DAOVelo.getVelosByLieu(lieu).size()
					+ (DAOEmprunt.NombreVelosSortisHeures(lieu, 1))
					- (DAOEmprunt.NombreVelosRentresHeures(lieu, 1)));
			series.add(heure2,  DAOVelo.getVelosByLieu(lieu).size()
					+ (DAOEmprunt.NombreVelosSortisHeures(lieu, 2))
					- (DAOEmprunt.NombreVelosRentresHeures(lieu, 2)));
			series.add(heure3,  DAOVelo.getVelosByLieu(lieu).size()
					+ (DAOEmprunt.NombreVelosSortisHeures(lieu, 3))
					- (DAOEmprunt.NombreVelosRentresHeures(lieu, 3)));


		final XYSeries series2 = new XYSeries("Capacité de l'endroit");
		series2.add(heureencours, lieu.getCapacite());
		series2.add(heure3, lieu.getCapacite());
		series2.add(heure2, lieu.getCapacite());
		series2.add(heure1, lieu.getCapacite());


		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(series2);
		return collection;

	}

	public static void main(final String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		final DiagrammeNbVelosStation demo = new DiagrammeNbVelosStation((Station) DAOLieu.getLieuById("2"));
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
