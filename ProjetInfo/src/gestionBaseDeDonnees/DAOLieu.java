package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Garage;
import metier.Lieu;
import metier.Station;

public class DAOLieu {

	public static Lieu getLieuById(String identifiant) throws SQLException, ClassNotFoundException {
		Lieu lieu;
		
		if (identifiant == Garage.ID_GARAGE){ // c'est LE garage (unique)
			lieu = (Garage) Garage.getInstance();
		}

		else{// alors il ne peut s'agir que d'une station

			lieu = (Station) new Station();

			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select adresse, capacite from Lieu Where idLieu ='" + identifiant+"'");
			try {
				if (res.next()) {
					lieu.setId(identifiant);
					lieu.setCapacite(res.getInt("capacite"));
				}
				else {
					throw new PasDansLaBaseDeDonneeException();
				}
			}
			catch(PasDansLaBaseDeDonneeException e1){
				System.out.println("Erreur d'identifiant");
				lieu = null;
			}
			finally{
				ConnexionOracleViaJdbc.fermer();
			}
		}

		return lieu;
	}
	
	
	public static List<Station> getAllStation() throws SQLException, ClassNotFoundException {
		ArrayList<Station> liste = new ArrayList<Station>();

			Station station = (Station) new Station();

			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select* from Lieu");
			try {
				if (res.next()) {
					station = (Station) DAOLieu.getLieuById(res.getString("idLieu"));
					liste.add(station);
				}
				else {
					throw new SQLException("pas de station");
				}
			}
			catch(SQLException e1){
				liste = null;
				ConnexionOracleViaJdbc.fermer();
			}
			finally{
				ConnexionOracleViaJdbc.fermer();
			}

		return liste;
	}


}
