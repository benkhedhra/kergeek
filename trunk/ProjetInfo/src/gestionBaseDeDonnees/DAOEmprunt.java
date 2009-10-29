package gestionBaseDeDonnees;

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
			s.executeUpdate("INSERT into Emprunt values ('" 
					+ emprunt.getId() +  "', '" 
					+ emprunt.getUtilisateur().getCompte().getId() +  "', '" 
					+ emprunt.getVelo().getId() + "', '" 
					+ emprunt.getDateEmprunt() + "', '" 
					+ emprunt.getLieuEmprunt().getId() + "')");
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}

	
	
	
	
	public static boolean updateDateRetour(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Emprunt SET dateRetour = '" + emprunt.getDateRetour() 
					+ "' WHERE idEmprunt = '"+ emprunt.getId() + "'");
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}
	
	
	
	
	public static boolean updateLieuRetour(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Emprunt SET idLieuRetour = '" + emprunt.getLieuRetour().getId() 
					+ "' WHERE idEmprunt = '"+ emprunt.getId() + "'");
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}

}
