package ihm.appliUtil;

import metier.Compte;
import metier.Utilisateur;


public class LancerAppliUtil {

	// UTEST sera un utilisateur test qui servira � initialiser les attributs utilisateurs des diff�rentes fen�tres
	public static Utilisateur UTEST = new Utilisateur(new Compte(Compte.TYPE_UTILISATEUR),"Test","Utilisateur","1 rue des Lilas");
	/*Velo veloTest = new Velo("v�lo1",false);
	UTEST.setVelo(veloTest);*/

	public static void main(String[] args) {

		
		
		new FenetreAuthentificationUtil(false);
		//MenuUtilisateur m1 = new MenuUtilisateur(UTEST);
		//new FenetreEmpruntCourt(UTEST);
		//new FenetreEmprunterVelo(UTEST);

	}
}