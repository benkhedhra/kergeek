package gestionBaseDeDonnees;

import exception.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Compte;

public class DAOCompte {


	public static boolean createCompte(Compte compte) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = null;

			if (compte.getType() == Compte.TYPE_ADMINISTRATEUR){
				res = s.executeQuery("Select seqAdministrateur.NEXTVAL as id from dual");
				if (res.next()){
					String id = "a" + res.getString("id");
					compte.setId(id);
				}
				else{
					throw new SQLException("probleme de sequence");
				}
			}
			else if (compte.getType() == Compte.TYPE_UTILISATEUR){
				res = s.executeQuery("Select seqUtilisateur.NEXTVAL as id from dual");
				if (res.next()){
					String id = "u" + res.getString("id");
					compte.setId(id);
				}
				else{
					throw new SQLException("probleme de sequence");
				}
			}
			else if (compte.getType() == Compte.TYPE_TECHNICIEN){
				res = s.executeQuery("Select seqTechnicien.NEXTVAL as id from dual");
				if (res.next()){
					String id = "t" + res.getString("id");
					compte.setId(id);
				}
				else{
					throw new SQLException("probleme de sequence");
				}
			}
			if(compte.isActif()){
				s.executeUpdate("INSERT into Compte values (" 
						+ "'" + compte.getId() + "'," 
						+ "'" + compte.getMotDePasse() + "',"
						+ "'',"
						+ "'',"
						+ "'',"
						+ "'" + compte.getAdresseEmail() + "',"
						+ "'1',"
						+ "'',"
						+ "'" + compte.getType() + "'" 
						+ ")");
				s.executeUpdate("COMMIT");
				effectue=true;
			}
			else{
				s.executeUpdate("INSERT into Velo values (" 
						+ "'" + compte.getId() + "'," 
						+ "'" + compte.getMotDePasse() + "',"
						+ "'',"
						+ "'',"
						+ "'',"
						+ "'" + compte.getAdresseEmail() + "',"
						+ "'0',"
						+ "'',"
						+ "'" + compte.getType() + "'" 
						+ ")");
				s.executeUpdate("COMMIT");
				effectue=true;
				System.out.println("Compte ajoutee a la base de donnees");
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



	public static boolean updateCompte(Compte compte) throws SQLException, ClassNotFoundException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();
			s.executeUpdate("UPDATE Compte SET "
					+ "motDePasse = '" + compte.getMotDePasse() + "', "
					+ "adresseMail = '" + compte.getAdresseEmail() + "'"
					+ " WHERE idCompte = '"+ compte.getId() + "'"
			);
			if (compte.isActif()){
				s.executeUpdate("UPDATE Compte SET actif = 1 WHERE idCompte = '"+ compte.getId() + "'");
				s.executeUpdate("COMMIT");
				effectue=true;
			}
			else{
				s.executeUpdate("UPDATE Compte SET actif = 0 WHERE idCompte = '"+ compte.getId() + "'");
				s.executeUpdate("COMMIT");
				effectue=true;
			}
			System.out.println("Compte mis a jour dans la base de donnees");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd meme si des exceptions sont soulevees
		}
		return effectue;
	}





	public static Compte getCompteById(String identifiant) throws SQLException, ClassNotFoundException {
		Compte compte = null;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select motDePasse, actif, type, adresseMail from Compte Where idCompte ='" + identifiant + "'");
		try {
			if (res.next()) {
				compte = new Compte();
				compte.setId(identifiant);
				compte.setMotDePasse(res.getString("motDePasse"));
				compte.setType(res.getInt("type"));
				compte.setAdresseEmail(res.getString("adresseMail"));

				if (res.getBoolean("actif")){
					compte.setActif(true);
				}
				else{
					compte.setActif(false);
				}
			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'identifiant du compte");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
			compte = null;
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return compte;
	}


	public static Compte getCompteByAdresseEmail(String email) throws SQLException, ClassNotFoundException {
		Compte compte = null;

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		ResultSet res = s.executeQuery("Select idCompte from Compte Where AdresseMail ='" + email + "'");
		try {
			if (res.next()) {

				compte = getCompteById(res.getString("idCompte"));

			}
			else {
				throw new PasDansLaBaseDeDonneeException("Erreur d'adresse email");

			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return compte;
	}




	public static boolean estDansLaBdd (String id) throws SQLException, ClassNotFoundException{
		return (getCompteById(id)!=null);
	}





	public static List<Compte> getComptesByRecherche (int type, String ident, String nom, String prenom, String adresseEMail) throws ClassNotFoundException, SQLException{
		//le type est toujours renseign�, les autres peuvent valoir null
		//on va compter le nombre de param�tres non-nuls
		ArrayList<String> listeChamps = new ArrayList<String>();
		listeChamps.add(""+type);
		listeChamps.add(ident);
		listeChamps.add(nom);
		listeChamps.add(prenom);
		listeChamps.add(adresseEMail);

		int nbChampsRemplis=0;
		if (!listeChamps.get(0).equals("0")){nbChampsRemplis++;}
		for (String champ : listeChamps){
			if (champ!=null){nbChampsRemplis++;}
		}
		//du coup le premier de la liste est forc�ment non nul car il vaut une cha�ne vide
		nbChampsRemplis--;

		List<Compte> listeComptes = new ArrayList<Compte>();
		String requete = "select * from Compte where Compte.actif = 1";
		if(nbChampsRemplis>0){
			requete=requete+" where ";
			if(type!=0){
				if (!requete.equals("select * from Compte where ")){
					requete=requete+" and ";
				}
				requete=requete+" Compte.type = " + type;
			}
			if(ident!=null){
				if (!requete.equals("select * from Compte where ")){
					requete=requete+" and ";
				}
				requete=requete+"idCompte = '"+ident+"'";
			}
			if(adresseEMail!=null){
				if (!requete.equals("select * from Compte where ")){
					requete=requete+" and ";
				}
				requete=requete+" Compte.adresseMail = '" + adresseEMail + "'";
			}
			if(type==Compte.TYPE_UTILISATEUR){
				if(nom!=null){
					if (!requete.equals("select * from Compte where ")){
						requete=requete+" and ";
					}
					requete=requete+"nom = '" + nom  + "'";
				}
				if(prenom!=null){
					if (!requete.equals("select * from Compte where ")){
						requete=requete+" and ";
					}
					requete=requete+" prenom = '" + prenom + "'";
				}
			}
		}

		System.out.println("requete = "+requete);
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery(requete);

			Compte compte;
			String idCompte = "";
			ArrayList<String> listeIdComptes = new ArrayList<String>();

			while(res.next()) {
				idCompte = res.getString("idCompte");
				listeIdComptes.add(idCompte);
			}
			for(String id : listeIdComptes) {
				compte =  getCompteById(id);
				listeComptes.add(compte);
			}

		} catch(SQLException e1){
			listeComptes = null;
			System.out.println(e1.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeComptes;
	}
}