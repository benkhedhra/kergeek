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
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link TypeIntervention}.
 * @author KerGeek
 */
public class DAOTypeIntervention {

	/**
	 * Ajoute une instance de la classe {@link DemandeAssignationTypeIntervention} � la base de donn�es.
	 * @param typeIntervention
	 * l'instance de la classe {@link TypeIntervention} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * Met � jour une instance de la classe {@link TypeIntervention} d�j� pr�sente dans la base de donn�es.
	 * @param typeIntervention
	 * l'instance de la classe {@link TypeIntervention} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateTypeIntervention(TypeIntervention typeIntervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			//S'il existe bien d�j� une ligne correspondant � cette instance dans la base donn�es
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
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de donn�es, mise � jour impossible");
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}


	/**
	 * @param type
	 * @return  l'instance de la classe {@link TypeIntervention} dont le type correspond au param�tre.
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return typeIntervention;
	}

	/**
	 * @return la carte de l'ensemble des {@link TypeIntervention} pr�sents dans la base de donn�es.
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return typesIntervention;
	}

	/*
	/**
	 * @return la liste de l'ensemble des {@link TypeIntervention} pr�sents dans la base de donn�es.
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return liste;
	}*/

}
