package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import metier.Compte;
import metier.Emprunt;
import metier.Lieu;
import metier.Velo;

public class DAOEmprunt {


	public static boolean createEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqEmprunt.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("dummy");
				emprunt.setId(id);

				s.executeUpdate("INSERT into Emprunt values ('" 
						+ emprunt.getId() +  "', '"
						+ emprunt.getDateEmprunt() + "', '"
						+ emprunt.getDateRetour() + "', '"
						+ emprunt.getLieuEmprunt().getId() + "', '"
						+ emprunt.getLieuRetour().getId() + "', '"
						+ emprunt.getUtilisateur().getCompte().getId() +  "', '" 
						+ emprunt.getVelo().getId() + 
				"')");
				effectue=true;
				ConnexionOracleViaJdbc.fermer();
			}
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}





	public static boolean updateEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Emprunt SET"
					+ "idUtilisateur = '" + emprunt.getUtilisateur().getCompte().getId() + "',"
					+ "idvelo = '" + emprunt.getVelo().getId() 
					+ "lieuEmprunt = '" + emprunt.getLieuEmprunt() + "',"
					+ "lieuRetour = '" + emprunt.getLieuRetour() + "',"
					+ "dateEmprunt = '" + emprunt.getDateEmprunt() + "',"
					+ "dateRetour = '" + emprunt.getDateRetour() + "'" 
					+ "WHERE idEmprunt = '"+ emprunt.getId() + "'"
			);
			effectue=true;
			ConnexionOracleViaJdbc.fermer();
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}
	
	public static Velo getVeloById(String identifiant) throws SQLException, ClassNotFoundException {
		Velo velo = new Velo();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idVelo, idLieu, enPanne from Velo Where idVelo ='" + identifiant+"'");
		try {
			if (res.next()) {
				velo.setId(identifiant);
				velo.setLieu(DAOLieu.getLieuById(res.getString("idLieu")));
				velo.setEnPanne(res.getBoolean("enPanne"));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return velo;
	}
	
	public static Emprunt getEmpruntById(String identifiant) throws SQLException, ClassNotFoundException {
		Emprunt emprunt = new Emprunt();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select dateEmprunt, dateRetour, idLieuEmprunt, idLieuRetour, idCompte, idVelo from Emprunt Where idEmprunt ='" + identifiant + "'");
		try {
			if (res.next()) {
				emprunt.setId(identifiant);
				emprunt.setDateEmprunt(res.getDate("dateEmprunt"));
				emprunt.setDateEmprunt(res.getDate("dateRetour"));
				emprunt.setLieuEmprunt(DAOLieu.getLieuById(res.getString("idLieuEmprunt")));
				emprunt.setLieuRetour(DAOLieu.getLieuById(res.getString("idLieuRetour")));
				emprunt.setUtilisateur(DAOUtilisateur.getUtilisateurById(res.getString("idCompte")));
				emprunt.setVelo(DAOVelo.getVeloById(res.getString("idVelo")));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return emprunt;
	}

}
