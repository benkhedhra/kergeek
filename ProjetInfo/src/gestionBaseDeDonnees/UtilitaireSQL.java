package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.ExceptionAuthentification;

import java.sql.SQLException;

public class UtilitaireSQL {
	
	/**
	 * Test d'authentification d'un utilisateur oracle
	 * @param idU : identifiant de l'utilisateur
	 * @param mdp : mot de passe de l'utilisateur
	 * @throws ExceptionAuthentification
	 * @throws ConnexionFermeeException
	 * @author sbalmand
	 */
	public static void testerConnexionOracle() throws ExceptionAuthentification { // test de la connexion
		try{
			ConnexionOracleViaJdbc.ouvrir();
			ConnexionOracleViaJdbc.fermer();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(ConnexionFermeeException e){
			throw new ExceptionAuthentification(e.getMessage());
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
}
