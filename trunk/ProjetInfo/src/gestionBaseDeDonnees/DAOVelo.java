package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Emprunt;
import metier.Lieu;
import metier.Velo;

public class DAOVelo {

	public static boolean createVelo(Velo velo) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res = s.executeQuery("Select seqVelo.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				velo.setId(id);
				if (velo.isEnPanne()){
					s.executeUpdate("INSERT into Velo values ('" 
							+ velo.getId() + "', '1','"+ velo.getLieu().getId() + "')");
					effectue=true;
				}
				else{
					s.executeUpdate("INSERT into Velo values ('" 
							+ velo.getId() + "', '0','"+ velo.getLieu().getId() + "')");
					effectue=true;
				}
				s.executeUpdate("COMMIT");
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

	public static boolean updateVelo(Velo velo) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Velo SET "
					+ "idVelo = '" + velo.getId() + "', "
					+ "enPanne = '" + velo.isEnPanne() + "', "
					+ "idLieu = '" + velo.getLieu().getId() + "' "
					+ "WHERE idVelo = '"+ velo.getId() + "'"
			);
			if (velo.isEnPanne()){
				s.executeUpdate("UPDATE Velo SET enPanne = '1' WHERE idVelo = '"+ velo.getId() + "'");
				effectue=true;
			}
			else{
				s.executeUpdate("UPDATE Velo SET enPanne = '0' WHERE idVelo = '"+ velo.getId() + "'");
				effectue=true;
			}
			s.executeUpdate("COMMIT");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}

	public static boolean deleteVelo(Velo velo) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("DELETE from Velo WHERE idVelo = '" + velo.getId() + "')");
			s.executeUpdate("COMMIT");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return effectue;
	}


	public static Velo getVeloById(String identifiant) throws SQLException, ClassNotFoundException {
		Velo velo = new Velo();

		ConnexionOracleViaJdbc.ouvrir();
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
			System.out.println("Pas de velo");
			velo = null;
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
			velo = null;
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return velo;
	}




	//permet d'obtenir la liste des velos parques dans un lieu
	public static ArrayList<Velo> getVelosByLieu(Lieu lieu) throws SQLException, ClassNotFoundException {
		ArrayList<Velo> listeVelos = new ArrayList<Velo>();
		Velo velo = null;

		ConnexionOracleViaJdbc.ouvrir();
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
			System.out.println("Pas de velos dans ce lieu");
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return listeVelos;
	}
	
	

	public static Emprunt EmpruntEnCours(Velo velo) throws ClassNotFoundException, SQLException{
		Emprunt emprunt = new Emprunt();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			ResultSet res= s.executeQuery("Select* from Emprunt Where dateRetour IS NULL AND idVelo = '" +velo.getId());
			if(res.next()){
				velo.setEmprunt(DAOEmprunt.getEmpruntById(res.getString("idEmprunt")));
			}
			else {
				throw new PasDansLaBaseDeDonneeException();
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("Pas d'emprunt en cours pour ce velo");
			emprunt = null;
		}
		catch (SQLException e1){
			System.out.println(e1.getMessage());
		}

		finally {
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return emprunt;
	}
	
	public static boolean estDansLaBdd (String id) throws SQLException, ClassNotFoundException{
		return (getVeloById(id)!=null);
	}

}

