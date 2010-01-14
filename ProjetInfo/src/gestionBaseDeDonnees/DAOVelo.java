package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Detruit;
import metier.Lieu;
import metier.Station;
import metier.Velo;

/**
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link Velo}.
 * @author KerGeek
 */
public class DAOVelo {

	/**
	 * Ajoute une instance de la classe {@link Velo} à la base de données.
	 * @param velo
	 *l'instance de la classe {@link Velo} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createVelo(Velo velo) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On récupère un identifiant à partir de la séquence SQL correspondante
			ResultSet res = s.executeQuery("Select seqVelo.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				//On assigne l'identifiant à l'instance qui va être ajoutée à la base de données
				velo.setId(id);
				
				//Insertion dans la base de données
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * Met à jour une instance de la classe {@link Velo} déjà présente dans la base de données.
	 * @param velo
	 * l'instance de la classe {@link Velo} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateVelo(Velo velo) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			Boolean b = false;
			//S'il existe bien déjà une ligne correspondant à cette instance dans la base données
			if (DAOVelo.getVeloById(velo.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met à jour les informations
				s.executeUpdate("UPDATE Velo SET "
						+ "idVelo = '" + velo.getId() + "', "
						+ "enPanne = '" + -b.compareTo(velo.isEnPanne()) + "', "
						+ "idLieu = '" + velo.getLieu().getId() + "'"
						+ " WHERE idVelo = '"+ velo.getId() + "'"
				);
				s.executeUpdate("COMMIT");
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
	 * Obtient un objet java Velo à partir d'une ligne de la table VELO de la base de données.
	 * @param identifiant
	 * @return  l'instance de la classe {@link Velo} dont l'identifiant correspond au paramètre.
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
				//On crée ces variables locales pour pouvoir fermer la connexion sans perdre les infos du resultset
				String idLieu = resVelo.getString("idLieu");
				Boolean enPanne = resVelo.getBoolean("enPanne");

				velo.setId(identifiant);
				velo.setLieu(DAOLieu.getLieuById(idLieu));
				velo.setEnPanne(enPanne);
				
				//Au cas où le Velo est actuellement emprunté
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return velo;
	}



	/**
	 * Permet d'obtenir la liste des velos parqués dans un Lieu.
	 * @param lieu
	 * @return la liste des {@link Velo} parqués dans le {@link Lieu} passé en paramètre
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOVelo#getVeloById(String)
	 */
	public static List<Velo> getVelosByLieu(Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		// Création de la liste des vélos parqués dans ce lieu
		List<Velo> listeVelos = new ArrayList<Velo>();

		Velo velo;
		String idVelo;

		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			// On récupère la liste des identifiants des interventions concernant ce vélo
			//(car chaque appel à la DAO getDemandeAssignationById ferme la connexion à oracle)
			List<String> listeIdVelos = new ArrayList<String>();
			ResultSet res = s.executeQuery("Select idVelo from Velo Where idLieu ='" + lieu.getId()+"'");
			while(res.next()) {
				idVelo = res.getString("idVelo");
				listeIdVelos.add(idVelo);
			}
			//ajout des vélos récupérées à la liste des vélos parqués dans ce lieu
			for(String id : listeIdVelos) {
				velo = getVeloById(id);
				listeVelos.add(velo);
			}

		} catch(SQLException e1){
			listeVelos = null;
			System.out.println("Pas de vélo dans ce lieu");
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
		return listeVelos;
	}

	/**
	 * Teste si un identifiant correspond bien à un Velo du parc.
	 * @param id
	 * @return vrai si l'identifiant entrée en paramètre correspond à un {@link Velo} non détruit présent dans la base de données,
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
	 * Teste si un identifiant correspond bien à un Velo du parc disponible en station pour être emprunté.
	 * @param id
	 * @return vrai si l'identifiant entrée en paramètre correspond à un {@link Velo} présent dans la base de données 
	 * en état de marche et dans une {@link Station}
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

