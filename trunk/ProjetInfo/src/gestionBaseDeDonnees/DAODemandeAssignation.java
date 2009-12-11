package gestionBaseDeDonnees;

import exceptionsTechniques.ConnexionFermeeException;
import exceptionsTechniques.PasDansLaBaseDeDonneeException;

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
import metier.Velo;

public class DAODemandeAssignation {

	public static boolean createDemandeAssignation(DemandeAssignation ddeAssignation) throws SQLException,ClassNotFoundException, ConnexionFermeeException{
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
							+ "'" + ddeAssignation.getNombreVelosVoulusDansLieu() + "',"
							+ "'" + ddeAssignation.getLieu().getId() + "'" 
							+")");
				}

				else{
					s.executeUpdate("INSERT into DemandeAssignation values (" 
							+ "'" + id + "'," 
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeAssignation.getDate()) +"','DD-MM-YYYY HH24:MI'), "
							+ "'0',"
							+ "'" + ddeAssignation.getNombreVelosVoulusDansLieu() + "',"
							+ "'" + ddeAssignation.getLieu().getId() + "'" 
							+")");
				}
				s.executeUpdate("COMMIT");
				effectue = true;
				System.out.println("Demande d'assignation ajoutee a la base de donnees");
			}
		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}





	public static boolean updateDemandeAssignation(DemandeAssignation ddeAssignation) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			if (ddeAssignation.isPriseEnCharge()){
				s.executeUpdate("UPDATE DemandeAssignation SET "
						+ "dateAssignation = TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeAssignation.getDate()) +"','DD-MM-YYYY HH24:MI'), "
						+ "nombre = '" + ddeAssignation.getNombreVelosVoulusDansLieu() + "',"
						+ "priseEnCharge = '1',"
						+ "idLieu = '" + ddeAssignation.getLieu().getId() + "' "
						+ "WHERE idDemandeA = '"+ ddeAssignation.getId() + "'"
				);
			}
			else{
				s.executeUpdate("UPDATE DemandeAssignation SET "
						+ "dateAssignation = + TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeAssignation.getDate()) +"','DD-MM-YYYY HH24:MI'), "
						+ "nombre = '" + ddeAssignation.getNombreVelosVoulusDansLieu() + "',"
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
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception	
		}
		return effectue;
	}






	public static List<DemandeAssignation> getAllDemandesAssignation() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<DemandeAssignation> liste = new ArrayList<DemandeAssignation>();

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();

		try {
			ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation WHERE priseEnCharge = '0' ");



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
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return liste;
	}



	public static List<DemandeAssignation> getDemandesAssignationEnAttente() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<DemandeAssignation> liste = new LinkedList<DemandeAssignation>();

		ConnexionOracleViaJdbc.ouvrir();

		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation WHERE priseEnCharge = '0'");

			try {
				DemandeAssignation ddeAssignation = new DemandeAssignation();
				List<String> listeIdDde = new ArrayList<String>();
				int diff;

				while(res.next()) {
					String idDdeAssignation = res.getString("idDemandeA");
					listeIdDde.add(idDdeAssignation);
				}

				for (String idDdeA : listeIdDde){
					ddeAssignation = getDemandeAssignationById(idDdeA);
					diff = ddeAssignation.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(ddeAssignation.getLieu()).size();
					if (diff==0){
						ddeAssignation.setPriseEnCharge(true);
						updateDemandeAssignation(ddeAssignation);
					}
					else{
						liste.add(ddeAssignation);
					}
				}
			}
			catch(SQLException e1){
				liste = null;
				System.out.println(e1.getMessage());
			}
		}
		catch(NullPointerException e2){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}




	public static DemandeAssignation getDemandeAssignationById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		DemandeAssignation ddeAssignation = new DemandeAssignation();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
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
					ddeAssignation.setNombreVelosVoulusDansLieu(nombre);
					ddeAssignation.setLieu(DAOLieu.getLieuById(idLieu));
					ddeAssignation.setPriseEnCharge(priseEnCharge);

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
		}
		catch(NullPointerException e3){
			throw new ConnexionFermeeException();
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return ddeAssignation;
	}

	public static int getDiff(DemandeAssignation d) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Velo> velos = DAOVelo.getVelosByLieu(d.getLieu());
		return velos.size()-d.getNombreVelosVoulusDansLieu();
	}


	public static String ligne(DemandeAssignation d) throws ConnexionFermeeException{
		String resul = "Demande "+d.getId()+" - "+d.getLieu().getAdresse()+" - ";
		try {
			int diff;
			diff = d.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(d.getLieu()).size();
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
