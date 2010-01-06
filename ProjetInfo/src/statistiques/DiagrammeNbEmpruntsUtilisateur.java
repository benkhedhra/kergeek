package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import metier.Emprunt;
import metier.Utilisateur;
import metier.UtilitaireDate;

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
 * La classe DiagrammeNbEmpruntsUtilisateur permet de cr�er le diagramme relatif aux nombres d'emprunts d'un utilisateur.
 * @see Utilisateur
 * @see Emprunt
 * @author KerGeek
 */
public class DiagrammeNbEmpruntsUtilisateur extends ApplicationFrame{

	//Attributs
	private static final long serialVersionUID = 1L;

	/**
	 * @see DiagrammeFreqStations#getImage()
	 */
	private JFreeChart chart;

	//Constructeur
	
	/**
	 * Cr�ation d'un diagramme des nombres d'emprunt d'un {@link Utilisateur} � partir de ce dernier.
	 * @param u
	 * Utilisateur sur lequel on souhaite obtenir des informations
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws ClassNotFoundExceptionException
	 * @see DiagrammeNbEmpruntsUtilisateur#createDataset(Utilisateur)
	 * @see DiagrammeNbEmpruntsUtilisateur#createChart(CategoryDataset, Utilisateur)
	 */
	public DiagrammeNbEmpruntsUtilisateur(Utilisateur u) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

		super("Nombre d'emprunts de l'utilisateur demand� pour les six derniers mois");
		CategoryDataset dataset = createDataset(u);
		this.chart = createChart(dataset,u);
		ChartPanel chartPanel = new ChartPanel(this.chart, false);
		chartPanel.setPreferredSize(new Dimension(800, 800));
		this.setContentPane(chartPanel);

	}

	//M�thodes
	
	/**
	 * @return l'{@link DiagrammeNbEmpruntsUtilisateur#chart} du diagramme sous forme d'image.
	 * @see JFreeChart#createBufferedImage(int, int)
	 */
	public Image getImage() {
		return this.chart.createBufferedImage(800,800);
	}

	/**
	 * Cr�ation des donn�es utiles pour le diagramme.
	 * @param u
	 * l'{@link Utilisateur} sur lequel on souhaite obtenir des informations
	 * @return dataset
	 * les donn�es
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 * @see DAOEmprunt#getNombreEmpruntParUtilisateurParMois(Utilisateur, int)
	 */
	private static CategoryDataset createDataset(Utilisateur u) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

		// �tiquettes des ordonn�es
		String emprunts = "Nombre d'emprunts";

		// �tiquettes des abscisses :
		
		// d�claration d'un calendrier
		GregorianCalendar calendar = new GregorianCalendar();
		// initialise le calendrier � la date courante;
		calendar.setTime(UtilitaireDate.dateCourante());
		
		String nomMoisEnCours = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);
		
		calendar.add(Calendar.MONTH, -1);
		String nomMoisAnt1 = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);
			
		calendar.add(Calendar.MONTH, -1);
		String nomMoisAnt2 = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);
		
		calendar.add(Calendar.MONTH, -1);
		String nomMoisAnt3 = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);
		
		calendar.add(Calendar.MONTH, -1);
		String nomMoisAnt4 = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);
		
		calendar.add(Calendar.MONTH, -1);
		String nomMoisAnt5 = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);



		//// cr�ation des donn�es

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<Integer> liste = DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 5);
		dataset.addValue(liste.get(5), emprunts, nomMoisAnt5);
		dataset.addValue(liste.get(4), emprunts, nomMoisAnt4);
		dataset.addValue(liste.get(3), emprunts, nomMoisAnt3);
		dataset.addValue(liste.get(2), emprunts, nomMoisAnt2);
		dataset.addValue(liste.get(1), emprunts, nomMoisAnt1);
		dataset.addValue(liste.get(0), emprunts, nomMoisEnCours);

		return dataset;

	}

	/**
	 * Cr�ation du diagramme � partir des donn�es et de l'{@link Utilisateur} sur lequel on souhaite obtenir des informations.
	 * @param dataset
	 * les donn�es � repr�senter
	 * @param u
	 * l'{@link Utilisateur} sur lequel on souhaite obtenir des informations
	 * @return chart
	 * le diagramme
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundExceptionException
	 */
	private static JFreeChart createChart(CategoryDataset dataset,Utilisateur u) {

		// cr�er le graphique
		JFreeChart chart = ChartFactory.createBarChart(
				("Nombre d'emprunts de l'utilisateur "+ u.getPrenom()+
						" " +u.getNom() + " pour les six derniers mois"), // titre du graphique
						"6 derniers mois",        // nom de l'axe des abscisses
						"Nombre d' emprunts",     // nom de l'axe des ordonn�es
						dataset,                  // donn�es
						PlotOrientation.VERTICAL, // orientation
						true,                     // inclure la l�gende
						true,                     // tooltips
						false                     // URLs
		);


		// couleur de l'arri�re plan du graphique
		chart.setBackgroundPaint(Color.white);

		// customisation de l'environnement du graphique
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// r�glage de l'axe des abscisses pour qu'il ne pr�sente que des entiers
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// d�sactivation des bar outlines
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// d�finitions de la couleur de la s�rie
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 0, 64)
		);
		renderer.setSeriesPaint(0, gp0);

		// orientation de la l�gende
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
		);
		
		// R�glage de la taille des battons � travers l'espace entre 2 batons
		domainAxis.setCategoryMargin(0.6);

		return chart;

	}


}


