package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import metier.Emprunt;
import metier.Lieu;

public class DAOEmprunt {


	public static boolean createEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqEmprunt.NEXTVAL from dual");
			if (res.next()){
				String id = res.getString("dummy");
				emprunt.setId(id);

				/*TODO
				 * a verifier...
				 */

				s.executeUpdate("INSERT into Emprunt values ('" 
						+ emprunt.getId() +  "', '"
						+ emprunt.getDateEmprunt() + "', '"
						+ emprunt.getDateRetour() + "', '"
						+ emprunt.getLieuEmprunt().getId() + "', '"
						+ emprunt.getLieuRetour().getId() + "', '"
						+ emprunt.getUtilisateur().getCompte().getId() +  "', '" 
						+ emprunt.getVelo().getId() + 
				"')");
				effectue=true;
				ConnexionOracleViaJdbc.fermer();
			}
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}





	public static boolean updateEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Emprunt SET"
					+ "idUtilisateur = '" + emprunt.getUtilisateur().getCompte().getId() + "',"
					+ "idvelo = '" + emprunt.getVelo().getId() 
					+ "lieuEmprunt = '" + emprunt.getLieuEmprunt() + "',"
					+ "lieuRetour = '" + emprunt.getLieuRetour() + "',"
					+ "dateEmprunt = '" + emprunt.getDateEmprunt() + "',"
					+ "dateRetour = '" + emprunt.getDateRetour() + "'" 
					+ "WHERE idEmprunt = '"+ emprunt.getId() + "'"
			);
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}

}
