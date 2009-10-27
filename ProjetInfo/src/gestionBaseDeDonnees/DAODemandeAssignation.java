package gestionBaseDeDonnees;

import java.sql.SQLException;
import java.sql.Statement;

import metier.DemandeAssignation;

public class DAODemandeAssignation {

	public static void entrerDemandeAssignation(DemandeAssignation ddeAssignation) throws SQLException,ClassNotFoundException{

		ConnexionOracleViaJdbc.getC();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		if (ddeAssignation.isAjout()){
			s.executeUpdate("INSERT into DemandeAssignation values ('" 
					+ ddeAssignation.getDate() + "', '" + ddeAssignation.getLieu() + "','ajout'");
		}
		
		else{
			s.executeUpdate("INSERT into DemandeAssignation values ('" 
					+ ddeAssignation.getDate() + "', '" + ddeAssignation.getLieu() + "','retrait'");
		}

		s.executeUpdate("COMMIT");
		System.out.println("Demande d'assignation ajoutee ˆ la base de donnees");
		/*TODO
		 * gerer les exception
		 */


	}

}
