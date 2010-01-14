package ihm.appliAdminTech;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.UtilitaireSQL;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.ExceptionAuthentification;

import ihm.MsgBox;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * classe ne comportant qu'un main permettant de lancer l'application de l'administrateur et du technicien
 * @author KerGeek
 *
 */
public class LancerAppliAdminTech {

	public static void main (String [] args) throws SQLException, ClassNotFoundException{
		try {
			ConnexionOracleViaJdbc.parametresConnexionOracle();
			UtilitaireSQL.testerConnexionOracle();
			FenetreAuthentification f = new FenetreAuthentification(false);
			f.repaint();
		} 
		catch (ExceptionAuthentification e) {
			MsgBox.affMsg("<html> <center>Les paramètres de connexion à Oracle ne sont pas valides " +
					"<br> Merci de modifier le fichier " +System.getProperty("user.dir")+"/src/ressources/parametresOracle </center></html>");
		}
		catch (FileNotFoundException e) {
			MsgBox.affMsg("Le fichier 'parametresOracle' est introuvable à l'adresse " + System.getProperty("user.dir")+"/src/ressources");
		}

	}

}