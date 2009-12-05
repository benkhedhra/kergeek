package gestionBaseDeDonnees;

/**
 * @author KerGeek
 * @version 1.0
 */

import exceptionsTechniques.ExceptionAuthentification;

import java.sql.SQLException;

public class UtilitaireSQL {
	
	/**
	 * Test d'authentification
	 * @param idU : identifiant de l'utilisateur
	 * @param mdp : mot de passe de l'utilisateur
	 * @throws ExceptionAuthentification
	 */
	public static void tester(String idU, String mdp) throws ExceptionAuthentification{ // test de la connexion
		try{
			ConnexionOracleViaJdbc.setIdUtilisateur(idU);
			ConnexionOracleViaJdbc.setMotDePasse(mdp);
			ConnexionOracleViaJdbc.ouvrir();
			ConnexionOracleViaJdbc.fermer();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(NullPointerException e){
			throw new ExceptionAuthentification(e.getMessage());
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
}
