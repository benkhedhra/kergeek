package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Compte;
import metier.Utilisateur;

public class DAOUtilisateur {

	public static Utilisateur getUtilisateurById(String identifiant) throws SQLException, ClassNotFoundException {
		Utilisateur u = new Utilisateur(new Compte(),"","","");

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select compte, nom, prenom, adresse from Utilisateur Where idUtilisateur ='" + identifiant+"'");
		try {
			if (res.next()) {
				u.setCompte(new Compte (identifiant,"",Compte.TYPE_UTILISATEUR));
				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));

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
		return u;
	}


	public static boolean createUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("INSERT into Utilisateur values ('"  
					/*TODO
					 * 
					 */
					+ "')");
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

}