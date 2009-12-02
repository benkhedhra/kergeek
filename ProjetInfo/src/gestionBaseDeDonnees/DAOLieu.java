package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.Station;

public class DAOLieu {

	public static Lieu getLieuById(String identifiant) throws SQLException, ClassNotFoundException {
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
			finally{
				ConnexionOracleViaJdbc.fermer();
			}
		}

		return lieu;
	}



	public static boolean createLieu(Lieu lieu) throws SQLException, ClassNotFoundException {
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
		catch (SQLException e){
		System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}


	public static List<Station> getAllStations() throws SQLException, ClassNotFoundException {
		List<Station> liste = new ArrayList<Station>();
		List<String> listeId = new ArrayList<String>();
		
		Station station = new Station();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select* from Lieu");
		try {
			boolean vide=true;
			while(res.next()) {
				vide = false;
				String idLieu = res.getString("idLieu"); 
				listeId.add(idLieu);
			}
			for(String id : listeId){
				station = (Station) DAOLieu.getLieuById(id);
				liste.add(station);
			}
			if(vide) {
				throw new SQLException("pas de station");
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




	public static int calculerTx(Station station) throws SQLException, ClassNotFoundException{
		int nbVelo = DAOVelo.getVelosByLieu(station).size();
		int a = nbVelo/station.getCapacite();
		return a;
	}




	public static List<List<Station>> getStationsSurSous() throws SQLException, ClassNotFoundException {

		List<Station> listeToutesStation = getAllStations();

		List<Station> listeStationsSurOccupees = new ArrayList<Station>();
		List<Station> listeStationsSousOccupees = new ArrayList<Station>();
		
		List<List<Station>> liste = new ArrayList<List<Station>>();

		for (Station station : listeToutesStation){
			if (calculerTx(station)>Station.TAUX_OCCUPATION_MAX){
				listeStationsSurOccupees.add(station);
			}
			else if (calculerTx(station)<Station.TAUX_OCCUPATION_MIN){
				listeStationsSousOccupees.add(station);
			}
		}
		liste.add(listeStationsSurOccupees);
		liste.add(listeStationsSousOccupees);
		
		return liste; 
	}

}
