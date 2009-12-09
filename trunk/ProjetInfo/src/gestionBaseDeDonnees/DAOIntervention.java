package gestionBaseDeDonnees;

import exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import metier.DemandeAssignation;
import metier.Intervention;
import metier.UtilitaireDate;

public class DAOIntervention {


	public static boolean createIntervention(Intervention intervention) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				intervention.setId(id);

				s.executeUpdate("INSERT into Intervention values ("
						+ "'"+ intervention.getId() +  "'," 
						+ "'"+ intervention.getVelo().getId() + "'," 
						+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(intervention.getDate()) +"','DD-MM-YYYY HH24:MI'),"
						+ "'"+ intervention.getTypeIntervention() + "'"+ ")");
				effectue=true;
			}
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}


	public static Intervention getInterventionById(String identifiant) throws SQLException, ClassNotFoundException {
		Intervention intervention = new Intervention();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select * FROM Intervention WHERE idIntervention ='" + identifiant + "'");
		try {
			if (res.next()) {
				
				java.sql.Timestamp tempsIntervention = res.getTimestamp("dateIntervention");
				java.sql.Date dateIntervention = new java.sql.Date(tempsIntervention.getTime());
				int idTypeIntervention = res.getInt("idTypeIntervention");
				String idVelo = res.getString("idVelo");

				intervention.setId(identifiant);
				intervention.setDate(dateIntervention);
				intervention.setTypeIntervention(DAOTypeIntervention.getTypeInterventionById(idTypeIntervention));
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
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return intervention;
	}



	public static List<Integer> getNombresVelosParTypeIntervention(int depuisMois) throws SQLException, ClassNotFoundException{

		List <Integer> list = new ArrayList<Integer>();
		try {
			

			java.sql.Date dateSqlTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(), depuisMois);
			java.sql.Date dateSql = UtilitaireDate.initialisationDebutMois(dateSqlTemp);

			ResultSet res = null;
			for (Integer type : DAOTypeIntervention.getAllTypesIntervention().keySet()){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				res = s.executeQuery("Select count(*) as nombreVelosTypeIntervention from Intervention WHERE idTypeIntervention = '" + type + "' and dateIntervention >= TO_DATE('" + UtilitaireDate.conversionPourSQL(dateSql) + "','DD-MM-YYYY HH24:MI')");
				if (res.next()){
					list.add(res.getInt("nombreVelosTypeIntervention"));
				}
				else{
					list.add(0);
				}
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return list;
	}
	
	public static List<Intervention> getInterventionsNonTraitees() throws SQLException, ClassNotFoundException {
		List<Intervention> liste = new ArrayList<Intervention>();
		
		ConnexionOracleViaJdbc.ouvrir();
		
		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select idIntervention from Intervention WHERE idTypeIntervention IS NULL ORDER BY dateIntervention DESC");
		
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
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
		
	}
	
	public static String ligne(Intervention i){
		return "Intervention "+i.getClass()+" - vélo "+i.getVelo().getId();
		
	}

}
