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
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link Technicien}
 * @see DAOCompte
 * @author KerGeek
 */
public class DAOTechnicien {
	
	/**
	 * Ajoute une instance de la classe {@link Technicien} � la base de donn�es.
	 * @param tech
	 * l'instance de la classe {@link Technicien} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
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
	 * Met � jour une instance de la classe {@link Technicien} d�j� pr�sente dans la base de donn�es.
	 * @param tech
	 * l'instance de la classe {@link Technicien} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
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
	 * @return l'instance de la classe {@link Technicien} dont l'identifiant correspond au param�tre.
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
	 * @return l'instance de la classe {@link Technicien} dont l'adresseEmail correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#getCompteByAdresseEmail(String)
	 */
	public static Technicien getTechnicienByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Technicien admin = new Technicien(DAOCompte.getCompteByAdresseEmail(email).get(0));
		//Deux Techniciens ne doivent pas avoir la m�me adresse email
		return admin;
	}
	
	/**
	 * @return la liste de l'ensemble des {@link Technicien} pr�sents dans la base de donn�es.
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

