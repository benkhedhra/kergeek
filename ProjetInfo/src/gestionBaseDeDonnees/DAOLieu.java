package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.Station;

/**
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link Lieu}
 * et ses classes filles.
 * @author KerGeek
 */
public class DAOLieu {
	
	/**
	 * Ajoute une instance de {@link Lieu} � la base de donn�es.
	 * @param lieu
	 * l'instance de {@link Lieu} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
	 *  faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createLieu(Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			if (lieu.getId() == Lieu.ID_GARAGE){
				s.executeUpdate("INSERT into Lieu values ('" 
						+ Garage.ID_GARAGE + "', '" 
						+ Garage.ADRESSE_GARAGE + "', '" 
						+ Garage.CAPACITE_GARAGE+ "')"
				);
				effectue=true;
			}
			else if (lieu.getId() == Lieu.ID_SORTIE){
				s.executeUpdate("INSERT into Lieu values (" 
						+ "'" + Lieu.ID_SORTIE + "'," 
						+ "'" + Lieu.ADRESSE_SORTIE + "'," 
						+ "''" + ")"
				);
				effectue=true;
			}
			else if (lieu.getId() == Lieu.ID_DETRUIT){
				s.executeUpdate("INSERT into Lieu values (" 
						+ "'" + Lieu.ID_DETRUIT + "'," 
						+ "'" + Lieu.ADRESSE_DETRUIT + "'," 
						+ "''" + ")"
				);
				effectue=true;
			}
			else{
				ResultSet res = s.executeQuery("Select seqLieu.NEXTVAL as id from dual");
				if (res.next()){
					String id = res.getString("id");
					lieu.setId(id);
				}
				else{
					throw new SQLException("probleme de sequence");
				}
				s.executeUpdate("INSERT into Lieu values ('" 
						+ lieu.getId() + "', '" 
						+ lieu.getAdresse() + "', '" 
						+ lieu.getCapacite() 
						+ "')");
				s.executeUpdate("COMMIT");
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return effectue;
	}

	/**
	 * @param identifiant
	 * @return l'instance de {@link Lieu} dont l'identifiant correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static Lieu getLieuById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Lieu lieu;
	
		if (identifiant == Lieu.ID_GARAGE){ // c'est LE garage (unique)
			lieu = (Garage) Garage.getInstance();
		}
	
		else if (identifiant == Lieu.ID_SORTIE){ // pas de lieu
			lieu = (Sortie) Sortie.getInstance();
		}
	
		else{// alors il ne peut s'agir que d'une station
	
			lieu = (Station) new Station();
	
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			try{
				ResultSet res = s.executeQuery("Select adresseLieu, capacite from Lieu Where idLieu ='" + identifiant +"'");
				try {
					if (res.next()) {
						lieu.setId(identifiant);
						lieu.setCapacite(res.getInt("capacite"));
						lieu.setAdresse(res.getString("adresseLieu"));
					}
					else {
						throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant du Lieu");
					}
				}
				catch(PasDansLaBaseDeDonneeException e1){
					System.out.println(e1.getMessage());
					lieu = null;
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
		}
	
		return lieu;
	}

	/**
	 * @return la liste de l'ensemble des {@link Stations} pr�sentes dans la base de donn�es auquel s'ajoute le {@link Garage}.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOLieu#getLieuById(String)
	 */
	public static List<Lieu> getStationsEtGarage() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Lieu> listeLieus = new ArrayList<Lieu>();
		List<String> listeId = new ArrayList<String>();
	
		Lieu lieu;
	
		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select* from Lieu WHERE idLieu <> '" + Lieu.ID_SORTIE + "' AND idLieu <> '" + Lieu.ID_DETRUIT + "'");
			try {
				while(res.next()) {
					String idLieu = res.getString("idLieu"); 
					listeId.add(idLieu);
				}
				for(String id : listeId){
					lieu = DAOLieu.getLieuById(id);
					listeLieus.add(lieu);
				}
			}
			catch(SQLException e1){
				listeLieus = null;
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
	
		return listeLieus;
	}

	/**
	 * @return la liste de l'ensemble des {@link Stations} pr�sentes dans la base de donn�es.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOLieu#getStationsEtGarage()
	 */
	public static List<Station> getAllStations() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Station> listeStations = new ArrayList<Station>();
		List<Lieu> listeLieus = getStationsEtGarage();
		for(Lieu lieu : listeLieus){
			if (!lieu.getId().equals(Lieu.ID_GARAGE)){
				listeStations.add((Station) lieu);
			}
		}
		return listeStations;
	}
	
	/**
	 * @param station
	 * @return le taux d'occupation de la station pass�e en param�tre
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOVelo#getVelosByLieu(Station)
	 * @see Station#getCapacite()
	 */
	public static float calculerTx(Station station) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		float nbVelo = DAOVelo.getVelosByLieu(station).size();
		return  nbVelo/station.getCapacite();
	}

	/**
	 * @return Une liste � deux �l�ments : 
	 * le premier est la liste des {@link Station} dont le taux d'occupation est sup�rieur � {@link Station#TAUX_OCCUPATION_MAX},
	 * le second est la liste des {@link Station} dont le taux d'occupation est inf�rieur � {@link Station#TAUX_OCCUPATION_MIN}
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOLieu#getAllStations()
	 * @see DAOLieu#calculerTx(Station)
	 */
	public static List<List<Station>> getStationsSurSous() throws SQLException, ClassNotFoundException, ConnexionFermeeException {

		List<Station> listeToutesStations = getAllStations();

		List<Station> listeStationsSurOccupees = new ArrayList<Station>();
		List<Station> listeStationsSousOccupees = new ArrayList<Station>();

		List<List<Station>> liste = new ArrayList<List<Station>>();

		float taux = 0;

		for (Station station : listeToutesStations){
			taux = calculerTx(station);
			if (taux>Station.TAUX_OCCUPATION_MAX){
				listeStationsSurOccupees.add(station);
			}
			else if (taux<Station.TAUX_OCCUPATION_MIN){
				listeStationsSousOccupees.add(station);
			}
		}
		liste.add(listeStationsSurOccupees);
		liste.add(listeStationsSousOccupees);

		return liste; 
	}

	/**
	 * Une fonction qui sert � l'affichage d'une station sur-occup�e.
	 * @param s
	 * la station sur-occup�e � afficher
	 * @return Une cha�ne de caract�res pr�sentant la station et sp�cifiant que cette-derni�re est sur-occup�e
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @see Lieu#toString()
	 */
	public static String ligneStationSur(Station s) throws SQLException, ClassNotFoundException{
		String resul = s.toString()+ " - sur-occup�e";
		return resul;
	}
	
	/**
	 * Une fonction qui sert � l'affichage d'une station sous-occup�e.
	 * @param s
	 * la station sous-occup�e � afficher
	 * @return Une cha�ne de caract�res pr�sentant la station et sp�cifiant que cette-derni�re est sous-occup�e
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @see Lieu#toString()
	 */
	public static String ligneStationSous(Station s) throws SQLException, ClassNotFoundException{
		String resul = s.toString()+ " - sous-occup�e";
		return resul;
	}

}
