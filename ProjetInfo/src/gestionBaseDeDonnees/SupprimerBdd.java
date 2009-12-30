package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

public class SupprimerBdd {
	
	/**
	 * Supprime les tables de la base de données préalablement créées.
	 * @param args
	 * @throws ConnexionFermeeException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 * @see InitialisationBaseDeDonnees#suppressionTables()
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		InitialisationBaseDeDonnees.suppressionTables();
	}

}
