package ihm.appliUtil;

import metier.Compte;
import metier.Utilisateur;


public class LancerAppliUtil {

	public static Utilisateur UTEST = new Utilisateur(new Compte(Compte.TYPE_UTILISATEUR,"utest@gmail.com"),"Test","Utilisateur","1 rue des Lilas");

	public static void main(String[] args) {

		new FenetreAuthentificationUtil(false);

	}
}