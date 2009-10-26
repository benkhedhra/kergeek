package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Compte;

public class DAOCompte {
	
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
