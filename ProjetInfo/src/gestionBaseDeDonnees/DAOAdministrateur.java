package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Administrateur;
import metier.Compte;

/**
 * Rassemble l'ensembles des mŽthodes static de liaison avec la base de données concernant la classe metier {@link Administrateur}
 * @author KerGeek
 * @see DAOCompte
 */
public class DAOAdministrateur {

	/**
	 * Ajoute une instance de la classe {@link Administrateur} à la base de données.
	 * @param admin
	 * l'instance de la classe {@link Administrateur} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#createCompte(Compte)
	 */
	public static boolean createAdministrateur(Administrateur admin) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return DAOCompte.createCompte(admin.getCompte());
	}

	/**
	 * Met à jour une instance de la classe {@link Administrateur} déjà présente dans la base de données.
	 * @param admin
	 * l'instance de la classe {@link Administrateur} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#updateCompte(Compte)
	 */
	public static boolean updateAdministrateur(Administrateur admin) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return DAOCompte.updateCompte(admin.getCompte());
	}

	/** 
	 * @param identifiant
	 * @return l'instance de la classe {@link Administrateur} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#getCompteById(String)
	 */
	public static Administrateur getAdministrateurById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Administrateur admin = new Administrateur(DAOCompte.getCompteById(identifiant));
		return admin;
	}


	/**
	 * @param email
	 * @return l'instance de la classe {@link Administrateur} dont l'adresseEmail correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#getCompteByAdresseEmail(String)
	 */
	public static Administrateur getAdministrateurByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Administrateur admin = new Administrateur(DAOCompte.getCompteByAdresseEmail(email).get(0)); 
		//Deux Administrateurs ne doivent pas avoir la míme adresse email
		return admin;
	}
	
	/**
	 * @return la liste de l'ensemble des {@link Administrateur} présents dans la base de données.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOAdministrateur#getAdministrateurById(String)
	 */
	public static List<Administrateur> getAllAdministrateurs() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Administrateur> liste = new ArrayList<Administrateur>();
		List<String> listeId = new ArrayList<String>();

		Administrateur administrateur = new Administrateur();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try {
			ResultSet res = s.executeQuery("Select idCompte from Compte WHERE type = '" + Compte.TYPE_ADMINISTRATEUR + "'");

			try{
				while(res.next()) {
					String idCompte = res.getString("idCompte"); 
					listeId.add(idCompte);
				}
				for(String id : listeId){
					administrateur = DAOAdministrateur.getAdministrateurById(id);
					liste.add(administrateur);
				}
			}
			catch(SQLException e1){
				liste = null;
				System.out.println(e1.getMessage());
			}
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
			ConnexionOracleViaJdbc.fermer();
		}
		return liste;
	}

}