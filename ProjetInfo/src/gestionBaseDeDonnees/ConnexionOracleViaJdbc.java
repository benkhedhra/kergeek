package gestionBaseDeDonnees;

/**
 * @author sbalmand
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;
public class ConnexionOracleViaJdbc {
	
	static private Connection c;
	static private String idUtilisateur = identifiantBDD.UTILISATEUR;
	static private String motPasse = identifiantBDD.MOTDEPASSE;
	
	// avec un constructeur "private"
	// comme la Connection est static private, on ne pourra avoir qu'une seule connexion à la fois
	// le constructeur ne pourra être appelé en dehors de la classe
	// ici on ne fait pas appel au constructeur dans la classe, elle n'est donc pas instanciable
	private ConnexionOracleViaJdbc(){
		ConnexionOracleViaJdbc.setIdUtilisateur(identifiantBDD.UTILISATEUR);
		ConnexionOracleViaJdbc.setIdUtilisateur(identifiantBDD.MOTDEPASSE);
	}
	
	
	/**
	 * connexion au serveur Oracle.
	 * @throws ClassNotFoundException
	 */
	private static void connecter() throws ClassNotFoundException{
		// création d'une connexion à une base de données		
	    
		String url = "jdbc:oracle:thin:@oraens10g:1521:ORAENS";
		
		
		
	    try {
	    	// Create a OracleDataSource instance and set properties
			OracleDataSource ods = new OracleDataSource();
		    ods.setUser(idUtilisateur);
		    ods.setPassword(motPasse);
		    ods.setURL(url);
		    

		    // Connect to the database
		    c = ods.getConnection();
		    c.setAutoCommit(false);
	    }
		catch (SQLException e){
				System.out.println("Echec de la tentative de connexion : " + e.getMessage());
		}
		
	}
	
	/**
	 * fermeture de la connexion
	 */	
	public static void fermer() throws SQLException{
		c.close();
	}
	
	/**
	 * ouverture de la connexion
	 * @throws SQLException, ClassNotFoundException
	 */	
	public static void ouvrir() throws SQLException,ClassNotFoundException{
		try  {
			if (c.isClosed()){
				// ce n'est pas la 1ere connexion au cours du programme : la connexion precedente a ete fermee
				connecter();
		   }
		}
	   catch (NullPointerException e){
			// c'est  la 1ere connexion a la BD au cours du programme
			// demander identifiant et mot de passe ici...
			connecter();
		}
	}
	
	/**
	 * création d'instructions (statements) - redéfinition de la méthode
	 */	
	public static Statement createStatement(){
		Statement st = null;
		try{
			st = c.createStatement();
		}
		catch(SQLException e){
			System.out.println("Echec de la tentative de création d'instruction : " + e.getMessage());
		}
		return st;
	}

	/**
	 * @return c
	 */
	public static Connection getC(){
		return c;
	}
	
	public static void setIdUtilisateur(String idU){
		idUtilisateur = idU;
	}
	
	public static void setMotDePasse(String mdp){
		motPasse = mdp;
	}

}
