package gestionBaseDeDonnees;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import exceptions.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Intervention;
import metier.UtilitaireDate;
import metier.Velo;

public class DAOIntervention {


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
		/*TODO catch (SQLException e){
			System.out.println(e.getMessage());
		}*/
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}


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
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception	
		}
		return effectue;
	}




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
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return intervention;
	}


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
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;

	}







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
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}







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
				throw new ConnexionFermeeException();
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;

	}

	public static String ligne(Intervention i){
		return "Intervention "+i.getId()+" - vélo "+i.getVelo().getId();

	}

}
