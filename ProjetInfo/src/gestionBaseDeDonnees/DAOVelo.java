package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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
			Boolean b = false;
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Velo SET "
					+ "idVelo = '" + velo.getId() + "', "
					+ "enPanne = '" + -b.compareTo(velo.isEnPanne()) + "', "
					+ "idLieu = '" + velo.getLieu().getId() + "' "
					+ "WHERE idVelo = '"+ velo.getId() + "'"
			);
			s.executeUpdate("COMMIT");
			effectue=true;

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
			s.executeUpdate("DELETE from Velo WHERE idVelo = '" + velo.getId() + "'");
			s.executeUpdate("COMMIT");
			effectue = true;
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

		ResultSet resVelo = s.executeQuery("Select idLieu, enPanne from Velo Where idVelo ='" + identifiant+"'");
		try {
			if (resVelo.next()) {
				//On cr�e ces variables locales car on ne peut pas avoir 2 ResultSet en meme temps...
				String idLieu = resVelo.getString("idLieu");
				Boolean enPanne = resVelo.getBoolean("enPanne");

				velo.setId(identifiant);
				velo.setLieu(DAOLieu.getLieuById(idLieu));
				velo.setEnPanne(enPanne);
				velo.setEmpruntEnCours(DAOEmprunt.getEmpruntEnCoursByVelo(identifiant));
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant du velo");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println("PasDansLaBaseDeDonneeException  = "+e1.getMessage());
			velo = null;
		}
		catch (SQLException e2){
			System.out.println("SQLException  = "+e2.getMessage());
			velo = null;
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return velo;
	}




	//permet d'obtenir la liste des velos parques dans un lieu
	public static List<Velo> getVelosByLieu(Lieu lieu) throws SQLException, ClassNotFoundException {
		List<Velo> listeVelos = new LinkedList<Velo>();
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
				throw new PasDansLaBaseDeDonneeException("Pas de velo dans ce lieu");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si la requete sql souleve une exception
		}
		return listeVelos;
	}



	public static boolean estDansLaBdd (String id) throws SQLException, ClassNotFoundException{
		return (getVeloById(id)!=null);
	}

}

