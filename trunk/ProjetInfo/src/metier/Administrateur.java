package metier;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.SQLException;
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
		/*
		 * TODO Compte.NUMERO_DERNIER_ID_CREE = Compte.NUMERO_DERNIER_ID_CREE+1;
		 */
		return c;

	}

	public void resilierCompte(String idCompte) throws SQLException,
			ClassNotFoundException {
		DAOCompte.getCompteById(idCompte).setActif(false);
	}

	// editerCompte correspond à l'ensemble des setters

	public void demanderAssignation(boolean ajout, Lieu lieu) throws SQLException, ClassNotFoundException{
		Date dateCourante = UtilitaireDate.dateCourante();
		DemandeAssignation  ddeAssignation = new DemandeAssignation(dateCourante, ajout, lieu);
		//creation de la demande d'assignation
		ConnexionOracleViaJdbc.ouvrir();//ouverture de la connection à la bdd
		DAODemandeAssignation.entrerDemandeAssignation(ddeAssignation);
		//entre la demande d'assignation dans la base de données
		ConnexionOracleViaJdbc.fermer();
		/*TODO
		 * gerer les exceptions!!
		 */
	}
}
