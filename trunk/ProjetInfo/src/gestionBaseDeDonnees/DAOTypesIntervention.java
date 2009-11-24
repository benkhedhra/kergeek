package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOTypesIntervention {

	public static Map<Integer,String> getAllTypesIntervention() throws SQLException, ClassNotFoundException{
		Map<Integer,String> typesIntervention = new HashMap<Integer,String>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		try {
			ResultSet res = s.executeQuery("Select* from TypeIntervention");
			boolean vide = true;
			while (res.next()) {
				vide = false;
				typesIntervention.put((res.getInt("idTypeIntervention")),(res.getString("description")));
			}
			if(vide){
				throw new SQLException("pas de type d'intervention reference");
			}
		}
		catch(SQLException e1){
			typesIntervention = null;
			System.out.println(e1.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return typesIntervention;
	}
	
}
