package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Intervention;
import metier.UtilitaireDate;

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
				s.executeUpdate("COMMIT");
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

	public static List<Integer> getNombresVelosParTypeIntervention(int depuisMois) throws SQLException, ClassNotFoundException{

		List <Integer> list = new ArrayList<Integer>();
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			
			java.sql.Date dateSql = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(), depuisMois);
			
			/*TODO
			 * System.out.println(dateSql.toString());
			 */
			
			ResultSet res = null;
			for (Integer type : DAOTypesIntervention.getAllTypesIntervention().keySet()){
				res = s.executeQuery("Select count(*) as nombreVelosTypeIntervention from Intervention WHERE idTypeIntervention = '" + type + "' and dateIntervention >= TO_DATE('" + dateSql + "','YYYY-MM-DD-HH24:MI')");
				if (res.next()){
					list.add(res.getInt("nombreVelosTypeIntervention"));
				}
				else{
					list.add(0);
				}
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return list;
	}
}
