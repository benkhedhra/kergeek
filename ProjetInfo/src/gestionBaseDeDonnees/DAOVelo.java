package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Lieu;
import metier.Velo;

public class DAOVelo {
	
	
	
	
	public static Velo getVeloById(String identifiant) throws SQLException, ClassNotFoundException {
		Velo velo = new Velo();

		ConnexionOracleViaJdbc.getC();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idVelo, idLieu, enPanne from Velo Where idVelo ='" + identifiant+"'");
		try {
			if (res.next()) {
				velo.setId(identifiant);
				velo.setLieu(DAOLieu.getLieuById(res.getString("idLieu")));
				velo.setEnPanne(res.getBoolean("enPanne"));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}


		return velo;
	}
	
	
	
	
	//permet d'obtenir la liste des velos parques dans un lieu
	public static ArrayList<Velo> getVeloByLieu(Lieu lieu) throws SQLException, ClassNotFoundException {
		ArrayList<Velo> listeVelos = new ArrayList<Velo>();
		Velo velo = null;

		ConnexionOracleViaJdbc.getC();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idVelo from Velo Where idLieu ='" + lieu.getId()+"'");
		try {
			if (res.next()) {
				velo = getVeloById(res.getString("idVelo"));
				listeVelos.add(velo);
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
		}
		return listeVelos;

	}
}

