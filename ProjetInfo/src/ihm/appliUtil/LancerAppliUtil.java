package ihm.appliUtil;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.UtilitaireSQL;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.ExceptionAuthentification;
import ihm.MsgBox;

import java.io.FileNotFoundException;


/**
 * classe ne comportant qu'un main permettant de lancer l'application de l'utilisateur
 *<br> cette application simule une borne pr�sente � une station
 * @author KerGeek
 *
 */
public class LancerAppliUtil {
	public static void main(String[] args) {
		try {
			ConnexionOracleViaJdbc.parametresConnexionOracle();
			UtilitaireSQL.testerConnexionOracle();
			FenetreAuthentificationUtil f = new FenetreAuthentificationUtil(false);
			f.repaint();
		} 
		catch (ExceptionAuthentification e) {
			MsgBox.affMsg("<html> <center>Les param�tres de connexion � Oracle ne sont pas valides " +
					"<br> Merci de modifier le fichier " +System.getProperty("user.dir")+"\\src/ressources\\parametresOracle </center></html>");
		}
		catch (FileNotFoundException e) {
			MsgBox.affMsg("Le fichier 'parametresOracle est introuvable � l'adresse " + System.getProperty("user.dir")+"/src/ressources");
		} 
	}
}