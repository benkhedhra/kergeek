package ihm.appliUtil;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.UtilitaireSQL;
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
	
	/**
	 * Lancement de l'application de l'utilisateur
	 * � condition que l'on puisse acc�der � Oracle.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConnexionOracleViaJdbc.parametresConnexionOracle();
			UtilitaireSQL.testerConnexionOracle();
			FenetreAuthentificationUtil f = new FenetreAuthentificationUtil(false);
			f.repaint();
		} 
		catch (ExceptionAuthentification e) {
			MsgBox.affMsg("<html> <center>Les param�tres de connection � Oracle ne sont pas valides. " +
							"<br> Merci de modifier le fichier<br>" +System.getProperty("user.dir")+"\\src\\ressources\\parametresOracle<br>" +
							"pour que l'application puisse fonctionner.</center></html>");
			System.exit(0);
		}
		catch (FileNotFoundException e) {
			MsgBox.affMsg("Le fichier 'parametresOracle' est introuvable � l'adresse<br>" +
							System.getProperty("user.dir")+"\\src\\ressources");
			System.exit(0);
		} 
	}
}