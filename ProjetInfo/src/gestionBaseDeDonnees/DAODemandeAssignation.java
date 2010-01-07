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
		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select seqDemandeAssignation.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				ddeAssignation.setId(id);

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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd míme si des exceptions sont soulevées
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
			if (DAODemandeAssignation.getDemandeAssignationById(ddeAssignation.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd míme si des exceptions sont soulevées	
		}
		return effectue;
	}

	/**
	 * @param identifiant
	 * @return l'instance de la classe {@link DemandeAssignation} dont l'identifiant correspond au paramètre.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static DemandeAssignation getDemandeAssignationById(String identifiant) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		DemandeAssignation ddeAssignation = new DemandeAssignation();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select * FROM DemandeAssignation WHERE idDemandeA ='" + identifiant + "'");

			if (res.next()) {
				ddeAssignation.setId(identifiant);
				java.sql.Timestamp tempsAssignation= res.getTimestamp("dateAssignation");
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
		List<DemandeAssignation> liste = new LinkedList<DemandeAssignation>();

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		try{
			ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation WHERE priseEnCharge = '0'");

			DemandeAssignation ddeAssignation = new DemandeAssignation();
			List<String> listeIdDde = new ArrayList<String>();
			int diff;

			while(res.next()) {
				String idDdeAssignation = res.getString("idDemandeA");
				listeIdDde.add(idDdeAssignation);
			}

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
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}

	/**
	 * @param ddeA
	 * @return la différence entre le nombre de {@link Velo} voulus dans le {@link Lieu} selon la DemandeAssignation et le
	 * nombre actuel de Velo dans le Lieu
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static int getDiff(DemandeAssignation ddeA) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Velo> velos = DAOVelo.getVelosByLieu(ddeA.getLieu());
		return velos.size()-ddeA.getNombreVelosVoulusDansLieu();
	}

	/**
	 * Une fonction qui sert à l'affichage d'une demande d'assignation.
	 * @param ddA
	 * la demande d'assignation à afficher
	 * @return Une chaíne de caractères présentant l'identifiant de la demande d'assignation, l'adresse du {@link Lieu} concerné,
	 * son type (ajout ou retrait) et la différence entre le nombre de vélos voulus dans le lieu et le
	 * nombre actuel de vèlos dans le Lieu
	 * @throws ConnexionFermeeException
	 * @see DAODemandeAssignation#getDiff(DemandeAssignation)
	 * @see DAOVelo#getVelosByLieu(metier.Lieu)
	 */
	public static String ligne(DemandeAssignation ddA) throws ConnexionFermeeException{
		String resul = "Demande "+ddA.getId()+" - "+ddA.getLieu().getAdresse()+" - ";
		try {
			int diff = ddA.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(ddA.getLieu()).size();
			String type;
			if(diff<0){type = "retrait";}
			else{type = "ajout";}
			resul = resul+type+ " de "+Math.abs(diff)+" vélos - "+ddA.getDate().toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return resul;
	}
}