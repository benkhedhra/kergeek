package ihm.appliAdminTech;

import java.sql.SQLException;

import metier.Compte;
import metier.Technicien;

public class LancerAppliAdminTech {

	public static void main (String [] args) throws SQLException, ClassNotFoundException{
		
		Technicien TTEST = new Technicien(new Compte(Compte.TYPE_TECHNICIEN,"techtest@jesuisuntest.fr"));
		new FenetreAuthentification(false);		
	}
}