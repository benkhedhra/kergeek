package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.UtilitaireDate;

public class DAOEmprunt {


	public static boolean createEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqEmprunt.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
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
				s.executeUpdate("COMMIT");
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		finally{
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


	/**
	 * 
	 * @param station
	 * @param depuisJours : nombre de jours sur lesquels ont veut avoir les nombre de velos sortis de la station
	 * @return le nombre de velos rentres dans la staion depuis depuisJours jours (depuisJours doit etre positif).
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int NombreVelosSortis(Station station, int depuisJours) throws SQLException, ClassNotFoundException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSql = UtilitaireDate.retrancheJour(UtilitaireDate.dateCourante(), depuisJours);

		/*TODO
		 * System.out.println(dateSql.toString());
		 */

		ResultSet res = s.executeQuery("Select count(*) as nombreVeloSortis from Emprunt Where idLieuEmprunt ='" + station.getId() + "' and dateEmprunt >= TO_DATE('" + dateSql +"','YYYY-MM-DD-HH24:MI')");
		try {
			if (res.next()){
				nb = res.getInt("nombreVeloSortis");
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return nb;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param station
	 * @param depuisJours: nombre de jours sur lesquels ont veut avoir les nombre de v�los rentres dans la station
	 * @return le nombre de velos rentres dans la station depuis depuisJours jours.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int NombreVelosRentres(Station station, int depuisJours) throws SQLException, ClassNotFoundException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSql = UtilitaireDate.retrancheJour(UtilitaireDate.dateCourante(), depuisJours);

		/*TODO
		 * System.out.println(dateSql.toString());
		 */

		ResultSet res = s.executeQuery("Select count(*) as nombreVeloRentres from Emprunt Where idLieuRetour ='" + station.getId() + "' and dateRetour >= TO_DATE('" + dateSql +"','YYYY-MM-DD-HH24:MI')");
		try {
			if (res.next()){
				nb = res.getInt("nombreVeloRentres");
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return nb;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param u
	 * @param nbMois
	 * @return la liste des nombre d'emprunts effectue par l'utilisateur u au cours
	 *			des nbMois derniers mois, pour chacun de ces mois.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static List<Integer> getNombreEmpruntParUtilisateurParMois(Utilisateur u, int nbMois) throws SQLException, ClassNotFoundException{

		List <Integer> liste = new ArrayList<Integer>();
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			
			
			
			/*TODO
			 * System.out.println(dateSql.toString());
			 */
			
			ResultSet res = null;
			for (int i=1; i <= nbMois; i++){
				java.sql.Date dateSqlSupTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(),i);
				java.sql.Date dateSqlSup = UtilitaireDate.initialisationDebutMois(dateSqlSupTemp);
				
				java.sql.Date dateSqlMinTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(),i+1);
				java.sql.Date dateSqlMin = UtilitaireDate.initialisationDebutMois(dateSqlMinTemp);
				
				res = s.executeQuery("Select count(*) as nombreEmpruntParMois from Emprunt WHERE idCompte= '" + u.getCompte().getId() + "' and dateEmprunt <= TO_DATE('" + dateSqlSup + "','YYYY-MM-DD-HH24:MI') and dateEmprunt >= TO_DATE('" + dateSqlMin + "','YYYY-MM-DD-HH24:MI')");
				if (res.next()){
					liste.add(res.getInt("nombreEmpruntParMois"));
				}
				else{
					liste.add(0);
				}
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}
}
