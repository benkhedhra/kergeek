package gestionBaseDeDonnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.PasDansLaBaseDeDonneeException;

import metier.Compte;
import metier.DemandeAssignation;

public class DAODemandeAssignation {

	public static boolean createDemandeAssignation(DemandeAssignation ddeAssignation) throws SQLException,ClassNotFoundException{
		boolean effectue = false;
		try{

			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqDemandeAssignation.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				ddeAssignation.setId(id);

				if (ddeAssignation.isPriseEnCharge()){
					s.executeUpdate("INSERT into DemandeAssignation values ('"
							+ id + "'," 
							+ "'" + ddeAssignation.getDate() + "',"
							+ "'1','"
							+ "'" + ddeAssignation.getNombreVelosVoulusDansStation() + "',"
							+ "'" + ddeAssignation.getLieu().getId() + "'" 
							+")");
				}

				else{
					s.executeUpdate("INSERT into DemandeAssignation values ('" 
							+ id + "'," 
							+ "'" + ddeAssignation.getDate() + "',"
							+ "'0','"
							+ "'" + ddeAssignation.getNombreVelosVoulusDansStation() + "',"
							+ "'" + ddeAssignation.getLieu().getId() + "'" 
							+")");
				}

				s.executeUpdate("COMMIT");
				effectue = true;
				System.out.println("Demande d'assignation ajoutee ˆ la base de donnees");
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


	public static DemandeAssignation getDemandeAssignationById(String identifiant) throws SQLException, ClassNotFoundException {
		DemandeAssignation ddeAssignation = new DemandeAssignation();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select motDePasse, actif, type, adresseMail from Compte Where DemandeAssignation ='" + identifiant + "'");
		try {
			if (res.next()) {
				ddeAssignation.setId(identifiant);
				ddeAssignation.setDate(res.getDate("Date"));
				ddeAssignation.setNombreVelosVoulusDansStation(res.getInt("nombre"));
				ddeAssignation.setLieu(DAOLieu.getLieuById(res.getString("idLieu")));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Erreur d'identifiant");
			ddeAssignation = null;
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return ddeAssignation;
	}

}
