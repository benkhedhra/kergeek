package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.ExceptionAuthentification;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class CreerBdd {

	/**
	 * Cr�e les tables necessaires au fonctionnement de l'application dans la base de donn�es et y ins�re les donn�es de tests. 
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
