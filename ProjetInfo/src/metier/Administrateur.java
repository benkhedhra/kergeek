package metier;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

import metier.exceptionsMetier.TypeCompteException;

/** 
 * Administrateur est la classe représentant un administrateur du parc à velos.
 * Un administrateur est caracterisé par un compte.
 * @see Compte
 * @author KerGeek
 */

public class Administrateur {

	// Attributs 

	/**
	 * Compte de l'Administrateur. Ce compte est modifiable.
	 * @see Administrateur#getCompte()
	 * @see Administrateur#setCompte(Compte)
	 */
	private Compte compte;


	// Constructeurs

	/**
	 * Constructeur par défaut d'un Administrateur.
	 */
	public Administrateur() {
		super();
	}
	
	/**
	 * Constructeur d'initialisation d'Administrateur.
	 * L'objet administrateur est créé à partir d'un compte
	 * 
	 * @param  compte
	 * le compte de l'administrateur
	 * @see Compte
	 * @see Administrateur#compte       
	 */
	public Administrateur(Compte compte) {
		this.setCompte(compte);
	}



	// Accesseurs et Mutateurs

	/**
	 * @return {@link Administrateur#compte} 
	 */
	public Compte getCompte() {
		return this.compte;
	}

	/**
	 * Initialise {@link Administrateur#compte}.
	 * @param compte
	 * le nouveau compte de l'administrateur
	 * @see Compte
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	
	
	//Méthodes

	/**
	 * Crée un compte, pour un futur Utilisateur du parc à velos par exemple, 
	 * ou pour un nouveau Technicien, ou même pour un nouvel Administrateur.
	 * 
	 * @param type
	 * le type du compte à créer
	 * @param adresseEmail
	 * l'adresse email du compte à créer
	 * @return le nouveau compte 
	 * @see Compte
	 */
	public Compte creerCompte(int type, String adresseEmail){
		Compte compte = new Compte(type, adresseEmail);
		return compte;
	}

	/**
	 * Crée un nouvel utilisateur à partir d'un compte et de coordonnées.
	 * @param compte
	 * @param nom
	 * @param prenom
	 * @param adressePostale
	 * @return le nouvel utilisateur
	 * @throws TypeCompteException
	 * @see Compte
	 * @see Utilisateur#Utilisateur(Compte, String, String, String)
	 */
	public Utilisateur creerUtilisateur(Compte compte, String nom, String prenom, String adressePostale) throws TypeCompteException{
		Utilisateur utilisateur = null;
		if (compte.getType() == Compte.TYPE_UTILISATEUR){
			utilisateur = new Utilisateur(compte, nom, prenom, adressePostale);
		}
		else{
			throw new TypeCompteException("Le type de compte ne correspond pas à un utilisateur");
		}
		return utilisateur;
	}

	/**
	 * Crée un nouvel administrateur à partir d'un compte.
	 * @param compte
	 * @return le nouvel administrateur
	 * @throws TypeCompteException
	 * @see Compte
	 * @see Administrateur#Administrateur(Compte)
	 */
	public Administrateur creerAdministrateur(Compte compte) throws TypeCompteException{
		Administrateur administrateur = null;
		if (compte.getType() == Compte.TYPE_ADMINISTRATEUR){
			administrateur = new Administrateur(compte);
		}
		else{
			throw new TypeCompteException("Le type de compte ne correspond pas à un administrateur");
		}
		return administrateur;
	}
	
	/**
	 * Crée un nouveau technicien à partir d'un compte.
	 * @param compte
	 * @return le nouveau technicien
	 * @throws TypeCompteException
	 * @see Compte
	 * @see Technicien#Technicien(Compte)
	 */
	public Technicien creerTechnicien(Compte compte)throws TypeCompteException{
		Technicien technicien = null;
		if (compte.getType() == Compte.TYPE_TECHNICIEN){
			technicien = new Technicien(compte);
		}
		else{
			throw new TypeCompteException("Le type de compte ne correspond pas à un technicien");
		}
		return technicien;
	}

	/**
	 * Résilie un compte d'un abonné du parc à velos en mettant le booleen actif du compte en question à false
	 * @param compte
	 * @return le compte identique à compte mais rendu inactif
	 * @see Compte
	 * @see Compte#setActif(Boolean)
	 */

	public Compte resilierCompte(Compte compte){
		Compte c2 = compte;
		c2.setActif(false);
		return c2;
	}

	// Le cas d'utilisation editerCompte() correspond a l'ensemble des setters!

	/**
	 * Crée une demande d'assignation, c'est-à-dire une demande de déplacement de vélos 
	 * à partir d'une station ou du Garage vers une station, en indiquant le nombre de vélos souhaîté dans la station
	 * en question.
	 * @param nombreVelosVouluDansStation
	 * @param lieu
	 * @return la nouvelle demande d'assignation
	 * @see DemandeAssignation
	 */

	public DemandeAssignation demanderAssignation(int nombreVelosVouluDansStation, Lieu lieu){
		DemandeAssignation  ddeAssignation = new DemandeAssignation(nombreVelosVouluDansStation, lieu);
		return ddeAssignation;
	}
	
	public Velo supprimerVelo(Velo v) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Velo v2 = v;
		v2.setLieu(Detruit.getInstance());
		return v2;
	}
	


	/**
	 * Vérifie l'égalité entre deux instances de la classe Administrateur.
	 * @return vrai si les deux instances de la classe Administrateur ont le même compte,
	 * faux sinon
	 * @see Compte#equals(Object)
	 */
	@Override
	public boolean equals(Object o) {
		Administrateur a =(Administrateur) o;
		return this.getCompte().equals(a.getCompte());
	}

	/**
	 * @return l'identifiant du compte de l'administrateur suivi de son adresse email
	 * @see Compte#toString()
	 */
	@Override
	public String toString(){
		return this.getCompte().toString();
	}

}
