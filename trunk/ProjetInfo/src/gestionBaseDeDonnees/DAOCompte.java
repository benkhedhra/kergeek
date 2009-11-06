package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Compte;
import metier.Velo;

public class DAOCompte {
	
	
	public static boolean createCompte(Compte compte) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("INSERT into Velo values ('" 
					+ compte.getId() + "', '" + compte.getMotDePasse() + "', '" + compte.isActif() + "', '" + compte.getType() + "')");
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}
	
	public static Compte getCompteById(String identifiant) throws SQLException, ClassNotFoundException {
		Compte compte = new Compte();

		ConnexionOracleViaJdbc.getC();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select motDePasse, actif, type from Compte Where idCompte ='" + identifiant+"'");
		try {
			if (res.next()) {
				compte.setId(identifiant);
				compte.setMotDePasse(res.getString("motDePasse"));
				compte.setActif(res.getBoolean("actif"));
				compte.setType(res.getInt("type"));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}


		return compte;
	}

}
