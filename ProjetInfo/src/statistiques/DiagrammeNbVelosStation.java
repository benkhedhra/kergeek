package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOVelo;

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

	private JFreeChart chart;

	public DiagrammeNbVelosStation(Station station) {

		super("");
		chart = createChart(station);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		//chartPanel.setPreferredSize(new Dimension(500, 270));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(500, 500);
	}

	JFreeChart createChart(Station station) {

		// create subplot
		final XYSeriesCollection data1 = createDataset(station);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final NumberAxis axeVelo = new NumberAxis("nombre de vélos");
		axeVelo.setAutoRangeIncludesZero(false);
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
		return new JFreeChart("Nombre de vélos de la station " + station.getAdresse()+ " le " + UtilitaireDate.dateCourante(),
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	}


	private XYSeriesCollection createDataset(Station station) {


		
		GregorianCalendar calendar = new GregorianCalendar(); 
		int heureencours = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure1 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure2 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure3 = calendar.get(Calendar.HOUR_OF_DAY);

		final XYSeries series = new XYSeries("Nombre de vélos");
		try {
			System.out.println("nb vélos sortis pdt l'heure "+DAOEmprunt.NombreVelosSortisHeures(station, 1));
			System.out.println("nb vélos sortis pdt l'heure "+DAOEmprunt.NombreVelosRentresHeures(station, 1));
			System.out.println("nb vélos sortis pdt les deux heures "+DAOEmprunt.NombreVelosSortisHeures(station, 2));
			System.out.println("nb vélos rentres pdt les deux heures "+DAOEmprunt.NombreVelosRentresHeures(station, 2));
			System.out.println("nb vélos sortis pdt les trois heures "+DAOEmprunt.NombreVelosSortisHeures(station, 3));
			System.out.println("nb vélos rentres pdt les trois heures "+DAOEmprunt.NombreVelosRentresHeures(station, 3));
			series.add(heureencours, DAOVelo.getVelosByLieu(station).size());
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
		


		final XYSeries series2 = new XYSeries("Capacité de la station");
		series2.add(heureencours, station.getCapacite());
		series2.add(heure3, station.getCapacite());
		series2.add(heure2, station.getCapacite());
		series2.add(heure1, station.getCapacite());


		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(series2);
		return collection;

	}

	public static void main(final String[] args) throws SQLException, ClassNotFoundException {
		final DiagrammeNbVelosStation demo = new DiagrammeNbVelosStation((Station) DAOLieu.getLieuById("2"));
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
