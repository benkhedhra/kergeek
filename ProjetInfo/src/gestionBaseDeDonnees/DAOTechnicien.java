package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptionsTechniques.ConnexionFermeeException;

import metier.Compte;
import metier.Lieu;
import metier.Station;
import metier.Technicien;

public class DAOTechnicien {



	public static boolean createTechnicien(Technicien tech) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return DAOCompte.createCompte(tech.getCompte());
	}



	public static boolean updateTechnicien(Technicien tech) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return DAOCompte.updateCompte(tech.getCompte());
	}



	public static Technicien getTechnicienById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Technicien admin = new Technicien(DAOCompte.getCompteById(identifiant));
		return admin;
	}



	public static Technicien getTechnicienByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Technicien admin = new Technicien(DAOCompte.getCompteByAdresseEmail(email));
		return admin;
	}

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
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}
}

