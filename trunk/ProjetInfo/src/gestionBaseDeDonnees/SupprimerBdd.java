package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

public class SupprimerBdd {
	
	/**
	 * Supprime les tables de la base de donn�es pr�alablement cr��es.
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
