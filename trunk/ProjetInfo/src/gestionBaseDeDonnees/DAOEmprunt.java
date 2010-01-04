package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Compte;
import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;

/**
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link Emprunt}.
 * @author KerGeek
 */
public class DAOEmprunt {

	/**
	 * Ajoute une instance de la classe {@link Emprunt} à la base de données.
	 * @param emprunt
	 * l'instance de la classe {@link Emprunt} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqEmprunt.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				emprunt.setId(id);

				if (emprunt.getStationRetour() != null){

					s.executeUpdate("INSERT into Emprunt values (" 
							+ "'" + emprunt.getId() + "',"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateEmprunt()) +"','DD-MM-YYYY HH24:MI'),"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateRetour()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'" + emprunt.getStationEmprunt().getId() + "',"
							+ "'" + emprunt.getStationRetour().getId() + "',"
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
							+  "'" + emprunt.getStationEmprunt().getId() + "',"
							+ "'',"
							+  "'" + emprunt.getUtilisateur().getCompte().getId() + "'," 
							+  "'" + emprunt.getVelo().getId() + "'" +
					")");
					effectue=true;
				}
			}

		}
		catch (SQLException e){
			System.out.println(e.getMessage());//pour se deconnecter de la bdd míme si des exceptions sont soulevées
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd míme si des exceptions sont soulevées
		}
		return effectue;
	}

	/**
	 * Met à jour une instance de la classe {@link Emprunt} déjà présente dans la base de données.
	 * @param emprunt
	 * l'instance de la classe {@link Emprunt} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateEmprunt(Emprunt emprunt) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			if (DAOEmprunt.getEmpruntById(emprunt.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();

				s.executeUpdate("UPDATE Emprunt SET "
						+ "idCompte = '" + emprunt.getUtilisateur().getCompte().getId() + "', "
						+ "idvelo = '" + emprunt.getVelo().getId() + "', "
						+ "idlieuEmprunt = '" + emprunt.getStationEmprunt().getId() + "', "
						+ "idlieuRetour = '" + emprunt.getStationRetour().getId() + "', "
						+ "dateEmprunt = " + "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateEmprunt()) +"','DD-MM-YYYY HH24:MI'), "
						+ "dateRetour = " +  "TO_DATE('" + UtilitaireDate.conversionPourSQL(emprunt.getDateRetour()) +"','DD-MM-YYYY HH24:MI') "
						+ " WHERE idEmprunt = '"+ emprunt.getId() + "'"
				);
				effectue=true;
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de données, mise à jour impossible");
			}
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd míme si des exceptions sont soulevées
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
		catch(PasDansLaBaseDeDonneeException e3){
			System.out.println(e3.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * @param identifiant
	 * @return l'instance de la classe {@link Emprunt} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOLieu#getLieuById(String)
	 * @see DAOUtilisateur#getUtilisateurById(String)
	 * @see DAOVelo#getVeloById(String)
	 */
	public static Emprunt getEmpruntById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Emprunt emprunt = new Emprunt();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
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
					emprunt.setStationEmprunt((Station) DAOLieu.getLieuById(idLieuEmprunt));
					emprunt.setStationRetour((Station) DAOLieu.getLieuById(idLieuRetour));
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
		}
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return emprunt;
	}


	/**
	 * @param station
	 * @param depuisJours
	 * le nombre de jours depuis la date actuelle au cours desquels ont veut avoir les nombre de vélos sortis de la station,
	 * doit ítre positif
	 * @return le nombre de velos empruntés à la staion depuis depuisJours jours
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see UtilitaireDate#retrancheJours(Date, int)
	 * @see UtilitaireDate#dateCourante()
	 * @see UtilitaireDate#initialisationDebutJour(Date) 
	 */
	public static int NombreVelosSortisJours(Station station, int depuisJours) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(), depuisJours);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);
		try{
			ResultSet res = s.executeQuery("Select count(*) as nombreVeloSortis from Emprunt Where idLieuEmprunt ='" + station.getId() + "' and dateEmprunt >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','DD-MM-YYYY HH24:MI')");
			try {
				if (res.next()){
					nb = res.getInt("nombreVeloSortis");
				}
			}
			catch (SQLException e1){
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

		return nb;
	}



	/**
	 * @param station
	 * @param depuisHeures
	 * le nombre d'heures depuis la date actuelle au cours desquelles ont veut avoir le nombre de vélos sortis de la station, 
	 * doit ítre positif
	 * @return le nombre de vélos empruntés à la staion depuis depuisHeures heures
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see UtilitaireDate#retrancheHeures(Date, int)
	 * @see UtilitaireDate#dateCourante()
	 * @see UtilitaireDate#initialisationDebutJour(Date)  
	 */
	public static int NombreVelosSortisHeures(Station station, int depuisHeures) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheHeures(UtilitaireDate.dateCourante(), depuisHeures);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);

		/* System.out.println("dateSqlTemp = "+dateSqlTemp.getHours());
		 System.out.println("dateSql = "+dateSql.getHours());*/

		try{
			ResultSet res = s.executeQuery("Select count(*) as nombreVeloSortis from Emprunt Where idLieuEmprunt ='" + station.getId() + "' and dateEmprunt >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','DD-MM-YYYY HH24:MI')");
			try {
				if (res.next()){
					nb = res.getInt("nombreVeloSortis");
				}
			}
			catch (SQLException e1){
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

		return nb;
	}





	/**
	 * @param station
	 * @param depuisJours
	 * le nombre de jours depuis la date actuelle au cours desquels ont veut avoir le nombre de vélos rendus à la station,
	 * doit ítre positif
	 * @return le nombre de velos rendus à la station depuis depuisJours jours.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see UtilitaireDate#retrancheJours(Date, int)
	 * @see UtilitaireDate#dateCourante()
	 * @see UtilitaireDate#initialisationDebutJour(Date)  
	 */
	public static int NombreVelosRendusJours(Station station, int depuisJours) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(), depuisJours);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);
		try{
			ResultSet res = s.executeQuery("Select count(*) as nombreVeloRentres from Emprunt Where idLieuRetour ='" + station.getId() + "' and dateRetour >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','DD-MM-YYYY HH24:MI')");
			try {
				if (res.next()){
					nb = res.getInt("nombreVeloRentres");
				}
			}
			catch (SQLException e1){
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
		return nb;
	}


	/** 
	 * @param station
	 * @param depuisHeures
	 * le nombre d'heures depuis la date actuelle au cours desquelles ont veut avoir le nombre de vélos rendus à la station,
	 * doit ítre positif
	 * @return le nombre de velos rendus dans la station depuis depuisHeures heures.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see UtilitaireDate#retrancheHeures(Date, int)
	 * @see UtilitaireDate#dateCourante()
	 * @see UtilitaireDate#initialisationDebutJour(Date) 
	 */

	public static int NombreVelosRendusHeures(Station station, int depuisHeures) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		int nb =0;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		java.sql.Date dateSqlTemp = UtilitaireDate.retrancheHeures(UtilitaireDate.dateCourante(), depuisHeures);
		java.sql.Date dateSql = UtilitaireDate.initialisationDebutJour(dateSqlTemp);
		try{
			ResultSet res = s.executeQuery("Select count(*) as nombreVeloRentres from Emprunt Where idLieuRetour ='" + station.getId() + "' and dateRetour >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) +"','DD-MM-YYYY HH24:MI')");
			try {
				if (res.next()){
					nb = res.getInt("nombreVeloRentres");
				}
			}
			catch (SQLException e1){
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
		return nb;
	}








	/** 
	 * @param utilisateur
	 * @param nbMois
	 * le nombre de mois depuis la date actuelle au cours de chacun desquels ont veut avoir le nombre d'emprunts effectués 
	 * par l'utilisateur u, doit ítre positif
	 * @return la liste des nombres d'emprunts effectués par l'utilisateur u au cours des nbMois derniers mois,
	 * pour chacun de ces mois.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see UtilitaireDate#retrancheMois(Date, int)
	 * @see UtilitaireDate#dateCourante()
	 * @see UtilitaireDate#initialisationDebutMois(Date) 
	 */
	public static List<Integer> getNombreEmpruntParUtilisateurParMois(Utilisateur utilisateur, int nbMois) throws SQLException, ClassNotFoundException, ConnexionFermeeException{

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

				res = s.executeQuery("Select count(*) as nombreEmpruntParMois from Emprunt WHERE idCompte= '" + utilisateur.getCompte().getId() + "' and dateEmprunt <= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSqlSup) + "','DD-MM-YYYY HH24:MI') and dateEmprunt >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSqlMin) + "','DD-MM-YYYY HH24:MI')");
				if (res.next()){
					liste.add(res.getInt("nombreEmpruntParMois"));
				}
				else{
					liste.add(0);
				}
			}
		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
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

	/**
	 * Associe un {@link Emprunt} au vélo passé en paramètre lorsque celui-ci est actuellement emprunté.
	 * @param velo
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 * @see DAOVelo#getVeloById(String)
	 */
	public static void setEmpruntEnCoursByVelo(Velo velo) throws ClassNotFoundException, SQLException, ConnexionFermeeException{

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
				emprunt.setStationEmprunt((Station) DAOLieu.getLieuById(idLieuEmprunt));
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
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally {
			ConnexionOracleViaJdbc.fermer();//pour etre bien sur de se deconnecter de la bdd
		}
	}


	/**
	 * Associe un {@link Emprunt} à l'utilisateur passé en paramètre lorsque celui-ci a actuellement un emprunt en cours.
	 * @param utilisateur
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOUtilisateur#getUtilisateurById(String)
	 */
	public static void setEmpruntEnCoursByUtilisateur(Utilisateur utilisateur) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
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
				emprunt.setStationEmprunt((Station) DAOLieu.getLieuById(idLieuEmprunt));

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
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd míme si des exceptions sont soulevées
		}
	}
}
