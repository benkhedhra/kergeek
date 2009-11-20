package ihm.appliAdminTech;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import statistiques.DiagrammeFreqStations;

public class FenetreAffichageResultats extends JFrame {

	public FenetreAffichageResultats(/*JFrame fenetrePrec,Compte c*/){
		this.setSize(new Dimension(700,500));
		DiagrammeFreqStations diag = new DiagrammeFreqStations("30 derniers jours");
		JLabel lblChart = new JLabel();
		lblChart.setIcon(new ImageIcon(diag.getImage()));
		this.getContentPane().add(lblChart);
		
		this.setVisible(true);
	}
	public static void main (String [] args){
		new FenetreAffichageResultats();
	}

}