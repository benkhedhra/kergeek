package gestionBaseDeDonnees;

/**
 * @author sbalmand
 * @version 1.0
 */

import ihm.MsgBox;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;
public class ConnexionOracleViaJdbc {
	//pour ecrire l'id et le mp pour acceder ˆ la base de donnees "en dur"
	public static final String UTILISATEUR ="id3033";
	public static final String MOTDEPASSE ="id3033";

	static private Connection c;
	static private  String idUtilisateur =UTILISATEUR; 
	static private  String motPasse = MOTDEPASSE;


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
			MsgBox.affMsg("Echec de la tentative de connexion : " + e.getMessage());
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
		/*try  {
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
			connecter();

		}*/
		connecter();
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
			MsgBox.affMsg("Echec de la tentative de création d'instruction : " + e.getMessage());
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
