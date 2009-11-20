package ihm.appliAdminTech;

import ihm.appliUtil.FenetreAuthentificationUtil;
import ihm.appliUtil.LancerAppliUtil;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import statistiques.DiagrammeFreqStations;
import statistiques.DiagrammeNbEmpruntsUtilisateur;
import statistiques.DiagrammeNbIntervention;

public class FenetreAffichageResultats extends JFrame {

	public FenetreAffichageResultats(/*JFrame fenetrePrec,Compte c*/){
		
		/*this.setSize(new Dimension(700,500));
		DiagrammeFreqStations diag = new DiagrammeFreqStations("30 derniers jours");
		JLabel lblChart = new JLabel();
		lblChart.setIcon(new ImageIcon(diag.getImage()));
		this.getContentPane().add(lblChart);
		this.setVisible(true);
	}
	public static void main (String [] args){
		new FenetreAffichageResultats();
	}*/
		
		/*this.setSize(new Dimension(700,500));
		DiagrammeNbIntervention diag = new DiagrammeNbIntervention("");
		JLabel lblChart = new JLabel();
		lblChart.setIcon(new ImageIcon(diag.getImage()));
		this.getContentPane().add(lblChart);
		this.setVisible(true);
	}
	public static void main (String [] args){
		new FenetreAffichageResultats();
	}*/
		
		this.setSize(new Dimension(700,500));
		DiagrammeNbEmpruntsUtilisateur diag = new DiagrammeNbEmpruntsUtilisateur(LancerAppliUtil.UTEST/*fenetrePrec.getUtilisateur*/);
		JLabel lblChart = new JLabel();
		lblChart.setIcon(new ImageIcon(diag.getImage()));
		this.getContentPane().add(lblChart);
		this.setVisible(true);
	}
	public static void main (String [] args){
		new FenetreAffichageResultats();
	}
		

}