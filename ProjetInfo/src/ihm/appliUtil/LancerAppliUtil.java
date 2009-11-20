package ihm.appliUtil;

import metier.Compte;
import metier.Utilisateur;


public class LancerAppliUtil {

	// UTEST sera un utilisateur test qui servira à initialiser les attributs utilisateurs des différentes fenêtres
	public static Utilisateur UTEST = new Utilisateur(new Compte(Compte.TYPE_UTILISATEUR),"Test","Utilisateur","1 rue des Lilas");
	/*Velo veloTest = new Velo("vélo1",false);
	UTEST.setVelo(veloTest);*/

	public static void main(String[] args) {

		
		
		new FenetreAuthentificationUtil(false);
		//MenuUtilisateur m1 = new MenuUtilisateur(UTEST);
		//new FenetreEmpruntCourt(UTEST);
		//new FenetreEmprunterVelo(UTEST);

	}
}