package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import metier.Compte;
import metier.Emprunt;
import metier.Lieu;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;

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

				if (emprunt.getLieuRetour() != null){
					
					s.executeUpdate("INSERT into Emprunt values (" 
							+ "'" + emprunt.getId() + "',"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateEmprunt()) +"','DD-MM-YYYY HH24:MI'),"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateRetour()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'" + emprunt.getLieuEmprunt().getId() + "',"
							+ "'" + emprunt.getLieuRetour().getId() + "',"
							+ "'" + emprunt.getUtilisateur().getCompte().getId() + "'," 
							+ "'" + emprunt.getVelo().getId() + "'" +
					")");
					effectue=true;
				}
				else{
					
					s.executeUpdate("INSERT into Emprunt values (" 
							+ "'" +  emprunt.getId() + "',"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateEmprunt()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'',"
							+  "'" + emprunt.getLieuEmprunt().getId() + "',"
							+ "'',"
							+  "'" + emprunt.getUtilisateur().getCompte().getId() + "'," 
							+  "'" + emprunt.getVelo().getId() + "'" +
					")");
					effectue=true;
				}
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
			
			s.executeUpdate("UPDATE Emprunt SET "
					+ "idCompte = '" + emprunt.getUtilisateur().getCompte().getId() + "', "
					+ "idvelo = '" + emprunt.getVelo().getId() + "', "
					+ "idlieuEmprunt = '" + emprunt.getLieuEmprunt().getId() + "', "
					+ "idlieuRetour = '" + emprunt.getLieuRetour().getId() + "', "
					+ "dateEmprunt = " + "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateEmprunt()) +"','DD-MM-YYYY HH24:MI'), "
					+ "dateRetour = " +  "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateRetour()) +"','DD-MM-YYYY HH24:MI') "
					+ " WHERE idEmprunt = '"+ emprunt.getId() + "'"
			);
			effectue=true;
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
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
				java.sql.Timestamp timeEmprunt = res.getTimestamp("dateEmprunt");
				java.sql.Date dateEmprunt = new Date(timeEmprunt.getTime());
				java.sql.Date dateRetour = null;
				if (res.getDate("dateRetour") != null){
					java.sql.Timestamp timeRetour = res.getTimestamp("dateRetour");
					dateRetour = new Date(timeRetour.getTime());
				}
				String idLieuEmprunt = res.getString("idLieuEmprunt");
				String idLieuRetour = res.getString("idLieuRetour");
				String idCompte = res.getString("idCompte");
				String idVelo = res.getString("idVelo");

				emprunt.setId(identifiant);
				emprunt.setDateEmprunt(dateEmprunt);
				emprunt.setDateRetour(dateRetour);
				emprunt.setLieuEmprunt(DAOLieu.getLieuById(idLieuEmprunt));
				emprunt.setLieuRetour(DAOLieu.getLieuById(idLieuRetour));
				emprunt.setUtilisateur(DAOUtilisateur.getUtilisateurById(idCompte));
				emprunt.setVelo(DAOVelo.getVeloById(idVelo));

			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant de l'Emprunt");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
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
	public static int NombreVelosSortis(Lieu lieu, int depuisJours) throws SQLException, ClassNotFoundException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(), depuisJours);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);

		ResultSet res = s.executeQuery("Select count(*) as nombreVeloSortis from Emprunt Where idLieuEmprunt ='" + lieu.getId() + "' and dateEmprunt >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','YYYY-MM-DD-HH24:MI')");
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
	 * @param lieu
	 * @param depuisHeures : nombre d'heures sur lesquelles ont veut avoir les nombre de velos sortis de la station
	 * @return le nombre de velos rentres dans la staion depuis depuisHeures heures (depuisHeures doit etre positif).
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int NombreVelosSortisHeures(Lieu lieu, int depuisHeures) throws SQLException, ClassNotFoundException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheHeures(UtilitaireDate.dateCourante(), depuisHeures);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);

		/* System.out.println("dateSqlTemp = "+dateSqlTemp.getHours());
		 System.out.println("dateSql = "+dateSql.getHours());*/
		 
		
		ResultSet res = s.executeQuery("Select count(*) as nombreVeloSortis from Emprunt Where idLieuEmprunt ='" + lieu.getId() + "' and dateEmprunt >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','YYYY-MM-DD-HH24:MI')");
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
	 * @param depuisJours: nombre de jours sur lesquels ont veut avoir les nombre de vŽlos rentres dans la station
	 * @return le nombre de velos rentres dans la station depuis depuisJours jours.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int NombreVelosRentres(Lieu lieu, int depuisJours) throws SQLException, ClassNotFoundException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(), depuisJours);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);

		ResultSet res = s.executeQuery("Select count(*) as nombreVeloRentres from Emprunt Where idLieuRetour ='" + lieu.getId() + "' and dateRetour >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','YYYY-MM-DD-HH24:MI')");
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
	 * @param lieu
	 * @param depuisheures: nombre d'heures sur lesquels ont veut avoir les nombre de vŽlos rentres dans la station
	 * @return le nombre de velos rentres dans la station depuis depuisHeures heures.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public static int NombreVelosRentresHeures(Lieu lieu, int depuisHeures) throws SQLException, ClassNotFoundException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheHeures(UtilitaireDate.dateCourante(), depuisHeures);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);

		ResultSet res = s.executeQuery("Select count(*) as nombreVeloRentres from Emprunt Where idLieuRetour ='" + lieu.getId() + "' and dateRetour >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','YYYY-MM-DD-HH24:MI')");
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


			ResultSet res = null;
			for (int i=1; i <= nbMois; i++){
				java.sql.Date dateSqlSupTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(),i);
				java.sql.Date dateSqlSup = UtilitaireDate.initialisationDebutMois(dateSqlSupTemp);

				java.sql.Date dateSqlMinTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(),i+1);
				java.sql.Date dateSqlMin = UtilitaireDate.initialisationDebutMois(dateSqlMinTemp);

				res = s.executeQuery("Select count(*) as nombreEmpruntParMois from Emprunt WHERE idCompte= '" + u.getCompte().getId() + "' and dateEmprunt <= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSqlSup) + "','YYYY-MM-DD-HH24:MI') and dateEmprunt >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSqlMin) + "','YYYY-MM-DD-HH24:MI')");
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




	public static void setEmpruntEnCoursByVelo(Velo velo) throws ClassNotFoundException, SQLException{

		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select idEmprunt, dateEmprunt, idLieuEmprunt, idCompte from Emprunt WHERE dateRetour IS NULL AND idVelo = '" + velo.getId() + "'");

			if (res.next()) {
				
				Emprunt emprunt = new Emprunt();
				//On crée ces variables locales pour pouvoir fermer la connexion sans perdre les infos du resultset
				String idEmprunt = res.getString("idEmprunt");
				java.sql.Timestamp tempsEmprunt = res.getTimestamp("dateEmprunt");
				java.sql.Date dateEmprunt = new java.sql.Date(tempsEmprunt.getTime());
				String idLieuEmprunt = res.getString("idLieuEmprunt");
				String idCompte = res.getString("idCompte");

				emprunt.setId(idEmprunt);
				emprunt.setDateEmprunt(dateEmprunt);
				emprunt.setLieuEmprunt(DAOLieu.getLieuById(idLieuEmprunt));
				emprunt.setVelo(velo);

				ConnexionOracleViaJdbc.ouvrir();
				Statement s2 = ConnexionOracleViaJdbc.createStatement();
				ResultSet res2 = s2.executeQuery("Select nom, prenom, adressePostale, bloque from Compte Where idCompte ='" + idCompte +"'");
				if (res2.next()) {
					Utilisateur u = new Utilisateur(new Compte());
					u.setNom(res2.getString("nom"));
					u.setPrenom(res2.getString("prenom"));
					u.setAdressePostale(res2.getString("adressePostale"));
					u.setBloque(res2.getBoolean("bloque"));
					u.setCompte(DAOCompte.getCompteById(idCompte));
					u.setEmpruntEnCours(emprunt);
					
					emprunt.setUtilisateur(u);
					
					velo.setEmpruntEnCours(emprunt);
				}
				else {
					throw new PasDansLaBaseDeDonneeException("Erreur sur l'Utilisateur lors de l'obtention de l'emprunt du velo");
				}
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("PasDansLaBaseDeDonneeException  = "+e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}

		finally {
			ConnexionOracleViaJdbc.fermer();//pour etre bien sur de se deconnecter de la bdd
		}
	}





	public static void setEmpruntEnCoursByUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException{
		Emprunt emprunt = null;

		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select idEmprunt, dateEmprunt, idLieuEmprunt, idVelo from Emprunt WHERE dateRetour IS NULL AND idCompte = '" + utilisateur.getCompte().getId() + "'");

			if (res.next()) {
				emprunt = new Emprunt();
				
				String idEmprunt = res.getString("idEmprunt");
				java.sql.Timestamp tempsEmprunt = res.getTimestamp("dateEmprunt");
				java.sql.Date dateEmprunt = new java.sql.Date(tempsEmprunt.getTime());
				String idLieuEmprunt = res.getString("idLieuEmprunt");
				String idVelo = res.getString("idVelo");
				
				emprunt.setUtilisateur(utilisateur);
				emprunt.setId(idEmprunt);
				emprunt.setDateEmprunt(dateEmprunt);
				emprunt.setLieuEmprunt(DAOLieu.getLieuById(idLieuEmprunt));

				ConnexionOracleViaJdbc.ouvrir();
				Statement s2 = ConnexionOracleViaJdbc.createStatement();

				ResultSet res2 = s2.executeQuery("Select idLieu, enPanne from Velo Where idVelo ='" + idVelo+"'");
				if (res2.next()) {
					//On crée ces variables locales pour pouvoir fermer la connexion sans perdre les infos du resultset
					String idLieu = res2.getString("idLieu");
					Boolean enPanne = res2.getBoolean("enPanne");

					Velo velo = new Velo();

					velo.setId(idVelo);
					velo.setLieu(DAOLieu.getLieuById(idLieu));
					velo.setEnPanne(enPanne);
					velo.setEmpruntEnCours(emprunt);
					
					emprunt.setVelo(velo);
					utilisateur.setEmpruntEnCours(emprunt);
				}
				
				else {
					throw new PasDansLaBaseDeDonneeException("Erreur sur le velo lors de l'obtention de l'emprunt de l'Utilisateur");
				}
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("PasDansLaBaseDeDonneeException  = "+e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
	}
}
