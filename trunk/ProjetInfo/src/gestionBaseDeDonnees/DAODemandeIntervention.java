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
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link DemandeIntervention}.
 * @author KerGeek
 */
public class DAODemandeIntervention {

	/**
	 * Ajoute une instance de la classe {@link DemandeIntervention} � la base de donn�es.
	 * @param ddeIntervention
	 * l'instance de la classe {@link DemandeIntervention} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
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

			// On r�cup�re un identifiant � partir de la s�quence correspondante
			ResultSet res = s.executeQuery("Select seqDemandeIntervention.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				//On assigne l'identifiant � la DemandeIntervention qui va �tre ajout�e � la base de donn�es
				ddeIntervention.setId(id);

				//Insertion de la DemandeIntervention dot�e de son identifiant dans la base de donn�es
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * Met � jour une instance de la classe {@link DemandeIntervention} d�j� pr�sente dans la base de donn�es.
	 * @param ddeIntervention
	 * l'instance de la classe {@link DemandeIntervention} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
	 * faux sinon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateDemandeIntervention(DemandeIntervention ddeIntervention) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			//S'il existe bien d�j� une ligne correspondant � cette instance dans la base donn�es
			if (DAODemandeIntervention.getDemandeInterventionById(ddeIntervention.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met � jour les informations
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
				System.out.println("Demande d'intervention mise a jour dans la base de donn�es");
			}

			else {
				throw new PasDansLaBaseDeDonneeException("Ne figure pas dans la base de donn�es, mise � jour impossible");
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * Obtient un objet java DemandeIntervention � partir d'une ligne de la table
	 * DEMANDEINTERVENTION de la base de donn�es.
	 * @param identifiant
	 * @return l'instance de la classe {@link DemandeIntervention} dont l'identifiant correspond au param�tre.
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
					// On r�cup�re les informations

					//On r�cup�re un Timestamp
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return ddeIntervention;
	}

	/**
	 * @return la liste de l'ensemble des {@link DemandeIntervention} non prise en charge, c'est-�-dire auquelles 
	 * aucune {@link Intervention} n'a encore �t� associ�e, pr�sentes dans la base de donn�es 
	 * ordonn�es par dat, de la plus ancienne � la plus r�cente.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAODemandeIntervention#getDemandeInterventionById(String)
	 */
	public static List<DemandeIntervention> getDemandesInterventionEnAttente() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//Cr�ation de la liste des demandes en attente
		List<DemandeIntervention> liste = new LinkedList<DemandeIntervention>();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On r�cup�re la liste des identifiants des DemandeIntervertion non prises en charges
			//c'est-�-dire auquelles aucune intervention n'est associ�e
			//(car chaque appel � la DAO getDemandeAssignationById ferme la connexion � oracle)

			ResultSet res = s.executeQuery("Select idDemandeI from DemandeIntervention WHERE idIntervention IS NULL ORDER BY dateDemandeI ASC");

			List<String> listeIdInter = new ArrayList<String>();
			while(res.next()) {
				String idDdeIntervention = res.getString("idDemandeI");
				listeIdInter.add(idDdeIntervention);
			}

			//ajout des DemandeIntervertion en attente � la liste des demandes en attentes
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}

	/**
	 * Une fonction qui sert � l'affichage d'une demande d'intervention.
	 * @param ddeI
	 * la demande d'intervention � afficher
	 * @return Une cha�ne de caract�res pr�sentant l'identifiant de la demande d'intervention, le {@link Velo} concern�, 
	 * l'adresse de la station dans laquelle il se trouve et la date de la demande.
	 */
	public static String ligne(DemandeIntervention ddeI){
		return "Demande "+ddeI.getId()+" - V�lo "+ddeI.getVelo().getId()+" - Station "+ddeI.getVelo().getLieu().getAdresse()+" - "+ddeI.getDate().toString();
	}

}
