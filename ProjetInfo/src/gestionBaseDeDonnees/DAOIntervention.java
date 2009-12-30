package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Garage;
import metier.Intervention;
import metier.TypeIntervention;
import metier.UtilitaireDate;
import metier.Velo;

/**
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link Intervention}.
 * @author KerGeek
 */
public class DAOIntervention {

	/**
	 * Ajoute une instance de la classe {@link Intervention} à la base de données.
	 * C'est au cours de cette action que les identifiants sont générés à l'aide de séquences SQL.
	 * @param intervention
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createIntervention(Intervention intervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				intervention.setId(id);
				if (intervention.getTypeIntervention() != null){
					s.executeUpdate("INSERT into Intervention values ("
							+ "'"+ intervention.getId() +  "', " 
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(intervention.getDate()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'"+ intervention.getTypeIntervention() + "', "
							+ "'"+ intervention.getVelo().getId() + "'"
							+")");
					effectue=true;
				}
				else{
					s.executeUpdate("INSERT into Intervention values ("
							+ "'"+ intervention.getId() +  "', " 
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(intervention.getDate()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'', "
							+ "'"+ intervention.getVelo().getId() + "'"
							+")");
					effectue=true;
				}
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevées
		}
		return effectue;
	}

	/**
	 * Met à jour une instance de la classe {@link Intervention} déjà présente dans la base de données.
	 * @param intervention
	 * l'instance de la classe {@link Intervention} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateIntervention(Intervention intervention) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			s.executeUpdate("UPDATE Intervention SET "
					+ "dateIntervention = TO_DATE('" + UtilitaireDate.conversionPourSQL(intervention.getDate()) +"','DD-MM-YYYY HH24:MI'), "
					+ "idTypeIntervention = '"+intervention.getTypeIntervention()+"',"
					+ "idVelo = '" + intervention.getVelo().getId() + "' "
					+ "WHERE idIntervention = '"+ intervention.getId() + "'"
			);

			s.executeUpdate("COMMIT");
			effectue=true;
			System.out.println("Intervention mise a jour dans la base de données");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
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
	 * @param identifiant
	 * de l'intervention recherchée
	 * @return l'instance de la classe {@link Intervention} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static Intervention getInterventionById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Intervention intervention = new Intervention();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select * FROM Intervention WHERE idIntervention ='" + identifiant + "'");
			try {
				if (res.next()) {

					java.sql.Timestamp tempsIntervention = res.getTimestamp("dateIntervention");
					java.sql.Date dateIntervention = new java.sql.Date(tempsIntervention.getTime());
					int idTypeIntervention = res.getInt("idTypeIntervention");
					String idVelo = res.getString("idVelo");

					intervention.setId(identifiant);
					intervention.setDate(dateIntervention);
					if(idTypeIntervention != 0){
						intervention.setTypeIntervention(DAOTypeIntervention.getTypeInterventionById(idTypeIntervention));	
					}
					else{
						intervention.setTypeIntervention(null);
					}
					intervention.setVelo(DAOVelo.getVeloById(idVelo));

				}
				else {
					throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant de l'intervention");
				}
			}
			catch(PasDansLaBaseDeDonneeException e1){
				System.out.println(e1.getMessage());
				intervention = null;
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
		return intervention;
	}

	/**
	 * @param velo
	 * des interventions recherchés
	 * @return a liste des instances de la classe {@link Intervention} dont le vélo correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static List<Intervention> getInterventionsByVelo(Velo velo) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Intervention> liste = new ArrayList<Intervention>();

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select * from Intervention WHERE idTypeIntervention IS NOT NULL AND idVelo = '" + velo.getId() + "' ORDER BY dateIntervention DESC");

			try {
				Intervention intervention = new Intervention();
				List<String> listeId = new ArrayList<String>();

				while(res.next()) {
					String idIntervention = res.getString("idIntervention");
					listeId.add(idIntervention);
				}

				for (String idI : listeId){
					intervention = getInterventionById(idI);
					liste.add(intervention);
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

	/**
	 * @param depuisMois
	 * le nombre de mois auquels on s'interesse en jusqu'à la date actuelle. 
	 * @return La liste des nombres de vélos ayant subit chaque {@link TypeIntervention} depuis depuisMois mois.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOTypeIntervention#getAllTypesIntervention()
	 */
	public static List<Integer> getNombresVelosParTypeIntervention(int depuisMois) throws SQLException, ClassNotFoundException, ConnexionFermeeException{

		List <Integer> liste = new ArrayList<Integer>();
		try {

			java.sql.Date dateSqlTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(), depuisMois);
			java.sql.Date dateSql = UtilitaireDate.initialisationDebutMois(dateSqlTemp);

			ResultSet res = null;
			for (Integer type : DAOTypeIntervention.getAllTypesIntervention().keySet()){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				res = s.executeQuery("Select count(*) as nombreVelosTypeIntervention from Intervention WHERE idTypeIntervention = '" + type + "' and dateIntervention >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) + "','DD-MM-YYYY HH24:MI')");
				if (res.next()){
					liste.add(res.getInt("nombreVelosTypeIntervention"));
				}
				else{
					liste.add(0);
				}
			}
		}
		catch(SQLException e1){
			liste = null;
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
	 * @return La liste des interventions en attente de traitement, c'est-à-dire auquelles aucun {@link TypeIntervention} n'a 
	 * encore été assignés, concernant donc des vélos en panne au {@link Garage}
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see Velo#enPanne
	 */
	//TODO la liste des vélos en panne au garage plut™t?
	public static List<Intervention> getInterventionsNonTraitees() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Intervention> liste = new ArrayList<Intervention>();

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select * from Intervention WHERE idTypeIntervention IS NULL ORDER BY dateIntervention DESC");

			try {
				Intervention intervention = new Intervention();
				List<String> listeId = new ArrayList<String>();

				while(res.next()) {
					String idIntervention = res.getString("idIntervention");
					listeId.add(idIntervention);
				}

				for (String idI : listeId){
					intervention = getInterventionById(idI);
					liste.add(intervention);
				}
			}
			catch(SQLException e1){
				liste = null;
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
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;

	}

	
}
