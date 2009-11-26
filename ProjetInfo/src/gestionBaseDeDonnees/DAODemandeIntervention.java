package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				
				s.executeUpdate("INSERT into DemandeIntervention values ('" 
						+ id + "', '"+ UtilitaireDate.dateCourante() + "', '" 
						+ ddeIntervention.getVelo().getId() + "', '" 
						+ ddeIntervention.getUtilisateur().getCompte().getId() +"', '" 
						+ ddeIntervention.getVelo().getLieu().getId() + "')"
						);
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
	

	public static List<DemandeIntervention> getDemandesInterventionEnAttente() throws SQLException, ClassNotFoundException {
		List<DemandeIntervention> liste = new LinkedList<DemandeIntervention>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select* from DemandeIntervention WHERE idIntervention IS NOT NULL");
		try {
			boolean vide=true;
			while(res.next()) {
				vide = false;
				DemandeIntervention ddeIntervention = getDemandeInterventionById(res.getString("idDemandeI"));
				liste.add(ddeIntervention);
			}
			if(vide) {
				throw new SQLException("pas de demandes en attente");
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
				ddeIntervention.setId(identifiant);
				ddeIntervention.setDate(res.getDate("dateDemandeI"));
				ddeIntervention.setVelo(DAOVelo.getVeloById((res.getString("idVelo"))));
				ddeIntervention.setUtilisateur(DAOUtilisateur.getUtilisateurById(res.getString("idCompte")));
				ddeIntervention.setIntervention(DAOIntervention.getInterventionById(res.getString("idIntervention")));
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
}
