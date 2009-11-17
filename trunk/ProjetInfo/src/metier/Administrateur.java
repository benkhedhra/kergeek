package metier;

import exception.PasDansLaBaseDeDonneeException;
import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/** 
 * Administrateur est la classe representant un administrateur du parc a velos.
 * Un administrateur est caracterise par un compte.
 * 
 * @see Compte
 * @see ConnexionViaJdbc
 * @see DAOCompte
 * @see DAODemandeAssignation
 */

public class Administrateur {

	// Attribut 
	
	/**
	 * Compte de l'administrateur. Ce compte est modifiable.
	 * 
	 * @see Compte
	 * @see Administrateur#Administrateur(compte)
	 * @see Administrateur#getCompte()
	 * @see Administrateur#setCompte(compte)
	 * 
	 */

	private Compte compte;


	// Constructeurs
	
	 /**
	  * Constructeur d'initialisation d'Administrateur.
	  * L'objet administrateur est creer a partir d'un compte
	  * 
	  * @param  compte, le compte de l'administrateur
	  * @see Compte
	  * @see Administrateur#compte       
	 */
	    
	public Administrateur(Compte compte) {
		super();
		this.setCompte(compte);
	}

	// Accesseurs et modificateurs

	/**
     * retourne le compte de l'administrateur
     * 
     * @return compte, le compte de l'administrateur 
     */
	
	public Compte getCompte() {
		return this.compte;
	}
	
	/**
     * Met à jour le compte de l'administrateur
     * 
     * @param compte, le nouveau compte de l'administrateur
     * @see Compte
     */
	
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	// Methodes

	/**
	 * Cree un compte pour un futur utilisateur du parc a velos
	 * 
	 * @param compte
	 * @return c, nouveau compte 
	 * @see Compte
	 * @see DAOCompte
	 * @throws SQLException, si SQL Server retourne un avertissement ou une erreur
	 * @throws ClassNotFoundException, si SQL Server ne retrouve pas la classe Compte ou DAOCompte
	 */
	
	public Compte creerCompte() throws SQLException, ClassNotFoundException {
		Compte c = new Compte();
		DAOCompte.createCompte(compte);
		return c;
	}

	/**
	 * Resilie un compte d'un abonne du parc a velos
	 * 
	 * @param idCompte
	 * @see DAOCompte
	 * @throws SQLException, si SQL Server retourne un avertissement ou une erreur
	 * @throws ClassNotFoundException, si SQL Server ne retrouve pas la classe DAOCompte
	 */
	
	public void resilierCompte(String idCompte) throws SQLException,
	ClassNotFoundException {
		DAOCompte.getCompteById(idCompte).setActif(false);
	}

	// editerCompte() correspond a l'ensemble des setters!

	/**
	 * Cree une demande d'assignation, c'est a dire une demande de deplacement de velos 
	 * d'une station a une autre
	 * 
	 * @param ajout
	 * @param nombre
	 * @param lieu
	 * @see ConnexionViaJdbc
	 * @see DAODemandeAssignation
	 * @throws SQLException, si SQL Server retourne un avertissement ou une erreur
	 * @throws ClassNotFoundException, si SQL Server ne retrouve pas la classe DAODemandeAssignation
	 * @throws PasDansLaBaseDeDonneeException, si SQL Server ne retrouve pas la classe idLieu dans la BDD
	 */
	
	public void demanderAssignation(boolean ajout, int nombre, Lieu lieu) throws SQLException, ClassNotFoundException, PasDansLaBaseDeDonneeException{

		Date dateCourante = UtilitaireDate.dateCourante();
		//ouverture de la connexion a la bdd
		ConnexionOracleViaJdbc.ouvrir();
		/* verification de la presence du lieu en question dans la base de donnees.
		 * A terme, l'administrateur demandera une assignation alors qu'il s'interesse deja
		 * a une station particuliere, cette verification sera alors superflue.
		 */
		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select idLieu from Lieu Where idLieu ='" + lieu.getId()+"'");
		//creation de la demande d'assignation
		if (res.next()){
			DemandeAssignation  ddeAssignation = new DemandeAssignation(dateCourante, ajout, nombre, lieu);
			DAODemandeAssignation.createDemandeAssignation(ddeAssignation);
			//entre la demande d'assignation dans la base de donnees
		}
		else{
			System.out.println("La station en question n'existe pas.");
			throw new PasDansLaBaseDeDonneeException();
		}
		// fermeture de la connexion
		ConnexionOracleViaJdbc.fermer();
	}
}
