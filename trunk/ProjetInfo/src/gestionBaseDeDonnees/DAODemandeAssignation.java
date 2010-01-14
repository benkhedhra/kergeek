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
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On r�cup�re un identifiant pour cette DemandeAssignation � partir de la s�quence correspondante
			ResultSet res = s.executeQuery("Select seqDemandeAssignation.NEXTVAL as id from dual");
			if (res.next()){
				String id = res.getString("id");
				//On assigne l'identifiant � la DemandeAssignation qui va �tre ajout�e � la base de donn�es
				ddeAssignation.setId(id);
				//Insertion de la DemandeAssignation dot�e de son identifiant dans la base de donn�es
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
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
			//S'il existe bien d�j� une ligne correspondant � cette instance dans la base donn�es
			if (DAODemandeAssignation.getDemandeAssignationById(ddeAssignation.getId()) != null){
				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				//On met � jour les informations
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}
		return effectue;
	}

	/**
	 * Obtient un objet java DemandeAssignation � partir d'une ligne de la table DEMANDEASSIGNATION de la base de donn�es.
	 * @param identifiant
	 * @return l'instance de la classe {@link DemandeAssignation} dont l'identifiant correspond au param�tre.
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
				//On r�cup�re un Timestamp
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
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
		//Cr�ation de la liste des demandes en attente
		List<DemandeAssignation> liste = new LinkedList<DemandeAssignation>();
		try{
			ConnexionOracleViaJdbc.ouvrir();
			Statement s = ConnexionOracleViaJdbc.createStatement();

			// On r�cup�re la liste des identifiants des DemandesAssignation non prises en charges
			//(car chaque appel � la DAO getDemandeAssignationById ferme la connexion � oracle)
			ResultSet res = s.executeQuery("Select idDemandeA from DemandeAssignation WHERE priseEnCharge = '0'");
			DemandeAssignation ddeAssignation = new DemandeAssignation();
			List<String> listeIdDde = new ArrayList<String>();
			int diff;
			while(res.next()) {
				String idDdeAssignation = res.getString("idDemandeA");
				listeIdDde.add(idDdeAssignation);
			}

			//On v�rifie que les demandes non prises en charge n'ont pas �t� "compens�es" 
			//par les mouvements de v�los faits par les utilisateurs, ou les techniciens 
			//retirant des v�los d�fectueux. Si une demande est toujours valable, on l'ajoute
			// � la liste des demandes en attentes
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
			//se deconnecter de la bdd m�me si des exceptions sont soulev�es
			ConnexionOracleViaJdbc.fermer();
		}

		return liste;
	}

	/**
	 * @param ddeA
	 * @return la diff�rence entre le nombre de {@link Velo} voulus dans le {@link Lieu} selon la DemandeAssignation et le
	 * nombre actuel de Velo dans le Lieu (v�los en �tat de marche s'il s'agit du garage)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static int getDiff(DemandeAssignation ddeA) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Velo> velos = DAOVelo.getVelosByLieu(ddeA.getLieu());
		// Si il s'agit du garage, on ne tient pas compte des v�los en panne
		// car sa grande capacit� ne nous limite pas  (on s'interesse donc uniquement
		// � un ajout de v�los dans ce cas l�)
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
			int diff = DAODemandeAssignation.getDiff(ddA);
			String type;
			//Selon la valeur de diff, on identifie s'il s'agit d'un ajout ou d'un retrait
			if(diff<0){type = "ajout";}
			else{type = "retrait";}
			resul = resul+type+ " de "+Math.abs(diff)+" v�los - "+ddA.getDate().toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return resul;
	}
}