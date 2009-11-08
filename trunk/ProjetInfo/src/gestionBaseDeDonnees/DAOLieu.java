package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Lieu;
import metier.Station;
import metier.Garage;
import exception.PasDansLaBaseDeDonneeException;

public class DAOLieu {

	public static Lieu getLieuById(String identifiant) throws SQLException, ClassNotFoundException {
		Lieu lieu;
		
		if (identifiant == Garage.ID_GARAGE){ // c'est LE garage (unique)
			lieu = (Garage) Garage.getInstance();
		}

		else{// alors il ne peut s'agir que d'une station

			lieu = (Station) new Station();

			ConnexionOracleViaJdbc.getC();
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
		}

		return lieu;
	}

}
