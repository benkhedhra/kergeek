package gestionBaseDeDonnees;

import java.sql.SQLException;

import metier.Technicien;

public class DAOTechnicien {



	public static boolean createTechnicien(Technicien tech) throws SQLException, ClassNotFoundException {
			return DAOCompte.createCompte(tech.getCompte());
	}

	
	
	public static boolean updateTechnicien(Technicien tech) throws SQLException, ClassNotFoundException {
		return DAOCompte.updateCompte(tech.getCompte());
	}
	
	
	
	public static Technicien getTechnicienById(String identifiant) throws SQLException, ClassNotFoundException {
		Technicien admin = new Technicien(DAOCompte.getCompteById(identifiant));
		return admin;
	}
	
	
	
	public static Technicien getTechnicienByAdresseEmail(String email) throws SQLException, ClassNotFoundException {
		Technicien admin = new Technicien(DAOCompte.getCompteByAdresseEmail(email));
		return admin;
	}
	
}
