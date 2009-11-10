package gestionBaseDeDonnees;

import java.sql.SQLException;

import metier.Administrateur;

public class DAOAdministrateur {


	public static boolean createAdministrateur(Administrateur admin) throws SQLException, ClassNotFoundException {
			return DAOCompte.createCompte(admin.getCompte());
	}

	
	
	public static boolean updateAdministrateur(Administrateur admin) throws SQLException, ClassNotFoundException {
		return DAOCompte.updateCompte(admin.getCompte());
	}
	
	
	
	public static Administrateur getAdministrateurById(String identifiant) throws SQLException, ClassNotFoundException {
		Administrateur admin = new Administrateur(DAOCompte.getCompteById(identifiant));
		return admin;
	}
	
	
	
	public static Administrateur getAdministrateurByAdresseEmail(String email) throws SQLException, ClassNotFoundException {
		Administrateur admin = new Administrateur(DAOCompte.getCompteByAdresseEmail(email));
		return admin;
	}
	
}