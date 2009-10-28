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

public class Administrateur {

	
	
	
	// Attributs

	private Compte compte;

	
	
	
	
	// Constructeur

	public Administrateur() {
		super();
		compte.setType(Compte.TYPE_ADMINISTRATEUR);
	}

	
	
	
	
	
	// Accesseurs

	public Compte getCompte() {
		return this.compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}



	
	
	
	// Methodes

	public Compte creerCompte() {

		Compte c = new Compte();
		/*TODO 
		 * Compte.NUMERO_DERNIER_ID_CREE = Compte.NUMERO_DERNIER_ID_CREE+1;
		 * COMMENT GENERER LES IDENTIFIANT EN SQL?
		 */
		return c;

	}

	
	
	
	public void resilierCompte(String idCompte) throws SQLException,
	ClassNotFoundException {

		DAOCompte.getCompteById(idCompte).setActif(false);
	}

	
	
	// editerCompte() correspond ˆ l'ensemble des setters!

	
	
	
	public void demanderAssignation(boolean ajout, Lieu lieu) throws SQLException, ClassNotFoundException, PasDansLaBaseDeDonneeException{

		Date dateCourante = UtilitaireDate.dateCourante();
		
		ConnexionOracleViaJdbc.ouvrir();
		//ouverture de la connection ˆ la bdd
		
		Statement s = ConnexionOracleViaJdbc.createStatement();
		ResultSet res = s.executeQuery("Select idLieu from Lieu Where idLieu ='" + lieu.getId()+"'");
		
		/*verification de la présence du lieu en question dans la base de donnees.
		 * A terme, l'administrateur demandera une assignation alors qu'il s'interesse deja
		 * a une station particuliere, cette verification sera alors superflue.
		 */
		
		if (res.next()){
		DemandeAssignation  ddeAssignation = new DemandeAssignation(dateCourante, ajout, lieu);
		//creation de la demande d'assignation


		DAODemandeAssignation.entrerDemandeAssignation(ddeAssignation);
		//entre la demande d'assignation dans la base de donnŽes
		}
		
		else{
			System.out.println("La station en question n'existe pas.");
			throw new PasDansLaBaseDeDonneeException();
		}
		
		ConnexionOracleViaJdbc.fermer();
	}
}
