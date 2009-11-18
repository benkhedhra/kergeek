package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Compte;
import metier.Utilisateur;

public class DAOUtilisateur {


	public static boolean createUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqUtilisateur.NEXTVAL from dual");
			if (res.next()){
				String id = "u" + res.getString("dummy");
				utilisateur.getCompte().setId(id);

				/*TODO
				 * a verifier...
				 */
				
				s.executeUpdate("INSERT into Utilisateur values ('"
						+ utilisateur.getCompte().getId() + "', '"
						+ utilisateur.getNom() + "', '"
						+ utilisateur.getPrenom() + "', '"
						+ utilisateur.getAdressePostale() + "', '"
						+ utilisateur.isBloque() + "', '"
						+ utilisateur.getVelo()
						+ "')");
				DAOCompte.createCompte(utilisateur.getCompte());
				effectue=true;
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
		}
		return effectue;
	}

	public static boolean updateUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Utilisateur SET"  
					+ "nom = '" + utilisateur.getNom() + "',"
					+ "prenom = '"+ utilisateur.getPrenom() + "',"
					+ "adressePostale = '"+ utilisateur.getAdressePostale() + "',"
					+ "bloque = '"+ utilisateur.isBloque() + "',"
					+ "idVelo = '"+ utilisateur.getVelo() + "'"
					+ "' WHERE idCompte = '"+ utilisateur.getCompte().getId() + "'"
			);

			ConnexionOracleViaJdbc.fermer();
			effectue = DAOCompte.updateCompte(utilisateur.getCompte());
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
		}
		return effectue;
	}

	public static Utilisateur getUtilisateurById(String identifiant) throws SQLException, ClassNotFoundException {
		Utilisateur u = new Utilisateur(new Compte());

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque, idVelo from Utilisateur Where idCompte ='" + identifiant+"'");
		try {
			if (res.next()) {
				u.setCompte(DAOCompte.getCompteById(res.getString(identifiant)));
				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));
				u.setVelo(DAOVelo.getVeloById(res.getString("idVelo")));
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



	public static Utilisateur getUtilisateurByAdresseEmail(String email) throws SQLException, ClassNotFoundException {
		Utilisateur u = new Utilisateur(new Compte());

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque, idVelo from Utilisateur Where idCompte ='" + DAOCompte.getCompteByAdresseEmail(email).getId()+"'");
		try {
			if (res.next()) {
				u.setCompte(DAOCompte.getCompteById(res.getString("idCompte")));
				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));
				u.setVelo(DAOVelo.getVeloById(res.getString("idVelo")));
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



	public static ArrayList<Utilisateur> getUtilisateurByNom(String nom) throws SQLException, ClassNotFoundException {
		ArrayList<Utilisateur> listeUtils = null;
		/*
		 * TODOOn peut peut-etre utiliser une HashMap (peut-etre que c'est mieux)
		 */

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select prenom, adressePostale, bloque, idCompte, idVelo from Utilisateur Where nom ='" + nom +"'");
		try {
			while (res.next()) {
				Utilisateur u = new Utilisateur(new Compte());
				u.setNom(nom);
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));
				u.setVelo(DAOVelo.getVeloById(res.getString("idVelo")));
				u.setCompte(DAOCompte.getCompteById(res.getString("idCompte")));
				listeUtils.add(u);
			}
			if(listeUtils == null){
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}


	public static ArrayList<Utilisateur> getUtilisateurByPrenom(String prenom) throws SQLException, ClassNotFoundException {
		ArrayList<Utilisateur> listeUtils = null;
		/*
		 * TODO
		 * On peut peut-etre utiliser une HashMap (peut-etre que c'est mieux)
		 */

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select nom, adressePostale, bloque, idCompte, idVelo from Utilisateur Where prenom ='" + prenom +"'");
		try {
			while (res.next()) {
				Utilisateur u = new Utilisateur(new Compte());
				u.setPrenom(prenom);
				u.setNom(res.getString("nom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));
				u.setVelo(DAOVelo.getVeloById(res.getString("idVelo")));
				u.setCompte(DAOCompte.getCompteById(res.getString("idCompte")));
				listeUtils.add(u);
			}
			if(listeUtils == null){
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}
}