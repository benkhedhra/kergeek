package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

		ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque from Compte Where idCompte ='" + identifiant+"'");
		try {
			if (res.next()) {

				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));

				u.setCompte(DAOCompte.getCompteById(identifiant));
				u.setEmpruntEnCours(DAOEmprunt.getEmpruntEnCoursByIdUtilisateur(identifiant));
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
		String idCompte = DAOCompte.getCompteByAdresseEmail(email).getId();
		Utilisateur u = getUtilisateurById(idCompte);
		return u;
	}



	public static List<Utilisateur> getUtilisateurByNom(String nom) throws SQLException, ClassNotFoundException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();
		List<String> listeIdCompte = new ArrayList<String>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idCompte from Compte Where nom ='" + nom +"'");
		try {
			while (res.next()) {
				listeIdCompte.add(res.getString("idCompte"));
			}
			for (String id : listeIdCompte){
				listeUtils.add(getUtilisateurById(id));
			}
			if(listeUtils == null){
				throw new PasDansLaBaseDeDonneeException("Nom absent de la base de donn�es");
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
		List<String> listeIdCompte = new ArrayList<String>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idCompte from Compte Where prenom ='" + prenom +"'");
		try {
			while (res.next()) {
				listeIdCompte.add(res.getString("idCompte"));
			}
			for (String id : listeIdCompte){
				listeUtils.add(getUtilisateurById(id));
			}
			if(listeUtils == null){
				throw new PasDansLaBaseDeDonneeException("Prenom absent de la base de donn�es");
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

}