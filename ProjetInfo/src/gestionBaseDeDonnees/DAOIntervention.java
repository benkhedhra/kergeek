package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			// On récupère un identifiant à partir de la séquence SQL correspondante
			ResultSet res = s.executeQuery("Select seqIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				//On assigne l'identifiant à l'instance qui va être ajoutée à la base de données
				intervention.setId(id);
				
				//Insertion dans la base de données
				if (intervention.getTypeIntervention() != null){
					s.executeUpdate("INSERT into Intervention values ("
							+ "'"+ intervention.getId() +  "', " 
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(intervention.getDate()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'"+ intervention.getTypeIntervention().getNumero() + "', "
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
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
			//S'il existe bien déjà une ligne correspondant à cette instance dans la base données
			if (DAOIntervention.getInterventionById(intervention.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met à jour les informations
				s.executeUpdate("UPDATE Intervention SET "
						+ "dateIntervention = TO_DATE('" + UtilitaireDate.conversionPourSQL(intervention.getDate()) +"','DD-MM-YYYY HH24:MI'), "
						+ "idTypeIntervention = '"+intervention.getTypeIntervention().getNumero()+"',"
						+ "idVelo = '" + intervention.getVelo().getId() + "' "
						+ "WHERE idIntervention = '"+ intervention.getId() + "'"
				);

				s.executeUpdate("COMMIT");
				effectue=true;
				System.out.println("Intervention mise a jour dans la base de données");
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de données, mise à jour impossible");
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
		catch(PasDansLaBaseDeDonneeException e3){
			System.out.println(e3.getMessage());
		}
		finally{
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}



	/**
	 * Obtient un objet java Intervention à partir d'une ligne de la table INTERVENTION de la base de données.
	 * @param identifiant
	 * de l'intervention recherchée
	 * @return l'instance de la classe {@link Intervention} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static Intervention getInterventionById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Intervention intervention = new Intervention();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select * FROM Intervention WHERE idIntervention ='" + identifiant + "'");
			try {
				if (res.next()) {

					//On récupère un Timestamp
					java.sql.Timestamp tempsIntervention = res.getTimestamp("dateIntervention");
					//Transformation du Timestamp en date
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
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
		// Création de la liste des interventions concernant ce vélo
		List<Intervention> liste = new ArrayList<Intervention>();
		try{
			ConnexionOracleViaJdbc.ouvrir();

			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select * from Intervention WHERE idTypeIntervention IS NOT NULL AND idVelo = '" + velo.getId() + "' ORDER BY dateIntervention DESC");
			try {
				// On récupère la liste des identifiants des interventions concernant ce vélo
				//(car chaque appel à la DAO getDemandeAssignationById ferme la connexion à oracle)
				List<String> listeId = new ArrayList<String>();
				Intervention intervention = new Intervention();
				while(res.next()) {
					String idIntervention = res.getString("idIntervention");
					listeId.add(idIntervention);
				}
				
				//ajout des Interventions récupérées à la liste des interventions concernant ce vélo
				for (String idI : listeId){
					//récupération de l'instance correspondant à l'identifiant
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;

	}

	/**
	 * @param depuisMois
	 * le nombre de mois auquels on s'interesse en jusqu'à la date actuelle. 
	 * @return La liste des listes des nombres de vélos ayant subit chaque {@link TypeIntervention}
	 * depuis depuisMois mois pour chaque mois.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOTypeIntervention#getAllTypesIntervention()
	 */
	public static List <List <Integer>> getNombresVelosParTypeIntervention(int depuisMois) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		// Création de la liste des nombres d'interventions par TypeIntervention
		// chaque élément de la liste correspondant au nombre d'interventions pour un TypeIntervention
		List <List <Integer>> liste = new ArrayList<List<Integer>>();
		try {

			java.sql.Date dateSqlTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(), depuisMois);
			java.sql.Date dateSql = UtilitaireDate.initialisationDebutMois(dateSqlTemp);

			ResultSet res = null;
			// On fait l'inventaire de tout les TypeIntervention
			Map<Integer, String> m = DAOTypeIntervention.getAllTypesIntervention();
			for (Integer type : m.keySet()){//Pour chaque TypeIntervention
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On compte le nombre d'intervention pour ce TypeIntervention
				res = s.executeQuery("Select count(*) as nombreVelosTypeIntervention from Intervention WHERE idTypeIntervention = '" + type + "' and dateIntervention >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) + "','DD-MM-YYYY HH24:MI')");
				if (res.next()){
					List <Integer> listeTypeNombre = new ArrayList<Integer>();
					listeTypeNombre.add(type);
					listeTypeNombre.add(res.getInt("nombreVelosTypeIntervention"));
					liste.add(listeTypeNombre);
				}
				//S'il n'y a pas d'intervention pour ce TypeIntervention
				else{
					List <Integer> listeTypeNombre = new ArrayList<Integer>();
					listeTypeNombre.add(type);
					listeTypeNombre.add(0);
					liste.add(listeTypeNombre);
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
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
	//On ne fait pas la liste des vélos en panne au garage 
	//car on n'aurait pas accès à la date dans ce cas là
	public static List<Intervention> getInterventionsNonTraitees() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		// La liste des Interventions non traitées
		List<Intervention> liste = new ArrayList<Intervention>();
		try{
			ConnexionOracleViaJdbc.ouvrir();

			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On récupère la liste des identifiants des Intervertion non prises en charges
			//c'est-à-dire auquelles aucun TypeIntervention n'est associée
			//(car chaque appel à la DAO getDemandeAssignationById ferme la connexion à oracle)
			ResultSet res = s.executeQuery("Select * from Intervention WHERE idTypeIntervention IS NULL ORDER BY dateIntervention DESC");

			try {
				Intervention intervention = new Intervention();
				List<String> listeId = new ArrayList<String>();

				while(res.next()) {
					String idIntervention = res.getString("idIntervention");
					listeId.add(idIntervention);
				}
				
				//ajout des Intervertion non traitées récupérée à la liste des interventions non traitées
				for (String idI : listeId){
					//récupération de l'instance correspondant à l'identifiant
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;

	}


}
