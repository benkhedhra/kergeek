package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import metier.Detruit;
import metier.Lieu;
import metier.Station;
import metier.Velo;

/**
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link Velo}.
 * @author KerGeek
 */
public class DAOVelo {
	
	/**
	 * Ajoute une instance de la classe {@link Velo} � la base de donn�es.
	 * @param velo
	 *l'instance de la classe {@link Velo} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createVelo(Velo velo) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqVelo.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				velo.setId(id);
				if (velo.isEnPanne()){
					s.executeUpdate("INSERT into Velo values ('" 
							+ velo.getId() + "', '1','"+ velo.getLieu().getId() + "')");
					effectue=true;
				}
				else{
					s.executeUpdate("INSERT into Velo values ('" 
							+ velo.getId() + "', '0','"+ velo.getLieu().getId() + "')");
					effectue=true;
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return effectue;
	}

	/**
	 *  Met � jour une instance de la classe {@link Velo} d�j� pr�sente dans la base de donn�es.
	 * @param velo
	 * l'instance de la classe {@link Velo} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateVelo(Velo velo) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			Boolean b = false;
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Velo SET "
					+ "idVelo = '" + velo.getId() + "', "
					+ "enPanne = '" + -b.compareTo(velo.isEnPanne()) + "', "
					+ "idLieu = '" + velo.getLieu().getId() + "'"
					+ " WHERE idVelo = '"+ velo.getId() + "'"
			);
			s.executeUpdate("COMMIT");
			effectue=true;

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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return effectue;
	}

	/**
	 * @param identifiant
	 * @return  l'instance de la classe {@link Velo} dont l'identifiant correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOEmprunt#setEmpruntEnCoursByVelo(Velo)
	 */
	public static Velo getVeloById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Velo velo = new Velo();

		try {
		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet resVelo = s.executeQuery("Select idLieu, enPanne from Velo Where idVelo ='" + identifiant+"'");
		
			if (resVelo.next()) {
				//On cr�e ces variables locales pour pouvoir fermer la connexion sans perdre les infos du resultset
				String idLieu = resVelo.getString("idLieu");
				Boolean enPanne = resVelo.getBoolean("enPanne");

				velo.setId(identifiant);
				velo.setLieu(DAOLieu.getLieuById(idLieu));
				velo.setEnPanne(enPanne);
				DAOEmprunt.setEmpruntEnCoursByVelo(velo);
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant du velo");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("PasDansLaBaseDeDonneeException  = "+e1.getMessage());
			velo = null;
		}
		catch (SQLException e2){
			System.out.println("SQLException  = "+e2.getMessage());
			velo = null;
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return velo;
	}



	/**
	 * Permet d'obtenir la liste des velos parqu�s dans un Lieu.
	 * @param lieu
	 * @return la liste des {@link Velo} parqu�s dans le {@link Lieu} pass� en param�tre
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOVelo#getVeloById(String)
	 */
	public static List<Velo> getVelosByLieu(Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		
		List<String> listeIdVelos = new ArrayList<String>();
		List<Velo> listeVelos = new LinkedList<Velo>();

		Velo velo;
		String idVelo;

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();

		try {

			ResultSet res = s.executeQuery("Select idVelo from Velo Where idLieu ='" + lieu.getId()+"'");

			while(res.next()) {
				idVelo = res.getString("idVelo");
				listeIdVelos.add(idVelo);
			}

			for(String id : listeIdVelos) {
				velo = getVeloById(id);
				listeVelos.add(velo);
			}

		} catch(SQLException e1){
			listeVelos = null;
			System.out.println("Pas de v�lo dans ce lieu");
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return listeVelos;
	}
	
	/**
	 * Teste si un identifiant correspond bien � un Velo du parc.
	 * @param id
	 * @return vrai si l'identifiant entr�e en param�tre correspond � un {@link Velo} non d�truit pr�sent dans la base de donn�es,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see {@link Detruit}
	 */
	public static boolean existe(String id) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Velo velo = getVeloById(id);
		return (velo!=null && !velo.getLieu().getId().equals(Lieu.ID_DETRUIT));
	}
	
	/**
	 *  Teste si un identifiant correspond bien � un Velo du parc disponible en station pour �tre emprunt�.
	 * @param id
	 * @return vrai si l'identifiant entr�e en param�tre correspond � un {@link Velo} pr�sent dans la base de donn�es 
	 * en �tat de marche et dans une {@link Station}
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean estDisponible (String id) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Velo velo = getVeloById(id);
		return (velo!=null && !velo.getLieu().getId().equals(Lieu.ID_GARAGE) && !velo.getLieu().getId().equals(Lieu.ID_SORTIE) && !velo.isEnPanne());
	}

}

