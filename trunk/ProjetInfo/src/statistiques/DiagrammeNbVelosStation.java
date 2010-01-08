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

/**
 * La classe DiagrammeNbVelosStation permet de cr�er le diagramme relatif aux nombres de v�los dans une station.
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
	 * Cr�ation d'un diagramme des nombres de v�lo par station � partir de cette derni�re.
	 * @param station
	 * la Station sur laquel on souhaite obtenir des informations
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws ClassNotFoundExceptionException
	 * @see DiagrammeNbVelosStation#createChart(Station)
	 */
	public DiagrammeNbVelosStation(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {
		super("");
		this.chart = createChart(station);
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(600, 610));
		this.setContentPane(chartPanel);

	}

	
	//M�thodes
	
	/**
	 * @return l'{@link DiagrammeNbVelosStation#chart} du diagramme sous forme d'image.
	 * @see JFreeChart#createBufferedImage(int, int)
	 */
	public Image getImage() {
		return this.chart.createBufferedImage(600, 610);
	}

	// Cr�ation des donn�es
	
	/**
	 * Cr�ation des donn�es utiles pour le diagramme.
	 * @param station
	 * la Station sur laquel on souhaite obtenir des informations
	 * @return collection
	 * les donn�es
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 * @see DAOEmprunt#NombreVelosSortisHeures(Station, int)
	 * @see DAOEmprunt#NombreVelosRendusHeures(Station, int)
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

		// �tiquette de la s�rie du nombre de v�los dans la Station
		final XYSeries series = new XYSeries("Nombre de v�los");

		// g�n�ration des donn�es de la s�rie du nombre de v�los dans la Station
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

		// �tiquette de la s�rie correspondant � la Capacit� de la Station
		final XYSeries series2 = new XYSeries("Capacit� de la station");
		
		// donn�es de la s�rie correspondant � la Capacit� de la Station
		series2.add(heureencours, station.getCapacite());
		series2.add(heure3, station.getCapacite());
		series2.add(heure2, station.getCapacite());
		series2.add(heure1, station.getCapacite());


		// ajout des 2 s�ries � la collection renvoy�e
		final XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(series2);
		return collection;

	}
	
	/**
	 * Cr�ation du diagramme.
	 * @param station
	 * la {@link Station} sur laquel on souhaite obtenir des informations
	 * @return chart
	 * le diagramme
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 * @see DiagrammeNbVelosStation#createDataset(Station)
	 */
	private JFreeChart createChart(Station station) throws ConnexionFermeeException, SQLException, ClassNotFoundException {



		//initialisation de l'axe des ordonn�es
		final NumberAxis axeVelo = new NumberAxis("nombre de v�los"); // titre
		axeVelo.setLabelFont(new Font("Arial Narrow", Font.BOLD,16)); // police
		axeVelo.setTickUnit(new NumberTickUnit(1)); // graduation

		// cr�ation du sous-dessin
		final XYSeriesCollection data1 = createDataset(station);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final XYPlot subplot1 = new XYPlot(data1, null, axeVelo, renderer1);
		subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		// initialisation de l'axe des abscisses
		NumberAxis axeHeures = new NumberAxis("Heure"); // titre
		axeHeures.setAutoRangeIncludesZero(false); // d�sactivation de l'obligation d'inclure le z�ro sur l'axe
		axeHeures.setTickUnit(new NumberTickUnit(1)); // graduation
		axeHeures.setLabelFont(new Font("Arial Narrow", Font.BOLD,16)); // police

		// cr�ation du dessin
		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(axeHeures);
		plot.setGap(10.0);

		// ajout du sous-dessin
		plot.add(subplot1, 1);

		//orientation du dessin
		plot.setOrientation(PlotOrientation.VERTICAL);

		// cr�ation du graphique
		JFreeChart chart = new JFreeChart(
				"Nombre de v�los de la station "+station.getAdresse()+" le "
				+UtilitaireDate.Presenter(UtilitaireDate.dateCourante()), // titre
				JFreeChart.DEFAULT_TITLE_FONT, //police
				plot, 
				true);

		// couleurs des s�ries
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0)
		);
		GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.black, 
				0.0f, 0.0f, new Color(64, 0, 0)
		);
		renderer1.setSeriesPaint(0, gp0);
		renderer1.setSeriesPaint(1, gp1);

		// epaisseur de la s�ries concernant la capacit�
		renderer1.setSeriesStroke(1, new BasicStroke(1.8f));

		// renvoie un JFreeChart comprenant le graphique r�sultant de la superposition
		return chart;
	}


}
