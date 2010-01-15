package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.ExceptionAuthentification;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SupprimerBdd {
	
	/**
	 * Supprime les tables de la base de données préalablement créées.
	 * @param args
	 * @throws ConnexionFermeeException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 * @throws FileNotFoundException 
	 * @throws ExceptionAuthentification 
	 * @see InitialisationBaseDeDonnees#suppressionTables()
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException, FileNotFoundException, ExceptionAuthentification {
		ConnexionOracleViaJdbc.parametresConnexionOracle();
		InitialisationBaseDeDonnees.suppressionTables();
	}

}
