package gestionBaseDeDonnees;

import exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import metier.DemandeIntervention;
import metier.UtilitaireDate;

public class DAODemandeIntervention {
	public static boolean createDemandeIntervention(DemandeIntervention ddeIntervention) throws SQLException, ClassNotFoundException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqDemandeIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				ddeIntervention.setId(id);
				if (ddeIntervention.getIntervention() != null){
					s.executeUpdate("INSERT into DemandeIntervention values (" 
							+ "'" + id + "',"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(UtilitaireDate.dateCourante()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'" + ddeIntervention.getVelo().getId() + "'," 
							+ "'" + ddeIntervention.getUtilisateur().getCompte().getId() +"'," 
							+ "'" + ddeIntervention.getVelo().getLieu().getId() +"'," 
							+ "'" + ddeIntervention.getIntervention().getId()+ "')"
					);
					effectue=true;
				}
				else{
					s.executeUpdate("INSERT into DemandeIntervention values (" 
							+ "'" + id + "',"
							+ "TO_DATE('" + UtilitaireDate.conversionPourSQL(UtilitaireDate.dateCourante()) +"','DD-MM-YYYY HH24:MI'),"
							+ "'" + ddeIntervention.getVelo().getId() + "'," 
							+ "'" + ddeIntervention.getUtilisateur().getCompte().getId() +"'," 
							+ "'" + ddeIntervention.getVelo().getLieu().getId() +"'," 
							+ "'')"
					);
				}
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

	
	public static boolean updateDemandeIntervention(DemandeIntervention ddeIntervention) throws ClassNotFoundException, SQLException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			s.executeUpdate("UPDATE DemandeIntervention SET "
					+ "dateDemandeI = TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeIntervention.getDate()) +"','DD-MM-YYYY HH24:MI'), "
					+ "idVelo = '" + ddeIntervention.getVelo().getId() + "' "
					+ "idCompte = '" + ddeIntervention.getUtilisateur().getCompte().getId() + "' "
					+ "idLieu = '" + ddeIntervention.getVelo().getLieu().getId() + "' "
					+ "idIntervention = '" + ddeIntervention.getVelo().getLieu().getId() + "' "
					+ "WHERE idDemandeI = '"+ ddeIntervention.getIntervention().getId() + "'"
			);

			s.executeUpdate("COMMIT");
			effectue=true;
			System.out.println("Demande d'intervention mise a jour dans la base de données");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception	
		}
		return effectue;
	}
	

	public static List<DemandeIntervention> getDemandesInterventionEnAttente() throws SQLException, ClassNotFoundException {
		List<DemandeIntervention> liste = new LinkedList<DemandeIntervention>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select idDemandeI from DemandeIntervention WHERE idIntervention IS NULL");
		try {
			DemandeIntervention ddeIntervention = new DemandeIntervention();
			List<String> listeIdInter = new ArrayList<String>();

			while(res.next()) {
				String idDdeIntervention = res.getString("idDemandeI");
				listeIdInter.add(idDdeIntervention);
			}

			for (String idDdeI : listeIdInter){
				ddeIntervention = getDemandeInterventionById(idDdeI);
				liste.add(ddeIntervention);
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




	public static DemandeIntervention getDemandeInterventionById(String identifiant) throws SQLException, ClassNotFoundException {
		DemandeIntervention ddeIntervention = new DemandeIntervention();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select * FROM DemandeIntervention WHERE idDemandeI ='" + identifiant + "'");
		try {
			if (res.next()) {

				java.sql.Timestamp tempsDemandeI = res.getTimestamp("dateDemandeI");
				java.sql.Date dateDemandeI = new java.sql.Date(tempsDemandeI.getTime());
				String idVelo = res.getString("idVelo");
				String idCompte = res.getString("idCompte");
				String idIntervention = res.getString("idIntervention");

				ddeIntervention.setId(identifiant);
				ddeIntervention.setDate(dateDemandeI);
				ddeIntervention.setVelo(DAOVelo.getVeloById(idVelo));
				ddeIntervention.setUtilisateur(DAOUtilisateur.getUtilisateurById(idCompte));
				ddeIntervention.setIntervention(DAOIntervention.getInterventionById(idIntervention));

			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant de la demande d'Intervention");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
			ddeIntervention = null;
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return ddeIntervention;
	}


	public static String ligne(DemandeIntervention d){
		return "Demande "+d.getId()+" - Vélo "+d.getVelo().getId()+" - Station "+d.getVelo().getLieu().getId();
	}

}
