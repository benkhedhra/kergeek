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

	static private Connection c;
	static private String idUtilisateurOracle; 
	static private String motDePasseOracle;
	static private String urlOracle;


	// avec un constructeur "private"
	// comme la Connection est static private, on ne pourra avoir qu'une seule connexion a la fois
	// le constructeur ne pourra etre appele en dehors de la classe
	// ici on ne fait pas appel au constructeur dans la classe, elle n'est donc pas instanciable
	private ConnexionOracleViaJdbc(){
		//ConnexionOracleViaJdbc.setIdUtilisateur(identifiantBDD.UTILISATEUR);
		//ConnexionOracleViaJdbc.setIdUtilisateur(identifiantBDD.MOTDEPASSE);
	}




	/**
	 * connexion au serveur Oracle.
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException 
	 */
	private static void connecter() throws ClassNotFoundException, ConnexionFermeeException{
		// création d'une connexion â€¡ une base de données		

		//TODO URL Oracle
		



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

	public static void setIdUtilisateurOracle(String idU){
		idUtilisateurOracle = idU;
	}

	public static void setMotDePasseOracle(String mdp){
		motDePasseOracle = mdp;
	}
	
	public static void setUrlOracle(String url){
		urlOracle = url;
	}
	
	public static String getIdUtilisateurOracle() {
		return idUtilisateurOracle;
	}


	public static String getMotDePasseOracle() {
		return motDePasseOracle;
	}


	public static String getUrlOracle() {
		return urlOracle;
	}
	
	
	public static void parametresConnexionOracle() throws FileNotFoundException{
		Scanner scanner=new Scanner(new File(System.getProperty("user.dir")+"/src/ressources/parametresOracle.txt"));
		scanner.nextLine();
		scanner.nextLine();
		scanner.nextLine();
		setUrlOracle(scanner.nextLine());
		scanner.nextLine();
		setIdUtilisateurOracle(scanner.nextLine());
		scanner.nextLine();
		setMotDePasseOracle(scanner.nextLine());
		scanner.close();
	}

}
