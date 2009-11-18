package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Compte;

public class DAOCompte {


	public static boolean createCompte(Compte compte) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqCompte.NEXTVAL from dual");
			if (res.next()){
				String id = res.getString("dummy");
				compte.setId(compte.getTypeLettre() + id);
				/*TODO
				 * a verifier...
				 */
			}
			else{
				throw new SQLException("probleme de sequence");
			}
			s.executeUpdate("INSERT into Velo values ('" 
					+ compte.getId() + "', '" 
					+ compte.getMotDePasse() + "', '" 
					+ compte.isActif() + "', '" 
					+ compte.getType() + "', '" 
					+ compte.getAdresseEmail()
					+ "')");
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






	public static boolean updateCompte(Compte compte) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Compte SET"  
					+ "motDePasse = '" + compte.getMotDePasse() + "',"
					+ "actif = '"+ compte.isActif() + "',"
					+ "adresseEmail = '" + compte.getAdresseEmail() + "',"
					+ "' WHERE idCompte = '"+ compte.getId() + "'"
			);
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
		}
		return effectue;
	}





	public static Compte getCompteById(String identifiant) throws SQLException, ClassNotFoundException {
		Compte compte = new Compte();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select motDePasse, actif, type, adresseMail from Compte Where idCompte ='" + identifiant + "'");
		try {
			if (res.next()) {
				compte.setId(identifiant);
				compte.setMotDePasse(res.getString("motDePasse"));
				compte.setActif(res.getBoolean("actif"));
				compte.setType(res.getInt("type"));
				compte.setAdresseEmail(res.getString("adresseMail"));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return compte;
	}


	public static Compte getCompteByAdresseEmail(String email) throws SQLException, ClassNotFoundException {
		Compte compte = new Compte();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idCompte, motDePasse, actif, type from Compte Where AdresseMail ='" + email + "'");
		try {
			if (res.next()) {
				compte.setAdresseEmail(email);
				compte.setId(res.getString("idCompte"));
				compte.setMotDePasse(res.getString("motDePasse"));
				compte.setActif(res.getBoolean("actif"));
				compte.setType(res.getInt("type"));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'adresse email");
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return compte;
	}

}
