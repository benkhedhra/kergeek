package gestionBaseDeDonnees;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
public class SuppressionTables {

	static public void main (String argv[]) throws SQLException,ClassNotFoundException, ConnexionFermeeException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();


		try{
			s.executeUpdate("DROP TABLE Emprunt");
			s.executeUpdate("DROP SEQUENCE seqEmprunt");
			s.executeUpdate("DROP TABLE DemandeAssignation ");
			s.executeUpdate("DROP SEQUENCE seqDemandeAssignation");
			s.executeUpdate("DROP TABLE DemandeIntervention");
			s.executeUpdate("DROP SEQUENCE seqDemandeIntervention");
			s.executeUpdate("DROP TABLE Intervention");
			s.executeUpdate("DROP SEQUENCE seqIntervention");
			s.executeUpdate("DROP TABLE TypeIntervention ");	
			s.executeUpdate("DROP SEQUENCE seqTypeIntervention");
			s.executeUpdate("DROP TABLE Compte");
			s.executeUpdate("DROP SEQUENCE seqTechnicien");
			s.executeUpdate("DROP SEQUENCE seqUtilisateur");
			s.executeUpdate("DROP SEQUENCE seqAdministrateur");
			s.executeUpdate("DROP TABLE Velo");
			s.executeUpdate("DROP SEQUENCE seqVelo");
			s.executeUpdate("DROP TABLE Lieu");
			s.executeUpdate("DROP SEQUENCE seqLieu");
			

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
