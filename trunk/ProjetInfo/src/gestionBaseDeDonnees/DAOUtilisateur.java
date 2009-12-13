package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import metier.Compte;
import metier.Utilisateur;

public class DAOUtilisateur {


	public static boolean createUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		DAOCompte.createCompte(utilisateur.getCompte());
		effectue = updateUtilisateur(utilisateur);
		return effectue;
	}

	public static boolean updateUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
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
			if(utilisateur.isBloque()){
				s.executeUpdate("UPDATE Compte SET bloque = '1'WHERE idCompte = '"+ utilisateur.getCompte().getId() + "'"); 
			}
			else {
				s.executeUpdate("UPDATE Compte SET bloque = '0'WHERE idCompte = '"+ utilisateur.getCompte().getId() + "'"); 

			}
			s.executeUpdate("COMMIT");
			effectue = true;

		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
			effectue = DAOCompte.updateCompte(utilisateur.getCompte());
		}
		return effectue;
	}



	public static Utilisateur getUtilisateurById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Utilisateur u = new Utilisateur(new Compte());

		ConnexionOracleViaJdbc.ouvrir();
		try{
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque from Compte Where idCompte ='" + identifiant+"'");
			if (res.next()) {

				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));

				u.setCompte(DAOCompte.getCompteById(identifiant));
				DAOEmprunt.setEmpruntEnCoursByUtilisateur(u);
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
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return u;
	}



	public static Utilisateur getUtilisateurByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		String idCompte = DAOCompte.getCompteByAdresseEmail(email).getId();
		Utilisateur u = getUtilisateurById(idCompte);
		return u;
	}



	public static List<Utilisateur> getUtilisateurByNom(String nom) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();
		List<String> listeIdCompte = new ArrayList<String>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		try {
			ResultSet res = s.executeQuery("Select idCompte from Compte Where nom ='" + nom +"'");
			while (res.next()) {
				listeIdCompte.add(res.getString("idCompte"));
			}
			for (String id : listeIdCompte){
				listeUtils.add(getUtilisateurById(id));
			}
			if(listeUtils == null){
				throw new PasDansLaBaseDeDonneeException("Nom absent de la base de donnŽes");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		catch(NullPointerException e3){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}


	public static List<Utilisateur> getUtilisateurByPrenom(String prenom) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();
		List<String> listeIdCompte = new ArrayList<String>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try {
			ResultSet res = s.executeQuery("Select idCompte from Compte Where prenom ='" + prenom +"'");

			while (res.next()) {
				listeIdCompte.add(res.getString("idCompte"));
			}
			for (String id : listeIdCompte){
				listeUtils.add(getUtilisateurById(id));
			}
			if(listeUtils == null){
				throw new PasDansLaBaseDeDonneeException("Prenom absent de la base de données");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		catch(NullPointerException e3){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}

	public static java.sql.Date getDerniereDateRetour(Utilisateur u) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		java.sql.Date dateDernierRetour = null;

		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select dateRetour from Emprunt WHERE dateRetour IS NOT NULL AND idCompte = '" + u.getCompte().getId()+"' ORDER BY dateRetour DESC");

			if (res.next()) {

				java.sql.Timestamp tempsDernierRetour = res.getTimestamp("dateRetour");
				dateDernierRetour = new java.sql.Date(tempsDernierRetour.getTime());
			}


		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return dateDernierRetour;
	}

}