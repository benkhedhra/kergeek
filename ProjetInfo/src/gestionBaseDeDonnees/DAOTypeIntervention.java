package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DAOTypeIntervention {

	public static Map<Integer,String> getAllTypeIntervention() throws SQLException, ClassNotFoundException{
		Map<Integer,String> typeIntervention = new HashMap<Integer,String>();
		
		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select* from TypeIntervention");
		try {
			if (res.next()) {
				typeIntervention.put((res.getInt("Type")),(res.getString("Type")));
			}
			else {
				throw new SQLException("pas de type d'intervention reference");
			}
		}
		catch(SQLException e1){
			typeIntervention = null;
			ConnexionOracleViaJdbc.fermer();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return typeIntervention;
	}
}
