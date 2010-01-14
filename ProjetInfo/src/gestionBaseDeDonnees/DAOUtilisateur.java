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
import metier.Emprunt;
import metier.Utilisateur;

/**
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link Utilisateur}.
 * @see DAOCompte
 * @author KerGeek
 */
public class DAOUtilisateur {

	/**
	 * Ajoute une instance de la classe {@link Utilisateur} � la base de donn�es.
	 * @param utilisateur
	 * l'instance de la classe {@link Utilisateur} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#createCompte(Compte)
	 */
	public static boolean createUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		DAOCompte.createCompte(utilisateur.getCompte());
		effectue = updateUtilisateur(utilisateur);
		return effectue;
	}

	/**
	 * Met � jour une instance de la classe {@link Utilisateur} d�j� pr�sente dans la base de donn�es.
	 * @param utilisateur
	 * l'instance de la classe {@link Utilisateur} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#updateCompte(Compte)
	 */
	public static boolean updateUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			//S'il existe bien d�j� une ligne correspondant � cette instance dans la base donn�es
			if (DAOUtilisateur.getUtilisateurById(utilisateur.getCompte().getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met � jour les informations sp�cifiques aux comptes de type Utilisateur
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
			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de donn�es, mise � jour impossible");
			}
		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e2.getMessage());
			}
		}
		catch(PasDansLaBaseDeDonneeException e3){
			System.out.println(e3.getMessage());
		}
		finally{
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
			//On met �galement � jour les informations autres informations d'un compte
			effectue = DAOCompte.updateCompte(utilisateur.getCompte());
		}
		return effectue;
	}

	/** 
	 * Obtient un objet java Utilisateur � partir d'une ligne de la table COMPTE de la base de donn�es.
	 * @param identifiant
	 * @return l'instance de la classe {@link Utilisateur} dont l'identifiant correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#getCompteById(String)
	 * @see DAOEmprunt#setEmpruntEnCoursByUtilisateur(Utilisateur)
	 */
	public static Utilisateur getUtilisateurById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Utilisateur u = new Utilisateur(new Compte());
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select nom, prenom, adressePostale, bloque from Compte Where idCompte ='" + identifiant+"'");
			if (res.next()) {

				u.setNom(res.getString("nom"));
				u.setPrenom(res.getString("prenom"));
				u.setAdressePostale(res.getString("adressePostale"));
				u.setBloque(res.getBoolean("bloque"));

				u.setCompte(DAOCompte.getCompteById(identifiant));
				
				//Au cas o� l'utilisateur a actuellement emprunt� un v�lo
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
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return u;
	}

	/**
	 * @param email
	 * @return la liste des instances de la classe {@link Utilisateur} dont l'adresseEmail correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOUtilisateur#getUtilisateurById(String)
	 * @see DAOCompte#getCompteByAdresseEmail(String)
	 */
	public static List<Utilisateur> getUtilisateurByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//plusieurs utilisateurs peuvent utiliser la m�me adresse email
		List<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
		for (Compte c : DAOCompte.getCompteByAdresseEmail(email)){
			listeUtilisateur.add(getUtilisateurById(c.getId()));
		}
		return listeUtilisateur;
	}

	/**
	 * @param nom
	 * @return la liste des instances de la classe {@link Utilisateur} dont le nom correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOUtilisateur#getUtilisateurById(String)
	 */
	public static List<Utilisateur> getUtilisateurByNom(String nom) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();
		List<String> listeIdCompte = new ArrayList<String>();
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();


			ResultSet res = s.executeQuery("Select idCompte from Compte Where nom ='" + nom +"'");
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
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}

	/**
	 * @param prenom
	 * @return la liste des instances de la classe {@link Utilisateur} dont le pr�nom correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOUtilisateur#getUtilisateurById(String)
	 */
	public static List<Utilisateur> getUtilisateurByPrenom(String prenom) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Utilisateur> listeUtils = new LinkedList<Utilisateur>();
		List<String> listeIdCompte = new ArrayList<String>();
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select idCompte from Compte Where prenom ='" + prenom +"'");

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
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return listeUtils;
	}

	/**
	 * @param u
	 * @return la derni�re date � laquelle l'utilisateur u (en param�tre) a rendu un v�lo. 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 * @see Emprunt
	 */
	public static java.sql.Date getDerniereDateRetour(Utilisateur u) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		java.sql.Date dateDernierRetour = null;

		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select dateRetour from Emprunt WHERE dateRetour IS NOT NULL AND idCompte = '" + u.getCompte().getId()+"' ORDER BY dateRetour DESC");

			if (res.next()) {

				//On r�cup�re un Timestamp
				java.sql.Timestamp tempsDernierRetour = res.getTimestamp("dateRetour");
				//Transformation du Timestamp en date
				dateDernierRetour = new java.sql.Date(tempsDernierRetour.getTime());
			}


		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e2.getMessage());
			}
		}
		finally{
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return dateDernierRetour;
	}

}