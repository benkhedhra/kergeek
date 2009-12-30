package metier;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

import metier.exceptionsMetier.TypeCompteException;

/** 
 * Administrateur est la classe repr�sentant un administrateur du parc � velos.
 * Un administrateur est caracteris� par un compte.
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
	 * Constructeur par d�faut d'un Administrateur.
	 */
	public Administrateur() {
		super();
	}
	
	/**
	 * Constructeur d'initialisation d'Administrateur.
	 * L'objet administrateur est cr�� � partir d'un compte
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

	
	
	//M�thodes

	/**
	 * Cr�e un compte, pour un futur Utilisateur du parc � velos par exemple, 
	 * ou pour un nouveau Technicien, ou m�me pour un nouvel Administrateur.
	 * 
	 * @param type
	 * le type du compte � cr�er
	 * @param adresseEmail
	 * l'adresse email du compte � cr�er
	 * @return le nouveau compte 
	 * @see Compte
	 */
	public Compte creerCompte(int type, String adresseEmail){
		Compte compte = new Compte(type, adresseEmail);
		return compte;
	}

	/**
	 * Cr�e un nouvel utilisateur � partir d'un compte et de coordonn�es.
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
			throw new TypeCompteException("Le type de compte ne correspond pas � un utilisateur");
		}
		return utilisateur;
	}

	/**
	 * Cr�e un nouvel administrateur � partir d'un compte.
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
			throw new TypeCompteException("Le type de compte ne correspond pas � un administrateur");
		}
		return administrateur;
	}
	
	/**
	 * Cr�e un nouveau technicien � partir d'un compte.
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
			throw new TypeCompteException("Le type de compte ne correspond pas � un technicien");
		}
		return technicien;
	}

	/**
	 * R�silie un compte d'un abonn� du parc � velos en mettant le booleen actif du compte en question � false
	 * @param compte
	 * @return le compte identique � compte mais rendu inactif
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
	 * Cr�e une demande d'assignation, c'est-�-dire une demande de d�placement de v�los 
	 * � partir d'une station ou du Garage vers une station, en indiquant le nombre de v�los souha�t� dans la station
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
	 * V�rifie l'�galit� entre deux instances de la classe Administrateur.
	 * @return vrai si les deux instances de la classe Administrateur ont le m�me compte,
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
