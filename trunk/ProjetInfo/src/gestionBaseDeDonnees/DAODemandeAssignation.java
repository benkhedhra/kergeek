package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.exceptionsTechniques.PasDansLaBaseDeDonneeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import metier.DemandeAssignation;
import metier.Lieu;
import metier.UtilitaireDate;
import metier.Velo;

/**
 * Rassemble l'ensemble des méthodes static de liaison avec la base de données concernant la classe metier {@link DemandeAssignation}.
 * @author KerGeek
 */
public class DAODemandeAssignation {

	/**
	 * Ajoute une instance de la classe {@link DemandeAssignation} à la base de données.
	 * @param ddeAssignation
	 * l'instance de la classe {@link DemandeAssignation} à ajouter à la base de données.
	 * @return vrai si l'ajout à la base de données a bel et bien été effectué,
	 * faux sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static boolean createDemandeAssignation(DemandeAssignation ddeAssignation) throws SQLException,ClassNotFoundException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On récupère un identifiant pour cette DemandeAssignation à partir de la séquence correspondante
			ResultSet res = s.executeQuery("Select seqDemandeAssignation.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				//On assigne l'identifiant à la DemandeAssignation qui va être ajoutée à la base de données
				ddeAssignation.setId(id);
				//Insertion de la DemandeAssignation dotée de son identifiant dans la base de données
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
	 * Met à jour une instance de la classe {@link DemandeAssignation} déjà présente dans la base de données.
	 * @param ddeAssignation
	 * l'instance de la classe {@link DemandeAssignation} à mettre à jour dans la base de données.
	 * @return vrai si la mise à jour de la base de données a bel et bien été effectuée,
	 * faux sinon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ConnexionFermeeException
	 */
	public static boolean updateDemandeAssignation(DemandeAssignation ddeAssignation) throws ClassNotFoundException, SQLException, ConnexionFermeeException{
		boolean effectue = false;
		try{
			//S'il existe bien déjà une ligne correspondant à cette instance dans la base données
			if (DAODemandeAssignation.getDemandeAssignationById(ddeAssignation.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met à jour les informations
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
	 * Obtient un objet java DemandeAssignation à partir d'une ligne de la table DEMANDEASSIGNATION de la base de données.
	 * @param identifiant
	 * @return l'instance de la classe {@link DemandeAssignation} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static DemandeAssignation getDemandeAssignationById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		DemandeAssignation ddeAssignation = new DemandeAssignation();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			ResultSet res = s.executeQuery("Select * FROM DemandeAssignation WHERE idDemandeA ='" + identifiant + "'");

			if (res.next()) {
				ddeAssignation.setId(identifiant);
				//On récupère un Timestamp
				java.sql.Timestamp tempsAssignation= res.getTimestamp("dateAssignation");
				//Transformation du Timestamp en date
				java.sql.Date date = new java.sql.Date(tempsAssignation.getTime());
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
		return ddeAssignation;
	}

	/**
	 * @return la liste de l'ensemble des {@link DemandeAssignation} non prise en charge présentes dans la base de données.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see DAODemandeAssignation#getDemandeAssignationById(String)
	 * @see DAOVelo#getVelosByLieu(metier.Lieu)
	 * @see DAODemandeAssignation#updateDemandeAssignation(DemandeAssignation)
	 */
	public static List<DemandeAssignation> getDemandesAssignationEnAttente() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		//Création de la liste des demandes en attente
		List<DemandeAssignation> liste = new LinkedList<DemandeAssignation>();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On récupère la liste des identifiants des DemandesAssignation non prises en charges
			//(car chaque appel à la DAO getDemandeAssignationById ferme la connexion à oracle)
			ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation WHERE priseEnCharge = '0'");
			DemandeAssignation ddeAssignation = new DemandeAssignation();
			List<String> listeIdDde = new ArrayList<String>();
			int diff;
			while(res.next()) {
				String idDdeAssignation = res.getString("idDemandeA");
				listeIdDde.add(idDdeAssignation);
			}

			//On vérifie que les demandes non prises en charge n'ont pas été "compensées" 
			//par les mouvements de vélos faits par les utilisateurs, ou les techniciens 
			//retirant des vélos défectueux. Si une demande est toujours valable, on l'ajoute
			// à la liste des demandes en attentes
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
	 * @param ddeA
	 * @return la différence entre le nombre de {@link Velo} voulus dans le {@link Lieu} selon la DemandeAssignation et le
	 * nombre actuel de Velo dans le Lieu (vélos en état de marche s'il s'agit du garage)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static int getDiff(DemandeAssignation ddeA) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Velo> velos = DAOVelo.getVelosByLieu(ddeA.getLieu());
		// Si il s'agit du garage, on ne tient pas compte des vélos en panne
		// car sa grande capacité ne nous limite pas  (on s'interesse donc uniquement
		// à un ajout de vélos dans ce cas là)
		if(ddeA.getLieu().getId().equals(Lieu.ID_GARAGE)){
			for(int k=0;k<velos.size();k++){
				if(velos.get(k).isEnPanne()){
					velos.remove(velos.get(k));
				}
			}
		}
		return velos.size()-ddeA.getNombreVelosVoulusDansLieu();
	}

	/**
	 * Une fonction qui sert à l'affichage d'une demande d'assignation.
	 * @param ddA
	 * la demande d'assignation à afficher
	 * @return Une chaîne de caractères présentant l'identifiant de la demande d'assignation, l'adresse du {@link Lieu} concerné,
	 * son type (ajout ou retrait) et la différence entre le nombre de vélos voulus dans le lieu et le
	 * nombre actuel de vèlos dans le Lieu
	 * @throws ConnexionFermeeException
	 * @see DAODemandeAssignation#getDiff(DemandeAssignation)
	 * @see DAOVelo#getVelosByLieu(metier.Lieu)
	 */
	public static String ligne(DemandeAssignation ddA) throws ConnexionFermeeException{
		String resul = "Demande "+ddA.getId()+" - "+ddA.getLieu().getAdresse()+" - ";
		try {
			int diff = DAODemandeAssignation.getDiff(ddA);
			String type;
			//Selon la valeur de diff, on identifie s'il s'agit d'un ajout ou d'un retrait
			if(diff<0){type = "ajout";}
			else{type = "retrait";}
			resul = resul+type+ " de "+Math.abs(diff)+" vélos - "+ddA.getDate().toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return resul;
	}
}