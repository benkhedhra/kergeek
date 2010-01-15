package gestionBaseDeDonnees;

/**
 * @author sbalmand
 * @version 1.0
 */

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;
public class ConnexionOracleViaJdbc {

	//Attributs
	
	/**
	 * @see Connection
	 */
	static private Connection c;
	
	/**
	 * login de connection à Oracle
	 */
	static private String idUtilisateurOracle;
	
	/**
	 *mot de passe de connection à Oracle
	 */
	static private String motDePasseOracle;
	
	/**
	 * url de connection à Oracle
	 */
	static private String urlOracle;

	//TODO
	private ConnexionOracleViaJdbc(){
	}




	/**
	 * connexion au serveur Oracle.
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException 
	 */
	private static void connecter() throws ClassNotFoundException, ConnexionFermeeException{
		// création d'une connexion â€¡ une base de données		

		try {
			// Create a OracleDataSource instance and set properties
			OracleDataSource ods = new OracleDataSource();
			ods.setUser(idUtilisateurOracle);
			ods.setPassword(motDePasseOracle);
			ods.setURL(urlOracle);

			// Connect to the database
			c = ods.getConnection();
			c.setAutoCommit(false);
		}
		catch (SQLException e){
			System.out.println("Echec de la tentative de connexion : " + e.getMessage());
		}
		catch(NullPointerException e){
			throw new ConnexionFermeeException(e.getMessage());
		}

	}

	/**
	 * fermeture de la connexion
	 * @throws SQLException 
	 * @throws ConnexionFermeeException 
	 */	
	public static void fermer() throws SQLException, ConnexionFermeeException{
		if(c != null){
			c.close();
		}
		else{
			throw new ConnexionFermeeException();
		}
	}

	/**
	 * ouverture de la connexion
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws ConnexionFermeeException 
	 */	
	public static void ouvrir() throws SQLException,ClassNotFoundException, ConnexionFermeeException{
		
		try{
			if (getC() != null) {
				if(c.isClosed()){
					connecter();
				}				
			}
			else{
				connecter();
			}
		}
		catch (NullPointerException e){
			connecter();
		}

		/*
		 //Si jamais on souhaite demander l'identifiant et le mot de passe oracle de l'utilisateur de l'application
		 try  {
			if (c.isClosed()){
				// ce n'est pas la 1ere connexion au cours du programme : la connexion precedente a ete fermee
				connecter();
			}
		}
		catch (NullPointerException e){
			// c'est  la 1ere connexion a la BD au cours du programme
			// on demande identifiant et mot de passe
			//obtenir l'id et mot de passe du compte courant... du boulot!!!

				System.out.println(" Donner votre identifiant puis votre mot de passe pour vous connecter a la base");
				Scanner sc= new Scanner(System.in);
				ConnexionOracleViaJdbc.setIdUtilisateur(sc.next());
				ConnexionOracleViaJdbc.setMotDePasse(sc.next());
				UtilitaireSQL.tester(idUtilisateur, motDePasse);
				
				connecter();
		}*/
	}

	/**
	 * création d'instructions (statements) - redéfinition de la méthode
	 * @return  st
	 * @throws java.lang.NullPointerException 
	 * @throws ConnexionFermeeException 
	 */	
	public static Statement createStatement() throws java.lang.NullPointerException, ConnexionFermeeException{
		Statement st = null;
		try{
			st = c.createStatement();
		}
		catch(SQLException e){
			System.out.println("Echec de la tentative de création d'instruction : " + e.getMessage());
		}
		catch(NullPointerException e){
			throw new ConnexionFermeeException(e.getMessage());
		}
		return st;
	}

	
	//Accesseurs et Modificateurs
	
	/**
	 * @return c
	 */
	public static Connection getC(){
		return c;
	}
	
	/**
	 * Initialisation du login de connection à Oracle
	 * @param idU
	 */
	public static void setIdUtilisateurOracle(String idU){
		idUtilisateurOracle = idU;
	}
	
	/**
	 * Initialisation du mot de passe de connection à Oracle
	 * @param mdp
	 */
	public static void setMotDePasseOracle(String mdp){
		motDePasseOracle = mdp;
	}
	
	/**
	 * Initialisation de l'url de connection à Oracle
	 * @param url
	 */
	public static void setUrlOracle(String url){
		urlOracle = url;
	}
	
	/**
	 * @return {@link ConnexionOracleViaJdbc#idUtilisateurOracle}
	 * login de connection à Oracle
	 */
	public static String getIdUtilisateurOracle() {
		return idUtilisateurOracle;
	}

	/**
	 * @return {@link ConnexionOracleViaJdbc#motDePasseOracle}
	 */
	public static String getMotDePasseOracle() {
		return motDePasseOracle;
	}

	/**
	 * @return {@link ConnexionOracleViaJdbc#urlOracle}
	 */
	public static String getUrlOracle() {
		return urlOracle;
	}
	
	/**
	 * On initialise les paramètre de connexion à Oracle à partir d'un fichier
	 * @throws FileNotFoundException
	 */
	public static void parametresConnexionOracle() throws FileNotFoundException{
		//ouverture du scanner
		Scanner scanner=new Scanner(new File(System.getProperty("user.dir")+"/src/ressources/parametresOracle.txt"));
		scanner.nextLine();
		scanner.nextLine();
		scanner.nextLine();
		scanner.nextLine();
		
		//Initialisation de l'url de connection
		setUrlOracle(scanner.nextLine());
		scanner.nextLine();
		scanner.nextLine();
		
		//Initialisation du login
		setIdUtilisateurOracle(scanner.nextLine());
		scanner.nextLine();
		scanner.nextLine();
		
		//initialisation du mot de passe
		setMotDePasseOracle(scanner.nextLine());
		
		//fermeture du scanner
		scanner.close();
	}

}
