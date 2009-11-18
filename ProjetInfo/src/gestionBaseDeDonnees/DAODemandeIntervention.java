package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.DemandeIntervention;

public class DAODemandeIntervention {
	public static boolean createDemandeIntervention(DemandeIntervention ddeIntervention) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqDemandeIntervention.NEXTVAL from dual");
			if (res.next()){
				String id = res.getString("dummy");
				
				/*TODO
				 * a verifier...
				 */
				
				/*TODO
				 * ddeIntervention.setId(id); a-t-on besoin d'un id en java?
				 */
				
				s.executeUpdate("INSERT into DemandeIntervention values ('" 
						+ id + "', '"+ UtilitaireDate.dateCourante() + "', '"+ ddeIntervention.getVelo().getId() + "', '" 
						+ ddeIntervention.getUtilisateur().getCompte().getId() + "')");
				ConnexionOracleViaJdbc.fermer();
				effectue=true;
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
