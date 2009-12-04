package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import metier.DemandeAssignation;
import metier.UtilitaireDate;

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
					s.executeUpdate("INSERT into DemandeAssignation values ("
							+ "'" + id + "', " 
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeAssignation.getDate()) +"','DD-MM-YYYY HH24:MI'), "
							+ "'1',"
							+ "'" + ddeAssignation.getNombreVelosVoulusDansStation() + "',"
							+ "'" + ddeAssignation.getLieu().getId() + "'" 
							+")");
				}

				else{
					s.executeUpdate("INSERT into DemandeAssignation values (" 
							+ "'" + id + "'," 
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeAssignation.getDate()) +"','DD-MM-YYYY HH24:MI'), "
							+ "'0',"
							+ "'" + ddeAssignation.getNombreVelosVoulusDansStation() + "',"
							+ "'" + ddeAssignation.getLieu().getId() + "'" 
							+")");
				}
				s.executeUpdate("COMMIT");
				effectue = true;
				System.out.println("Demande d'assignation ajoutee a la base de donnees");
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





	public static boolean updateDemandeAssignation(DemandeAssignation ddeAssignation) throws ClassNotFoundException, SQLException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			if (ddeAssignation.isPriseEnCharge()){
				s.executeUpdate("UPDATE DemandeAssignation SET "
						+ "dateAssignation = TO_DATE('" + ddeAssignation.getDate() +"','DD-MM-YYYY HH24:MI'), "
						+ "nombre = '" + ddeAssignation.getNombreVelosVoulusDansStation() + "',"
						+ "priseEnCharge = '1',"
						+ "idLieu = '" + ddeAssignation.getLieu().getId() + "' "
						+ "WHERE idDemandeA = '"+ ddeAssignation.getId() + "'"
				);
			}
			else{
				s.executeUpdate("UPDATE DemandeAssignation SET "
						+ "dateAssignation = + TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeAssignation.getDate()) +"''DD-MM-YYYY HH24:MI'), "
						+ "nombre = '" + ddeAssignation.getNombreVelosVoulusDansStation() + "',"
						+ "priseEnCharge = '0',"
						+ "idLieu = '" + ddeAssignation.getLieu().getId() + "' "
						+ "WHERE idDemandeA = '"+ ddeAssignation.getId() + "'"

				);
			}
			s.executeUpdate("COMMIT");
			effectue=true;
			System.out.println("Demande d'assignation mise a jour dans la base de donnees");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception	
		}
		return effectue;
	}






	public static List<DemandeAssignation> getAllDemandesAssignation() throws SQLException, ClassNotFoundException {
		List<DemandeAssignation> liste = new LinkedList<DemandeAssignation>();

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation");

		try {

			DemandeAssignation ddeAssignation = new DemandeAssignation();
			List<String> listeIdDde = new ArrayList<String>();

			while(res.next()) {
				String idDdeAssignation = res.getString("idDemandeA");
				listeIdDde.add(idDdeAssignation);
			}

			for (String idDdeA : listeIdDde){
				ddeAssignation = getDemandeAssignationById(idDdeA);
				liste.add(ddeAssignation);
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



	public static List<DemandeAssignation> getDemandesAssignationEnAttente() throws SQLException, ClassNotFoundException {
		List<DemandeAssignation> liste = new LinkedList<DemandeAssignation>();

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation WHERE prisEnCharge = '0'");

		try {
			DemandeAssignation ddeAssignation = new DemandeAssignation();
			List<String> listeIdDde = new ArrayList<String>();

			while(res.next()) {
				String idDdeAssignation = res.getString("idDemandeA");
				listeIdDde.add(idDdeAssignation);
			}

			for (String idDdeA : listeIdDde){
				ddeAssignation = getDemandeAssignationById(idDdeA);
				liste.add(ddeAssignation);
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




	public static DemandeAssignation getDemandeAssignationById(String identifiant) throws SQLException, ClassNotFoundException {
		DemandeAssignation ddeAssignation = new DemandeAssignation();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select * FROM DemandeAssignation WHERE idDemandeA ='" + identifiant + "'");
		//System.out.println(res.getString("idLieu"));
		try {
			if (res.next()) {
				
				GregorianCalendar cal = new GregorianCalendar();
				
				ddeAssignation.setId(identifiant);
				java.sql.Timestamp tempsAssignation= res.getTimestamp("dateAssignation");
				java.sql.Date date = new Date(tempsAssignation.getTime());
				int nombre = res.getInt("nombre");
				String idLieu = res.getString("idLieu");
				Boolean priseEnCharge = res.getBoolean("priseEnCharge");

				ddeAssignation.setId(identifiant);
				ddeAssignation.setDate(date);
				ddeAssignation.setNombreVelosVoulusDansStation(nombre);
				ddeAssignation.setLieu(DAOLieu.getLieuById(idLieu));
				ddeAssignation.setPriseEnCharge(priseEnCharge);

				/*TODO
				 if (res.getInt("priseEnCharge") == 1){

					ddeAssignation.setPriseEnCharge(true);
				}
				else{
					ddeAssignation.setPriseEnCharge(false);
				}*/

			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant de la demande d'Assignation");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
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


	//fonction écrite pour remplacer le toString qui n'allait effectivement pas dans métier, mais voir s'il faut encore la déplacer
	public static String ligne(DemandeAssignation d){
		String resul = "Demande "+d.getId()+" - Station "+d.getLieu().getId()+" - ";
		try {
			int diff;
			diff = d.getNombreVelosVoulusDansStation()-DAOVelo.getVelosByLieu(d.getLieu()).size();
			String type;
			if(diff<0){type = "retrait";}
			else{type = "ajout";}
			resul = resul+type+ " de "+Math.abs(diff)+" vélos - "+d.getDate().toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		return resul;
	}

}
