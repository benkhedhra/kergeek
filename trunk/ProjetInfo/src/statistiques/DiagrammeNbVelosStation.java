package statistiques;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOVelo;

import java.awt.Dimension;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

	public DiagrammeNbVelosStation(Station station) throws ConnexionFermeeException {

		super("");
		chart = createChart(station);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(300, 300));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(300, 300);
	}

	JFreeChart createChart(Station station) throws ConnexionFermeeException {

		// create subplot
		final XYSeriesCollection data1 = createDataset(station);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final NumberAxis axeVelo = new NumberAxis("nombre de v�los");
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
		return new JFreeChart("Nombre de v�los de la station " + station.getAdresse()+ " le " + UtilitaireDate.dateCourante(),
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	}


	private XYSeriesCollection createDataset(Station station) throws ConnexionFermeeException {


		
		GregorianCalendar calendar = new GregorianCalendar(); 
		int heureencours = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure1 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure2 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure3 = calendar.get(Calendar.HOUR_OF_DAY);

		final XYSeries series = new XYSeries("Nombre de v�los");
		try {
			series.add(heureencours, DAOVelo.getVelosByLieu(station).size());
			//System.out.println("nb v�los ds la station = "+DAOVelo.getVelosByLieu(station).size());
			series.add(heure1, DAOVelo.getVelosByLieu(station).size()
					+ (DAOEmprunt.NombreVelosSortisHeures(station, 1))
					- (DAOEmprunt.NombreVelosRentresHeures(station, 1)));
			series.add(heure2,  DAOVelo.getVelosByLieu(station).size()
					+ (DAOEmprunt.NombreVelosSortisHeures(station, 2))
					- (DAOEmprunt.NombreVelosRentresHeures(station, 2)));
			series.add(heure3,  DAOVelo.getVelosByLieu(station).size()
					+ (DAOEmprunt.NombreVelosSortisHeures(station, 3))
					- (DAOEmprunt.NombreVelosRentresHeures(station, 3)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		final XYSeries series2 = new XYSeries("Capacit� de la station");
		series2.add(heureencours, station.getCapacite());
		series2.add(heure3, station.getCapacite());
		series2.add(heure2, station.getCapacite());
		series2.add(heure1, station.getCapacite());


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
