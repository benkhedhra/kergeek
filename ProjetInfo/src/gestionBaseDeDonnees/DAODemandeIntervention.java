package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import metier.DemandeIntervention;
import metier.Intervention;
import metier.UtilitaireDate;
import metier.Velo;

/**
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link DemandeIntervention}.
 * @author KerGeek
 */
public class DAODemandeIntervention {

	/**
	 * Ajoute une instance de la classe {@link DemandeIntervention} à la base de données.
	 * @param ddeIntervention
	 * l'instance de la classe {@link DemandeIntervention} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createDemandeIntervention(DemandeIntervention ddeIntervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On récupère un identifiant à partir de la séquence correspondante
			ResultSet res = s.executeQuery("Select seqDemandeIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				//On assigne l'identifiant à la DemandeIntervention qui va être ajoutée à la base de données
				ddeIntervention.setId(id);

				//Insertion de la DemandeIntervention dotée de son identifiant dans la base de données
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
					effectue=true;
				}
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
	 * Met à jour une instance de la classe {@link DemandeIntervention} déjà présente dans la base de données.
	 * @param ddeIntervention
	 * l'instance de la classe {@link DemandeIntervention} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateDemandeIntervention(DemandeIntervention ddeIntervention) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			//S'il existe bien déjà une ligne correspondant à cette instance dans la base données
			if (DAODemandeIntervention.getDemandeInterventionById(ddeIntervention.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met à jour les informations
				s.executeUpdate("UPDATE DemandeIntervention SET "
						+ "dateDemandeI = TO_DATE('" + UtilitaireDate.conversionPourSQL(ddeIntervention.getDate()) +"','DD-MM-YYYY HH24:MI'), "
						+ "idVelo = '" + ddeIntervention.getVelo().getId() + "', "
						+ "idCompte = '" + ddeIntervention.getUtilisateur().getCompte().getId() + "', "
						+ "idLieu = '" + ddeIntervention.getVelo().getLieu().getId() + "', "
						+ "idIntervention = '" + ddeIntervention.getIntervention().getId() + "'"
						+ " WHERE idDemandeI = '"+ ddeIntervention.getId() + "'"
				);

				s.executeUpdate("COMMIT");
				effectue=true;
				System.out.println("Demande d'intervention mise a jour dans la base de données");
			}

			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de données, mise à jour impossible");
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
	 * Obtient un objet java DemandeIntervention à partir d'une ligne de la table
	 * DEMANDEINTERVENTION de la base de données.
	 * @param identifiant
	 * @return l'instance de la classe {@link DemandeIntervention} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static DemandeIntervention getDemandeInterventionById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		DemandeIntervention ddeIntervention = new DemandeIntervention();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select * FROM DemandeIntervention WHERE idDemandeI ='" + identifiant + "'");
			try {
				if (res.next()) {
					// On récupère les informations

					//On récupère un Timestamp
					java.sql.Timestamp tempsDemandeI = res.getTimestamp("dateDemandeI");
					//Transformation du Timestamp en date
					java.sql.Date dateDemandeI = new java.sql.Date(tempsDemandeI.getTime());
					String idVelo = res.getString("idVelo");
					String idCompte = res.getString("idCompte");
					String idIntervention = res.getString("idIntervention");

					// On initialise les attributs de notre DemandeIntervention
					ddeIntervention.setId(identifiant);
					ddeIntervention.setDate(dateDemandeI);
					ddeIntervention.setVelo(DAOVelo.getVeloById(idVelo));
					ddeIntervention.setUtilisateur(DAOUtilisateur.getUtilisateurById(idCompte));
					if(idIntervention!=null){
						ddeIntervention.setIntervention(DAOIntervention.getInterventionById(idIntervention));
					}
					else{
						ddeIntervention.setIntervention(null);
					}

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
		return ddeIntervention;
	}

	/**
	 * @return la liste de l'ensemble des {@link DemandeIntervention} non prise en charge, c'est-à-dire auquelles 
	 * aucune {@link Intervention} n'a encore été associée, présentes dans la base de données 
	 * ordonnées par dat, de la plus ancienne à la plus récente.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAODemandeIntervention#getDemandeInterventionById(String)
	 */
	public static List<DemandeIntervention> getDemandesInterventionEnAttente() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//Création de la liste des demandes en attente
		List<DemandeIntervention> liste = new LinkedList<DemandeIntervention>();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On récupère la liste des identifiants des DemandeIntervertion non prises en charges
			//c'est-à-dire auquelles aucune intervention n'est associée
			//(car chaque appel à la DAO getDemandeAssignationById ferme la connexion à oracle)

			ResultSet res = s.executeQuery("Select idDemandeI from DemandeIntervention WHERE idIntervention IS NULL ORDER BY dateDemandeI ASC");

			List<String> listeIdInter = new ArrayList<String>();
			while(res.next()) {
				String idDdeIntervention = res.getString("idDemandeI");
				listeIdInter.add(idDdeIntervention);
			}

			//ajout des DemandeIntervertion en attente à la liste des demandes en attentes
			DemandeIntervention ddeIntervention = new DemandeIntervention();
			for (String idDdeI : listeIdInter){
				ddeIntervention = getDemandeInterventionById(idDdeI);
				liste.add(ddeIntervention);
			}
		}
		catch(SQLException e1){
			liste = null;
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

		return liste;
	}

	/**
	 * Une fonction qui sert à l'affichage d'une demande d'intervention.
	 * @param ddeI
	 * la demande d'intervention à afficher
	 * @return Une chaîne de caractères présentant l'identifiant de la demande d'intervention, le {@link Velo} concerné, 
	 * l'adresse de la station dans laquelle il se trouve et la date de la demande.
	 */
	public static String ligne(DemandeIntervention ddeI){
		return "Demande "+ddeI.getId()+" - Vélo "+ddeI.getVelo().getId()+" - Station "+ddeI.getVelo().getLieu().getAdresse()+" - "+ddeI.getDate().toString();
	}

}
