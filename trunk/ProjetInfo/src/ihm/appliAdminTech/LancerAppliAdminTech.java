package ihm.appliAdminTech;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.UtilitaireSQL;
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
	
	/**
	 * Lancement de l'application de l'administrateur et du technicien
	 * à condition que l'on puisse accéder à Oracle.
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main (String [] args) throws SQLException, ClassNotFoundException{
		try {
			ConnexionOracleViaJdbc.parametresConnexionOracle();
			UtilitaireSQL.testerConnexionOracle();
			FenetreAuthentification f = new FenetreAuthentification(false);
			f.repaint();
		} 
		catch (ExceptionAuthentification e) {
			MsgBox.affMsg("<html><center>Les paramètres de connection à Oracle ne sont pas valides. " +
							"<br> Merci de modifier le fichier<br>" +System.getProperty("user.dir")+"\\src\\ressources\\parametresOracle.txt<br>" +
							"pour que l'application puisse fonctionner.</center></html>");
			System.exit(0);
		}
		catch (FileNotFoundException e) {
			MsgBox.affMsg("<html><center>Le fichier 'parametresOracle.txt' est introuvable à l'adresse :<br>" +
					System.getProperty("user.dir")+"\\src\\ressources</center></html>");
			System.exit(0);
		}

	}

}