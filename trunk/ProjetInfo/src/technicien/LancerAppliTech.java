package technicien;

import java.sql.SQLException;

import metier.Compte;
import metier.Technicien;

public class LancerAppliTech {

	public static Technicien TTEST = new Technicien(new Compte("000","",Compte.TYPE_TECHNICIEN));

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		FenetreEnregistrerVeloTech f = new FenetreEnregistrerVeloTech(TTEST);

	}
}
