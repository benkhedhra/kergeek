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
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link Compte}.
 * @author KerGeek
 */
public class DAOCompte {

	/**
	 * Ajoute une instance de la classe {@link Compte} à la base de données.
	 * C'est au cours de cette action que les identifiants sont générés à l'aide de séquences SQL. 
	 * Il existe une séquence par type de compte et un identifiant commence toujours par la lettre correspondant au type de 
	 * compte en question ('a' pour Administrateur, 't' pour Technicien et 'u' pour Utilisateur).
	 * <br> <br> Une fois l'identifiant attribuer à l'instance, on insère les valeurs des attributs de celles-ci dans la table COMPTE
	 * et une nouvelle ligne est créée.
	 * @param compte
	 * l'instance de la classe {@link Compte} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see {@link InitialisationBaseDeDonnees}
	 */
	public static boolean createCompte(Compte compte) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = null;

			//S'il s'agit d'un compte d'un administrateur
			if (compte.getType() == Compte.TYPE_ADMINISTRATEUR){
				// On récupère un identifiant pour ce compte à partir de la séquence correspondante
				res = s.executeQuery("Select seqAdministrateur.NEXTVAL as id from dual");
				if (res.next()){
					String nb = res.getString("id");
					System.out.println(nb);
					String id = "a" + res.getString("id");
					//On assigne l'identifiant au compte qui va être ajouté à la base de données
					compte.setId(id);

				}
				else{
					throw new SQLException("probleme de sequence");
				}
			}
			//S'il s'agit d'un compte d'un utilisateur
			else if (compte.getType() == Compte.TYPE_UTILISATEUR){
				// On récupère un identifiant pour ce compte à partir de la séquence correspondante
				res = s.executeQuery("Select seqUtilisateur.NEXTVAL as id from dual");
				if (res.next()){
					String nb = res.getString("id");
					System.out.println(nb);
					String id = "u" + res.getString("id");
					//On assigne l'identifiant au compte ajouté à la base de données
					compte.setId(id);
				}
				else{
					throw new SQLException("probleme de sequence");
				}
			}
			//S'il s'agit d'un compte d'un technicien
			else if (compte.getType() == Compte.TYPE_TECHNICIEN){
				// On récupère un identifiant pour ce compte à partir de la séquence correspondante
				res = s.executeQuery("Select seqTechnicien.NEXTVAL as id from dual");
				if (res.next()){
					String nb = res.getString("id");
					System.out.println(nb);
					String id = "t" + res.getString("id");
					//On assigne l'identifiant au compte ajouté à la base de données
					compte.setId(id);
				}
				else{
					throw new SQLException("probleme de sequence");
				}
			}
			//Insertion du compte doté de son identifiant dans la base de données
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}


	/**
	 * Met à jour une instance de la classe {@link Compte} déjà présente dans la base de données.
	 * @param compte
	 * l'instance de la classe {@link Compte} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @throws PasDansLaBaseDeDonneeException 
	 * @see {@link InitialisationBaseDeDonnees}
	 */
	public static boolean updateCompte(Compte compte) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			//S'il existe bien déjà une ligne correspondant à cette instance dans la base données
			if (DAOCompte.getCompteById(compte.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met à jour les informations
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
			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de données, mise à jour impossible");
			}
		}
		catch (SQLException e1){
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
		catch(PasDansLaBaseDeDonneeException e3){
			System.out.println(e3.getMessage());
		}
		finally{
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}




	/**
	 * Obtient un objet java Compte à partir d'une ligne de la table COMPTE de la base de données.
	 * @param identifiant
	 * du compte recherché
	 * @return l'instance de la classe {@link Compte} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see {@link InitialisationBaseDeDonnees}
	 */
	public static Compte getCompteById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		Compte compte = null;
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select motDePasse, actif, type, adresseMail from Compte Where idCompte ='" + identifiant + "'");
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return compte;
	}

	/**
	 * @param email
	 * des comptes recherchés
	 * @return la liste des instances de la classe {@link Compte} dont l'adresseEmail correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static List<Compte> getCompteByAdresseEmail(String email) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		List<Compte> listeComptes = new ArrayList<Compte>();
		try {
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

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
				throw new PasDansLaBaseDeDonneeException("Cette adresse Email ne figure pas dans la base de données");
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return listeComptes;
	}

	/**
	 * Teste si un identifiant correspond bien à un compte actif de la table COMPTE.
	 * @param id
	 * l'identifiant à tester
	 * @return vrai si l'identifiant entrée en paramètre correspond à un compte acif de la base de données,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see {@link InitialisationBaseDeDonnees}
	 */
	public static boolean estDansLaBddCompte (String id) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		return (getCompteById(id)!=null && getCompteById(id).isActif());
	}

	/**
	 * Test si un identifiant correspond bien à un compte {@link Utilisateur} actif de la table COMPTE.
	 * @param idUtil
	 * @return vrai si l'identifiant entrée en paramètre correspond à un compte {@link Utilisateur} acif de la base de données
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
	 * cette méthode permet d'obtenir la liste des comptes correspondant à une recherche donnée
	 * <br>une recherche s'effectue sur un nombre de paramètres allant de 0 à 5
	 * <br>si le type n'est pas renseigné ou s'il est renseigné et correspond au type d'un utilisateur, l'administrateur pourra faire une recherche à 4 paramètres au plus (plus le type)
	 * <br>sinon, la recherche pourra s'effectuer au maximum sur 2 paramètres (plus le type) : l'identifiant et l'adresse e-mail
	 * @param type
	 * le type de compte recherché (valant 0 si aucun type n'a été renseigné)
	 * @param ident
	 * l'identifiant du compte recherché
	 * @param nom
	 * le nom de l'utilisateur recherché
	 * @param prenom
	 * le prénom de l'utilisateur recherché
	 * @param adresseEMail
	 * l'adresse e-mail du compte recherché
	 * @return la liste des instances de la classe {@link Compte} dont correspondant à la requête créée à partir des paramètres remplis
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 */
	public static List<Compte> getComptesByRecherche (int type, String ident, String nom, String prenom, String adresseEMail) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		//le type est toujours renseigné, les autres peuvent valoir null
		//on va compter le nombre de paramètres non-nuls
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
		//le premier de la liste est forcément non nul car il vaut une chaîne vide
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
			//se deconnecter de la bdd même si des exceptions sont soulevées
			ConnexionOracleViaJdbc.fermer();
		}
		return listeComptes;
	}
}