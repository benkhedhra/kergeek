package ihm.appliAdminTech.technicien;

import java.sql.SQLException;

import metier.Compte;
import metier.Technicien;

public class LancerAppliTech {

	public static Technicien TTEST = new Technicien(new Compte(Compte.TYPE_TECHNICIEN));

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		new FenetreRetirerVeloTech(TTEST);
		//new FenetreEnregistrerVeloTech(TTEST);
		//new MenuPrincipalTech(TTEST);
	}
}