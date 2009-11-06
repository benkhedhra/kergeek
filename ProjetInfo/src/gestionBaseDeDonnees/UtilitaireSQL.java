package gestionBaseDeDonnees;

/**
 * @author KerGeek
 * @version 1.0
 */

import exception.ExceptionAuthentification;

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
	
	public static boolean testerIdent(String ident){
		/**
		@author Ma�lle
		* fonction provisoire et non cod�e cr��e le 04/11/09	
		**/
		//pour v�rifier si un UTILISATEUR est bien dans la base
		//rend true si il l'a trouv�
		return true;
	}
	
	public static int testerAuthent(String ident,String mdp){
		/**
		@author Ma�lle
		* fonction provisoire et non cod�e cr��e le 04/11/09	
		**/
		//pour v�rifier si un ADMINISTRATEUR ou un TECHNICIEN a bien entr� une bonne combinaison login-mot de passe
		//rend la nature de l'acteur : 1 si adm, 2 si techn, ou -1 si mauvaise combinaison
		return -1;
	}
	

}
