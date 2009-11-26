package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import metier.Compte;
import metier.Utilisateur;

public class DAOUtilisateur {


	public static boolean createUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqUtilisateur.NEXTVAL as id from dual");
			if (res.next()){
				String id = "u" + res.getString("id");
				utilisateur.getCompte().setId(id);
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
		}
		DAOCompte.createCompte(utilisateur.getCompte());
		effectue = updateUtilisateur(utilisateur);
		return effectue;
	}

	public static boolean updateUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Compte SET "  
					+ "nom = '" + utilisateur.getNom() + "', "
					+ "prenom = '"+ utilisateur.getPrenom() + "', "
					+ "adressePostale = '"+ utilisateur.getAdressePostale() + "' "
					+ "WHERE idCompte = '"+ utilisateur.getCompte().getId() + "'"
			);
			if (utilisateur.getVelo()!=null){
				s.executeUpdate("UPDATE Compte SET idVelo = '" + utilisateur.getVelo().getId() + "'"); 	
			}
			if(utilisateur.isBloque()){
				s.executeUpdate("UPDATE Compte SET bloque = '1'WHERE idCompte = '"+ utilisateur.getCompte().getId() + "'"); 
			}
			else {
				s.executeUpdate("UPDATE Compte SET bloque = '0'WHERE idCompte = '"+ utilisateur.getCompte().getId() + "'"); 
				
			}
			s.executeUpdate("COMMIT");
			effectue = true;
			
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
			effectue = DAOCompte.updateCompte(utilisateur.getCompte());
		}
		return effectue;
	}

	
	
	public static Utilisateur getUtilisateurById(String identifiant) throws SQLException, ClassNotFoundException {
		Utilisateur u = new Utilisateur(new Compte());

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque, idVelo from Compte Where idCompte ='" + identifiant+"'");
		try {
			if (res.next()) {
				u.setCompte(DAOCompte.getCompteById(identifiant));
				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));
				u.setVelo(DAOVelo.getVeloById(res.getString("idVelo")));
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant du compte de l'utilisateur");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
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

		ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque, idVelo from Compte Where idCompte ='" + DAOCompte.getCompteByAdresseEmail(email).getId()+"'");
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
				throw new PasDansLaBaseDeDonneeException("Erreur d'adresse email");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return u;
	}



	public static List<Utilisateur> getUtilisateurByNom(String nom) throws SQLException, ClassNotFoundException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select prenom, adressePostale, bloque, idCompte, idVelo from Compte Where nom ='" + nom +"'");
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
				throw new PasDansLaBaseDeDonneeException("Nom absent de la base de données");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}


	public static List<Utilisateur> getUtilisateurByPrenom(String prenom) throws SQLException, ClassNotFoundException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select nom, adressePostale, bloque, idCompte, idVelo from Compte Where prenom ='" + prenom +"'");
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
				throw new PasDansLaBaseDeDonneeException("Prénom absent de la base de données");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}
}