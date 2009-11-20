package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Intervention;

public class DAOIntervention {


	public static boolean createIntervention(Intervention intervention) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				intervention.setId(id);

				s.executeUpdate("INSERT into Intervention values ('" 
						+ intervention.getId() +  "', '" 
						+ intervention.getVelo().getId() + "', '" 
						+ intervention.getDate() + "', '" 
						+ intervention.getTypeIntervention() + "')");
				effectue=true;
				ConnexionOracleViaJdbc.fermer();
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
