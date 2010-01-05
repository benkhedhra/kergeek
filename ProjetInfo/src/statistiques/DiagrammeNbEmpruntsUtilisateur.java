package statistiques;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import metier.Utilisateur;

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


public class DiagrammeNbEmpruntsUtilisateur extends ApplicationFrame{

	private static final long serialVersionUID = 1L;

	private JFreeChart chart;

	public DiagrammeNbEmpruntsUtilisateur(Utilisateur u) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

		super("Nombre d'emprunts de l'utilisateur demandé pour les six derniers mois");
		CategoryDataset dataset = createDataset(u);
		this.chart = createChart(dataset,u);
		ChartPanel chartPanel = new ChartPanel(this.chart, false);
		chartPanel.setPreferredSize(new Dimension(800, 800));
		this.setContentPane(chartPanel);

	}

	public Image getImage() {
		return this.chart.createBufferedImage(800,800);
	}

	private static CategoryDataset createDataset(Utilisateur u) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

		// étiquettes des ordonnées
		String emprunts = "Nombre d'emprunts";


		// étiquettes des abscisses
		Calendar calendar = Calendar.getInstance(); 
		int moisencours = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		int moisAnt1 = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		int moisAnt2 = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		int moisAnt3 = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		int moisAnt4 = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		int moisAnt5 = calendar.get(Calendar.MONTH);


		ResourceBundle resourceBundle = ResourceBundle.getBundle("statistiques/utils",Locale.FRENCH);

		String nomMoisEnCours = resourceBundle.getString("mois."+moisencours);
		String nomMoisAnt1 = resourceBundle.getString("mois."+moisAnt1);
		String nomMoisAnt2 = resourceBundle.getString("mois."+moisAnt2);
		String nomMoisAnt3 = resourceBundle.getString("mois."+moisAnt3);
		String nomMoisAnt4 = resourceBundle.getString("mois."+moisAnt4);
		String nomMoisAnt5 = resourceBundle.getString("mois."+moisAnt5);




		//// création des données

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


	private static JFreeChart createChart(CategoryDataset dataset,Utilisateur u) {

		// créer le graphique
		JFreeChart chart = ChartFactory.createBarChart(
				("Nombre d'emprunts de l'utilisateur "+ u.getPrenom()+
						" " +u.getNom() + " pour les six derniers mois"), // titre du graphique
						"6 derniers mois",        // nom de l'axe des abscisses
						"Nombre d' emprunts",     // nom de l'axe des ordonnées
						dataset,                  // données
						PlotOrientation.VERTICAL, // orientation
						true,                     // inclure la légende
						true,                     // TODO tooltips?
						false                     // TODO URLs?
		);


		// couleur de l'arrière plan du graphique
		chart.setBackgroundPaint(Color.white);

		// customisation de l'environnement du graphique
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// réglage de l'axe des abscisses pour qu'il ne présente que des entiers
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// désactivation des bar outlines
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// définitions de la couleur de la série
		GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 0, 64)
		);
		renderer.setSeriesPaint(0, gp0);

		// orientation de la légende
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
		);
		
		// Réglage de la taille des battons à travers l'espace entre 2 batons
		domainAxis.setCategoryMargin(0.6);

		return chart;

	}


}


