package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOVelo;
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
import metier.Utilisateur;
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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * La classe DiagrammeNbVelosStation permet de créer le diagramme relatif aux nombres de vélos dans une station.
 * @see Station
 * @author KerGeek
 */
public class DiagrammeNbVelosStation extends ApplicationFrame {

	//Attributs
	private static final long serialVersionUID = 1L;

	/**
	 * @see DiagrammeFreqStations#getImage()
	 */
	private JFreeChart chart;

	//Constructeur
	
	/**
	 * Création d'un diagramme des nombres de vélo par station à partir d'un {@link DiagrammeNbVelosStation#station}.
	 * @param station
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 * @see DiagrammeNbVelosStation#createChart(Station)
	 */
	public DiagrammeNbVelosStation(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {
		super("");
		this.chart = createChart(station);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(600, 600));
		this.setContentPane(chartPanel);

	}

	
	//Méthodes
	
	/**
	 * @return l'{@link DiagrammeNbVelosStation#chart} du diagramme et en créer un image.
	 */
	public Image getImage() {
		return this.chart.createBufferedImage(600, 600);
	}

	// Création des données
	
	/**
	 * Création des données utiles dans le diagramme à partir d'un {@link DiagrammeNbVelosStation#station}.
	 * @param station
	 * @return collection
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 */
	private XYSeriesCollection createDataset(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {

		// abscisses
		GregorianCalendar calendar = new GregorianCalendar(); 
		int heureencours = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure1 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure2 = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		int heure3 = calendar.get(Calendar.HOUR_OF_DAY);

		// étiquette de la série du nombre de vélos dans la Station
		final XYSeries series = new XYSeries("Nombre de vélos");

		// génération des données de la série du nombre de vélos dans la Station
		int nbVelos = DAOVelo.getVelosByLieu(station).size();
		series.add(heureencours, nbVelos);
		series.add(heure1, nbVelos
				+ (DAOEmprunt.NombreVelosSortisHeures(station, 1))
				- (DAOEmprunt.NombreVelosRendusHeures(station, 1))
		);
		series.add(heure2,  nbVelos
				+ (DAOEmprunt.NombreVelosSortisHeures(station, 2))
				- (DAOEmprunt.NombreVelosRendusHeures(station, 2))
		);
		series.add(heure3,  nbVelos
				+ (DAOEmprunt.NombreVelosSortisHeures(station, 3))
				- (DAOEmprunt.NombreVelosRendusHeures(station, 3))
		);

		// étiquette de la série correspondant à la Capacité de la Station
		final XYSeries series2 = new XYSeries("Capacité de la station");
		
		// données de la série correspondant à la Capacité de la Station
		series2.add(heureencours, station.getCapacite());
		series2.add(heure3, station.getCapacite());
		series2.add(heure2, station.getCapacite());
		series2.add(heure1, station.getCapacite());


		// ajout des 2 séries à la collection renvoyée
		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(series2);
		return collection;

	}
	
	/**
	 * Création du chart du diagramme à partir d'un {@link DiagrammeNbVelosStation#station}.
	 * @param station
	 * @return chart
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 * @see DiagrammeNbVelosStation#createDataset(Station)
	 */
	private JFreeChart createChart(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {



		//initialisation de l'axe des ordonnées
		final NumberAxis axeVelo = new NumberAxis("nombre de vélos"); // titre
		axeVelo.setLabelFont(new Font("Arial Narrow", Font.BOLD,16)); // police

		// création du sous-dessin
		final XYSeriesCollection data1 = createDataset(station);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final XYPlot subplot1 = new XYPlot(data1, null, axeVelo, renderer1);
		subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		// initialisation de l'axe des abscisses
		NumberAxis axeHeures = new NumberAxis("Heure"); // titre
		axeHeures.setAutoRangeIncludesZero(false); // désactivation de l'obligation d'inclure le zéro sur l'axe
		axeHeures.setTickUnit(new NumberTickUnit(1)); // graduation
		axeHeures.setLabelFont(new Font("Arial Narrow", Font.BOLD,16)); // police

		// création du dessin
		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(axeHeures);
		plot.setGap(10.0);

		// ajout du sous-dessin
		plot.add(subplot1, 1);

		//orientation du dessin
		plot.setOrientation(PlotOrientation.VERTICAL);

		// création du graphique
		JFreeChart chart = new JFreeChart(
				"Nombre de vélos de la station "+station.getAdresse()+" le "
				+UtilitaireDate.Presenter(UtilitaireDate.dateCourante()), // titre
				JFreeChart.DEFAULT_TITLE_FONT, //police
				plot, 
				true);

		// couleurs des séries
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0)
		);
		GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, new Color(64, 0, 0)
		);
		renderer1.setSeriesPaint(0, gp0);
		renderer1.setSeriesPaint(1, gp1);

		// epaisseur de la séries concernant la capacité
		renderer1.setSeriesStroke(1, new BasicStroke(1.8f));

		// renvoie un JFreeChart comprenant le graphique résultant de la superposition
		return chart;
	}


}
