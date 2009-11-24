package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.DemandeIntervention;
import metier.UtilitaireDate;

public class DAODemandeIntervention {
	public static boolean createDemandeIntervention(DemandeIntervention ddeIntervention) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqDemandeIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				ddeIntervention.setId(id);
				
				s.executeUpdate("INSERT into DemandeIntervention values ('" 
						+ id + "', '"+ UtilitaireDate.dateCourante() + "', '"+ ddeIntervention.getVelo().getId() + "', '" 
						+ ddeIntervention.getUtilisateur().getCompte().getId() + "')");
				s.executeUpdate("COMMIT");
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
