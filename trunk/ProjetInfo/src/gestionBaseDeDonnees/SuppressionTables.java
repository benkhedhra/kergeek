package gestionBaseDeDonnees;
import java.sql.SQLException;
import java.sql.Statement;
public class SuppressionTables {

	static public void main (String argv[]) throws SQLException,ClassNotFoundException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		/*TODO
		 * COMMENT GENERER LES IDENTIFIANTS EN SQL?
		 */
		try{
			s.executeUpdate("DROP TABLE Emprunt");
			s.executeUpdate("DROP TABLE DemandeAssignation ");
			s.executeUpdate("DROP TABLE Intervention");
			s.executeUpdate("DROP TABLE TypeIntervention ");	
			s.executeUpdate("DROP TABLE DemandeIntervention");
			s.executeUpdate("DROP TABLE Velo");
			s.executeUpdate("DROP TABLE Lieu");
			s.executeUpdate("DROP TABLE Compte");

			s.executeUpdate("COMMIT");
			System.out.println("Suppression effectuee.");
		}
		/*catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();
			System.out.println(e.getMessage());
		}*/
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
	}		
}
