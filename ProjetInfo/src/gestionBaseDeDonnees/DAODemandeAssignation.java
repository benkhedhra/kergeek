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
 * Rassemble l'ensemble des m�thodes static de liaison avec la base de donn�es concernant la classe metier {@link DemandeAssignation}.
 * @author KerGeek
 */
public class DAODemandeAssignation {

	/**
	 * Ajoute une instance de la classe {@link DemandeAssignation} � la base de donn�es.
	 * @param ddeAssignation
	 * l'instance de la classe {@link DemandeAssignation} � ajouter � la base de donn�es.
	 * @return vrai si l'ajout � la base de donn�es a bel et bien �t� effectu�,
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es
		}
		return effectue;
	}

	/**
	 * Met � jour une instance de la classe {@link DemandeAssignation} d�j� pr�sente dans la base de donn�es.
	 * @param ddeAssignation
	 * l'instance de la classe {@link DemandeAssignation} � mettre � jour dans la base de donn�es.
	 * @return vrai si la mise � jour de la base de donn�es a bel et bien �t� effectu�e,
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
			ConnexionOracleViaJdbc.fermer();//pour se deconnecter de la bdd m�me si des exceptions sont soulev�es	
		}
		return effectue;
	}

	/**
	 * @param identifiant
	 * @return l'instance de la classe {@link DemandeAssignation} dont l'identifiant correspond au param�tre.
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
	 * @return la liste de l'ensemble des {@link DemandeAssignation} non prise en charge pr�sentes dans la base de donn�es.
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
	 * @return la diff�rence entre le nombre de {@link Velo} voulus dans le {@link Lieu} selon la DemandeAssignation et le
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
	 * Une fonction qui sert � l'affichage d'une demande d'assignation.
	 * @param ddA
	 * la demande d'assignation � afficher
	 * @return Une cha�ne de caract�res pr�sentant l'identifiant de la demande d'assignation, l'adresse du {@link Lieu} concern�,
	 * son type (ajout ou retrait) et la diff�rence entre le nombre de v�los voulus dans le lieu et le
	 * nombre actuel de v�los dans le Lieu
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
			resul = resul+type+ " de "+Math.abs(diff)+" v�los - "+ddA.getDate().toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return resul;
	}
}