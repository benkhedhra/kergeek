package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

				s.executeUpdate("INSERT into Intervention values ('" 
						+ intervention.getId() +  "', '" 
						+ intervention.getVelo().getId() + "', '" 
						+ "TO_DATE('" + intervention.getDate() +"','YYYY-MM-DD-HH24:MI'), '"
						+ intervention.getTypeIntervention() + "')");
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
				intervention.setId(identifiant);
				intervention.setDate(res.getDate("dateIntervention"));
				intervention.setTypeIntervention(DAOTypeIntervention.getTypeInterventionById(res.getInt("idIntervention")));
				intervention.setVelo(DAOVelo.getVeloById((res.getString("idVelo"))));
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
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			
			java.sql.Date dateSqlTemp = UtilitaireDate.retrancheMois(UtilitaireDate.dateCourante(), depuisMois);
			java.sql.Date dateSql = UtilitaireDate.initialisationDebutMois(dateSqlTemp);
			/*TODO
			 * System.out.println(dateSql.toString());
			 */
			
			ResultSet res = null;
			for (Integer type : DAOTypeIntervention.getAllTypesIntervention().keySet()){
				res = s.executeQuery("Select count(*) as nombreVelosTypeIntervention from Intervention WHERE idTypeIntervention = '" + type + "' and dateIntervention >= TO_DATE('" + dateSql + "','YYYY-MM-DD-HH24:MI')");
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
}
