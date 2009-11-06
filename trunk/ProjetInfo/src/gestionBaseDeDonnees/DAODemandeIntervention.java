package gestionBaseDeDonnees;

import java.sql.SQLException;
import java.sql.Statement;

import metier.DemandeIntervention;

public class DAODemandeIntervention {
	public static boolean createDemandeIntervention(DemandeIntervention ddeIntervention) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("INSERT into DemandeIntervention values ('" 
					+ UtilitaireDate.dateCourante() + "', '"+ ddeIntervention.getVelo() + "', '" 
					+ ddeIntervention.getUtilisateur().getCompte().getId() + "')");
			ConnexionOracleViaJdbc.fermer();
			effectue=true;
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
