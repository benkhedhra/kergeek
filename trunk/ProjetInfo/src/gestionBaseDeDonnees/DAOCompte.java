package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Compte;
import metier.Utilisateur;

/**
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link Compte}.
 * @author KerGeek
 */
public class DAOCompte {

	//TODO propager les exceptions PasDansLaBaseDeDonneesException
	
	/**
	 * Ajoute une instance de la classe {@link Compte} � la base de donn�es.
	 * C'est au cours de cette action que les identifiants sont g�n�r�s � l'aide de s�quences SQL. 
	 * Il existe une s�quence par type de compte et un identifiant commence toujours par la lettre correspondant au type de 
	 * compte en question ('a' pour Administrateur, 't' pour Technicien et 'u' pour Utilisateur).
	 * <br> <br> Une fois l'identifiant attribuer � l'instance, on ins�re les valeurs des attributs de celles-ci dans la table COMPTE
	 * o� une nouvelle ligne est cr��e.
	 * @param compte
	 * l'instance de la classe {@link Compte} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see CreationTables
	 */
	public static boolean createCompte(Compte compte) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = null;

			if (compte.getType() == Compte.TYPE_ADMINISTRATEUR){
				res = s.executeQuery("Select seqAdministrateur.NEXTVAL as id from dual");
				if (res.next()){
					String nb = res.getString("id");
					System.out.println(nb);
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
					String nb = res.getString("id");
					System.out.println(nb);
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
					String nb = res.getString("id");
					System.out.println(nb);
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
		catch(NullPointerException e2){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e2.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return effectue;
	}


	/**
	 * Met � jour une instance de la classe {@link Compte} d�j� pr�sente dans la base de donn�es.
	 * @param compte
	 * l'instance de la classe {@link Compte} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see CreationTables
	 */
	public static boolean updateCompte(Compte compte) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
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
		catch(NullPointerException e2){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e2.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return effectue;
	}




	/**
	 * @param identifiant
	 * du compte recherch�
	 * @return l'instance de la classe {@link Compte} dont l'identifiant correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see CreationTables
	 */
	public static Compte getCompteById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Compte compte = null;

		ConnexionOracleViaJdbc.ouvrir();
		try {
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select motDePasse, actif, type, adresseMail from Compte Where idCompte ='" + identifiant + "'");
			try{
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
		}
		catch(NullPointerException e3){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e3.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return compte;
	}

	/**
	 * @param email
	 * des comptes recherch�s
	 * @return la liste des instances de la classe {@link Compte} dont l'adresseEmail correspond au param�tre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static List<Compte> getCompteByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Compte> listeComptes = new ArrayList<Compte>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try {
			ResultSet res = s.executeQuery("Select idCompte from Compte Where AdresseMail ='" + email + "'");
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

			if (listeComptes.isEmpty()){
				throw new PasDansLaBaseDeDonneeException("Cette adresse Email ne figure pas dans la base de donn�es");
			}
		}
		catch(PasDansLaBaseDeDonneeException e1){
			System.out.println(e1.getMessage());
		}
		catch (SQLException e2){
			System.out.println(e2.getMessage());
		}
		catch(NullPointerException e2){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e2.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeComptes;
	}
	
	/**
	 * Teste si un identifiant correspond bien � un compte actif de la table COMPTE.
	 * @param id
	 * l'identifiant � tester
	 * @return vrai si l'identifiant entr�e en param�tre correspond � un compte acif de la base de donn�es,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see CreationTables
	 */
	public static boolean estDansLaBddCompte (String id) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		return (getCompteById(id)!=null && getCompteById(id).isActif());
	}

	/**
	 * Test si un identifiant correspond bien � un compte {@link Utilisateur} actif de la table COMPTE.
	 * @param idUtil
	 * @return vrai si l'identifiant entr�e en param�tre correspond � un compte {@link Utilisateur} acif de la base de donn�es
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAOCompte#estDansLaBddCompte(String)
	 * @see Compte#TYPE_UTILISATEUR
	 */
	public static boolean estDansLaBddUtil (String idUtil) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		return (estDansLaBddCompte(idUtil) && getCompteById(idUtil).getType()==Compte.TYPE_UTILISATEUR);
	}
	
	/**
	 * TODO Ma�lle, je te laisse commenter celle l�...
	 * @param type
	 * @param ident
	 * @param nom
	 * @param prenom
	 * @param adresseEMail
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 */
	public static List<Compte> getComptesByRecherche (int type, String ident, String nom, String prenom, String adresseEMail) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
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
		catch(NullPointerException e2){
			if (ConnexionOracleViaJdbc.getC() == null){
				throw new ConnexionFermeeException();
			}
			else if (ConnexionOracleViaJdbc.getC().isClosed()){
				throw new ConnexionFermeeException();
			}
			else{
				throw new NullPointerException(e2.getMessage());
			}
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
		return listeComptes;
	}
}