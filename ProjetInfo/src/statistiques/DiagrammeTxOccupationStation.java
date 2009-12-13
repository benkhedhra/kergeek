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


public class DiagrammeTxOccupationStation extends ApplicationFrame {

	private JFreeChart chart;

	public DiagrammeTxOccupationStation(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		super("");
		chart = createChart(station);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(300, 300));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(300, 300);
	}

	JFreeChart createChart(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		// create subplot
		final XYSeriesCollection data1 = createDataset(station);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final NumberAxis rangeAxis1 = new NumberAxis("");
		final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
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
		return new JFreeChart("Taux d' occupation de la station " + station.getAdresse() + " le " + UtilitaireDate.dateCourante(),
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	}


	private XYSeriesCollection createDataset(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {


		
		GregorianCalendar calendar = new GregorianCalendar(); 
		int heureencours = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure3 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure2 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure1 = calendar.get(Calendar.HOUR_OF_DAY);
		

		final XYSeries series = new XYSeries("Taux d'occupation");
			series.add(heureencours, (DAOVelo.getVelosByLieu(station).size()*100)/station.getCapacite());
			series.add(heure1, ((DAOVelo.getVelosByLieu(station).size() 
					+ DAOEmprunt.NombreVelosSortisHeures(station, 1)
					- DAOEmprunt.NombreVelosRentresHeures(station, 1))*100)/station.getCapacite());
			series.add(heure2, ((DAOVelo.getVelosByLieu(station).size() 
					+ DAOEmprunt.NombreVelosSortisHeures(station, 2)
					- DAOEmprunt.NombreVelosRentresHeures(station, 2))*100)/station.getCapacite());
			series.add(heure3, ((DAOVelo.getVelosByLieu(station).size() 
					+ DAOEmprunt.NombreVelosSortisHeures(station, 3)
					- DAOEmprunt.NombreVelosRentresHeures(station, 3))*100)/station.getCapacite());
		


		final XYSeries series2 = new XYSeries("Taux d'occupation minimum");
		series2.add(heureencours, Station.TAUX_OCCUPATION_MIN * 100);
		series2.add(heure3, Station.TAUX_OCCUPATION_MIN * 100);
		series2.add(heure2, Station.TAUX_OCCUPATION_MIN * 100);
		series2.add(heure1, Station.TAUX_OCCUPATION_MIN * 100);

		final XYSeries series3 = new XYSeries("Taux d'occupation maximum");
		series3.add(heureencours, Station.TAUX_OCCUPATION_MAX * 100);
		series3.add(heure3, Station.TAUX_OCCUPATION_MAX * 100);
		series3.add(heure2, Station.TAUX_OCCUPATION_MAX * 100);
		series3.add(heure1, Station.TAUX_OCCUPATION_MAX * 100);

		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(series2);
		collection.addSeries(series3);
		return collection;

	}

	public static void main(final String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		final DiagrammeTxOccupationStation demo = new DiagrammeTxOccupationStation((Station)DAOLieu.getLieuById("2"));
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
