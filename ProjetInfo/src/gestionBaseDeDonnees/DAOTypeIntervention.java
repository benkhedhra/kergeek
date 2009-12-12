package gestionBaseDeDonnees;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import exceptions.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import metier.TypeIntervention;

public class DAOTypeIntervention {


	public static boolean createTypeIntervention(TypeIntervention typeIntervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqTypeIntervention.NEXTVAL as type from dual");
			if (res.next()){
				int type = res.getInt("type");
				typeIntervention.setType(type);

				s.executeUpdate("INSERT into TypeIntervention values ('" 
						+ typeIntervention.getType() +  "', '" 
						+ typeIntervention.getDescription()
						+ "')");
				effectue=true;
			}
		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}



	public static boolean updateTypeIntervention(TypeIntervention typeIntervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE TypeIntervention SET "
					+"descritpion = '" + typeIntervention.getDescription() +  "'" 
					+ " WHERE idTypeIntervention = '" +typeIntervention.getType() + "'"
			);
			effectue=true;
		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}



	public static TypeIntervention getTypeInterventionById(int type) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		TypeIntervention typeIntervention = new TypeIntervention();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select * FROM TypeIntervention WHERE idTypeIntervention ='" + type + "'");
			try {
				if (res.next()) {
					typeIntervention.setType(type);
					typeIntervention.setDescription(res.getString("description"));
				}
				else {
					throw new PasDansLaBaseDeDonneeException("Erreur du type d'intervention");
				}
			}
			catch(PasDansLaBaseDeDonneeException e1){
				System.out.println(e1.getMessage());
				typeIntervention = null;
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
		return typeIntervention;
	}




	public static Map<Integer,String> getAllTypesIntervention() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Map<Integer,String> typesIntervention = new HashMap<Integer,String>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		try {
			ResultSet res = s.executeQuery("Select* from TypeIntervention");
			boolean vide = true;
			while (res.next()) {
				vide = false;
				typesIntervention.put((res.getInt("idTypeIntervention")),(res.getString("description")));
			}
			if(vide){
				throw new SQLException("pas de type d'intervention reference");
			}
		}
		catch(SQLException e1){
			typesIntervention = null;
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return typesIntervention;
	}

	/*public static List<TypeIntervention> getAllTypesIntervention() throws SQLException, ClassNotFoundException{

		List<TypeIntervention> liste = new LinkedList<TypeIntervention>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
		ResultSet res = s.executeQuery("Select* from TypeIntervention");
		try {
			TypeIntervention typeIntervention = new TypeIntervention();
			List<Integer> listeTypesInter = new ArrayList<Integer>();

			while(res.next()) {
				int idTypeIntervention = res.getInt("idTypeIntervention");
				listeTypesInter.add(idTypeIntervention);
			}
			for (int idTdeI : listeTypesInter){
				typeIntervention = getTypeInterventionById(idTdeI);
				liste.add(typeIntervention);
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
	}*/

}
