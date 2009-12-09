package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Administrateur;
import metier.Compte;

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
	
	public static List<Administrateur> getAllAdministrateurs() throws SQLException, ClassNotFoundException{
		List<Administrateur> liste = new ArrayList<Administrateur>();
		List<String> listeId = new ArrayList<String>();

		Administrateur administrateur = new Administrateur();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idCompte from Compte WHERE type = '" + Compte.TYPE_ADMINISTRATEUR + "'");
		try {
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
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return liste;
	}
	
}