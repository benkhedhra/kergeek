package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
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


public class DiagrammeTxOccupationStation extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	private JFreeChart chart;

	public DiagrammeTxOccupationStation(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		super("");
		this.chart = createChart(station);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(600,600));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(600, 600);
	}

	JFreeChart createChart(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		//initialisation de l'axe des ordonnées
		final NumberAxis axeTaux = new NumberAxis("taux d'occupation (en %)");// titre
		axeTaux.setLabelFont(new Font("Arial Narrow", Font.BOLD,16));// police

		// création du sous-dessin
		final XYSeriesCollection data1 = createDataset(station);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final XYPlot subplot1 = new XYPlot(data1, null, axeTaux, renderer1);
		subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);



		// initialisation de l'axe des abscisses
		NumberAxis axeHeures = new NumberAxis("Heure");// titre
		axeHeures.setAutoRangeIncludesZero(false);// désactivation de l'obligation d'inclure le zéro sur l'axe
		axeHeures.setTickUnit(new NumberTickUnit(1));// graduation
		axeHeures.setLabelFont(new Font("Arial Narrow", Font.BOLD,16));// police

		// création du dessin
		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(axeHeures);
		plot.setGap(10.0);

		// ajout du sous-dessin
		plot.add(subplot1, 1);

		//orientation du dessin
		plot.setOrientation(PlotOrientation.VERTICAL);

		// création du graphique
		JFreeChart chart = new JFreeChart("Taux d' occupation de la station "+station.getAdresse()+ " le "
				+UtilitaireDate.Presenter(UtilitaireDate.dateCourante()), // titre
				JFreeChart.DEFAULT_TITLE_FONT, // police
				plot, 
				true);

		// couleurs des séries
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0)
		);
		GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 0, 64)
		);
		GradientPaint gp2 = new GradientPaint(
				0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, new Color(64, 0, 0)
		);
		renderer1.setSeriesPaint(0, gp0); // taux d'occupation
		renderer1.setSeriesPaint(1, gp1); // minimum
		renderer1.setSeriesPaint(2, gp2); // maximum

		// epaisseur des séries délimitant le maximum et le minimum
		renderer1.setSeriesStroke(1, new BasicStroke(2.5f)); // minimum
		renderer1.setSeriesStroke(2, new BasicStroke(2.5f)); // maximum

		return chart;
	}

	// Création des données
	private XYSeriesCollection createDataset(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		// abscisses
		GregorianCalendar calendar = new GregorianCalendar(); 
		int heureencours = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure3 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure2 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure1 = calendar.get(Calendar.HOUR_OF_DAY);

		// étiquette de la série du taux d'occupation de la station
		final XYSeries series = new XYSeries("Taux d'occupation");

		// génération des données de la série du nombre de vélos dans la Station
		float taux = DAOLieu.calculerTx(station);
		series.add(heureencours, taux);
		series.add(heure1, (taux 
				+ ((DAOEmprunt.NombreVelosSortisHeures(station, 1)*100)/station.getCapacite())
				- ((DAOEmprunt.NombreVelosRendusHeures(station, 1)*100)/station.getCapacite())
		)
		);
		series.add(heure2, (taux 
				+ ((DAOEmprunt.NombreVelosSortisHeures(station, 2)*100)/station.getCapacite())
				- ((DAOEmprunt.NombreVelosRendusHeures(station, 2)*100)/station.getCapacite())
		)
		);
		series.add(heure3, (taux 
				+ ((DAOEmprunt.NombreVelosSortisHeures(station, 2)*100)/station.getCapacite())
				- ((DAOEmprunt.NombreVelosRendusHeures(station, 2)*100)/station.getCapacite())
		)
		);

		// étiquette de la série du taux maximum
		final XYSeries series2 = new XYSeries("Taux d'occupation minimum");
		
		// données de la série du taux maximum
		series2.add(heureencours, Station.TAUX_OCCUPATION_MIN * 100);
		series2.add(heure3, Station.TAUX_OCCUPATION_MIN * 100);
		series2.add(heure2, Station.TAUX_OCCUPATION_MIN * 100);
		series2.add(heure1, Station.TAUX_OCCUPATION_MIN * 100);
		
		// étiquette de la série du taux minimum
		final XYSeries series3 = new XYSeries("Taux d'occupation maximum");
		
		// données de la série du taux minimum
		series3.add(heureencours, Station.TAUX_OCCUPATION_MAX * 100);
		series3.add(heure3, Station.TAUX_OCCUPATION_MAX * 100);
		series3.add(heure2, Station.TAUX_OCCUPATION_MAX * 100);
		series3.add(heure1, Station.TAUX_OCCUPATION_MAX * 100);
		
		// ajout des 3 séries à la collection renvoyée
		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(series2);
		collection.addSeries(series3);
		return collection;

	}


}
