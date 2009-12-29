package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Compte;
import metier.Technicien;

/**
 * Rassemble l'ensemble des mŽthodes static de liaison avec la base de données concernant la classe metier {@link Technicien}
 * @see DAOCompte
 * @author KerGeek
 */
public class DAOTechnicien {
	
	/**
	 * Ajoute une instance de la classe {@link Technicien} à la base de données.
	 * @param tech
	 * l'instance de la classe {@link Technicien} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#createCompte(Compte)
	 */
	public static boolean createTechnicien(Technicien tech) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return DAOCompte.createCompte(tech.getCompte());
	}

	/**
	 * Met à jour une instance de la classe {@link Technicien} déjà présente dans la base de données.
	 * @param tech
	 * l'instance de la classe {@link Technicien} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#updateCompte(Compte)
	 */
	public static boolean updateTechnicien(Technicien tech) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return DAOCompte.updateCompte(tech.getCompte());
	}

	/** 
	 * @param identifiant
	 * @return l'instance de la classe {@link Technicien} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#getCompteById(String)
	 */
	public static Technicien getTechnicienById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Technicien admin = new Technicien(DAOCompte.getCompteById(identifiant));
		return admin;
	}

	/**
	 * @param email
	 * @return l'instance de la classe {@link Technicien} dont l'adresseEmail correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#getCompteByAdresseEmail(String)
	 */
	public static Technicien getTechnicienByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Technicien admin = new Technicien(DAOCompte.getCompteByAdresseEmail(email).get(0));
		//Deux Techniciens ne doivent pas avoir la míme adresse email
		return admin;
	}
	
	/**
	 * @return la liste de l'ensemble des {@link Technicien} présents dans la base de données.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOTechnicien#getTechnicienById(String)
	 */
	public static List<Technicien> getAllTechniciens() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Technicien> liste = new ArrayList<Technicien>();
		List<String> listeId = new ArrayList<String>();

		Technicien technicien = new Technicien();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select idCompte from Compte WHERE type = '" + Compte.TYPE_TECHNICIEN + "'");
			try {
				while(res.next()) {
					String idCompte = res.getString("idCompte"); 
					listeId.add(idCompte);
				}
				for(String id : listeId){
					technicien = DAOTechnicien.getTechnicienById(id);
					liste.add(technicien);
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

