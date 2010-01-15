package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.ExceptionAuthentification;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class CreerBdd {

	/**
	 * Crée les tables necessaires au fonctionnement de l'application dans la base de données et y insère les données de tests. 
	 * @param args
	 * @throws ConnexionFermeeException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 * @throws FileNotFoundException 
	 * @throws ExceptionAuthentification 
	 * @see InitialisationBaseDeDonnees#creationTables()
	 * @see InitialisationBaseDeDonnees#insertionDonneesTests()
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException, FileNotFoundException, ExceptionAuthentification {
		ConnexionOracleViaJdbc.parametresConnexionOracle();
		InitialisationBaseDeDonnees.creationTables();
		InitialisationBaseDeDonnees.insertionDonneesTests();

	}

}
