package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import metier.TypeIntervention;

/**
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link TypeIntervention}.
 * @author KerGeek
 */
public class DAOTypeIntervention {

	/**
	 * Ajoute une instance de la classe {@link DemandeAssignationTypeIntervention} à la base de données.
	 * @param typeIntervention
	 * l'instance de la classe {@link TypeIntervention} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createTypeIntervention(TypeIntervention typeIntervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select seqTypeIntervention.NEXTVAL as type from dual");
			if (res.next()){
				int type = res.getInt("type");
				typeIntervention.setNumero(type);

				s.executeUpdate("INSERT into TypeIntervention values ('" 
						+ typeIntervention.getNumero() +  "', '" 
						+ typeIntervention.getDescription()
						+ "')");
				effectue=true;
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * Met à jour une instance de la classe {@link TypeIntervention} déjà présente dans la base de données.
	 * @param typeIntervention
	 * l'instance de la classe {@link TypeIntervention} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateTypeIntervention(TypeIntervention typeIntervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			//S'il existe bien déjà une ligne correspondant à cette instance dans la base données
			if (DAOTypeIntervention.getTypeInterventionById(typeIntervention.getNumero()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				s.executeUpdate("UPDATE TypeIntervention SET "
						+"descritpion = '" + typeIntervention.getDescription() +  "'" 
						+ " WHERE idTypeIntervention = '" +typeIntervention.getNumero() + "'"
				);
				effectue=true;
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de données, mise à jour impossible");
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
	 * @param type
	 * @return  l'instance de la classe {@link TypeIntervention} dont le type correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static TypeIntervention getTypeInterventionById(int type) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		TypeIntervention typeIntervention = new TypeIntervention();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select * FROM TypeIntervention WHERE idTypeIntervention ='" + type + "'");
			try {
				if (res.next()) {
					typeIntervention.setNumero(type);
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
		return typeIntervention;
	}

	/**
	 * @return la carte de l'ensemble des {@link TypeIntervention} présents dans la base de données.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static Map<Integer,String> getAllTypesIntervention() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Map<Integer,String> typesIntervention = new HashMap<Integer,String>();
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();


			ResultSet res = s.executeQuery("Select* from TypeIntervention");
			while (res.next()) {
				typesIntervention.put((res.getInt("idTypeIntervention")),(res.getString("description")));
			}
		}
		catch(SQLException e1){
			typesIntervention = null;
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
		return typesIntervention;
	}

	/*
	/**
	 * @return la liste de l'ensemble des {@link TypeIntervention} présents dans la base de données.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @see DAOTypeIntervention#getTypeInterventionById(int) 
	 *
	public static List<TypeIntervention> getAllTypesIntervention() throws SQLException, ClassNotFoundException{

		List<TypeIntervention> liste = new LinkedList<TypeIntervention>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
		ResultSet res = s.executeQuery("Select* from TypeIntervention");
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
	}*/

}
