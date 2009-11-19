package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.DemandeAssignation;

public class DAODemandeAssignation {

	public static boolean createDemandeAssignation(DemandeAssignation ddeAssignation) throws SQLException,ClassNotFoundException{
		boolean effectue = false;
		try{

			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqDemandeAssignation.NEXTVAL from dual");
			if (res.next()){
				String id = res.getString("dummy");
				/*TODO
				 * a verifier...
				 */
				
				
				/*TODO
				 * ddeAssignation.setId(id); a-t-on besoin d'un id en java?
				 */
				

				if (ddeAssignation.isAjout()){
					s.executeUpdate("INSERT into DemandeAssignation values ('"
							+ id + "', '"
							+ ddeAssignation.getDate() + "', '" 
							+ "'1','"
							+ ddeAssignation.getNombreVelos() + "',"
							+ ddeAssignation.getLieu().getId()
							+")");
				}

				else{
					s.executeUpdate("INSERT into DemandeAssignation values ('" 
							+ id + "', '"
							+ ddeAssignation.getDate() + "', '"
							+ "'0','"
							+ ddeAssignation.getNombreVelos() + "',"
							+ ddeAssignation.getLieu().getId()
							+")");
				}

				s.executeUpdate("COMMIT");
				effectue = true;
				System.out.println("Demande d'assignation ajoutee � la base de donnees");
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;


	}

}