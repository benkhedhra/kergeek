package metier;

import exceptionsMetier.TypeCompteException;

/** 
 * Administrateur est la classe representant un administrateur du parc a velos.
 * Un administrateur est caracterise par un compte.
 * 
 * @see Compte
 * @see Utilisateur
 */

public class Administrateur {

	// Attributs 

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
	 * L'objet administrateur est creer ‡ partir d'un compte
	 * 
	 * @param  compte, le compte de l'administrateur
	 * @see Compte
	 * @see Administrateur#compte       
	 */

	public Administrateur(Compte compte) {
		this.setCompte(compte);
	}

	// Accesseurs et modificateurs

	/**
	 * Renvoie le compte de l'administrateur
	 * 
	 * @return compte, le compte de l'administrateur 
	 */

	public Compte getCompte() {
		return this.compte;
	}

	/**
	 * Met ‡ jour le compte de l'administrateur
	 * 
	 * @param compte, le nouveau compte de l'administrateur
	 * @see Compte
	 */

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	// Methodes

	/**
	 * Crée un compte, pour un futur utilisateur du parc ‡ velos par exemple, 
	 * ou pour un nouveau technicien, ou même pour un nouvel administarteur
	 * 
	 * @param compte
	 * @return compte, le nouveau compte 
	 * @see Compte
	 */

	public Compte creerCompte(int type, String adresseEmail){
		Compte compte = new Compte(type, adresseEmail);
		return compte;
	}

	/**
	 * Crée un nouvel utilisateur à partir de son compte et de ses coordonnée
	 * @param compte
	 * @param nom
	 * @param prenom
	 * @param adressePostale
	 * @return utilisateur, le nouvel utilisateur
	 */
	public Utilisateur creerUtilisateur(Compte compte, String nom, String prenom, String adressePostale){
		Utilisateur utilisateur = new Utilisateur(compte, nom, prenom, adressePostale);
		return utilisateur;
	}

	/**
	 * Crée un nouveau technicien à partir d'un compte son compte
	 * @param compte
	 * @return technicien, le nouveau technicien
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

	public Technicien creerTechnicien(Compte compte){
		Technicien technicien = new Technicien(compte);
		return technicien;
	}

	/**
	 * Resilie un compte d'un abonne du parc a velos
	 * 
	 * @param compte
	 * @see Compte
	 */

	public void resilierCompte(Compte compte){
		compte.setActif(false);
	}

	// editerCompte() correspond a l'ensemble des setters!

	/**
	 * Cree une demande d'assignation, c'est a dire une demande de deplacement de velos 
	 * d'une station a une autre
	 * 
	 * @param nombreVelosVouluDansStation
	 * @param lieu
	 * @see DemandeAssignation
	 */

	public DemandeAssignation demanderAssignation(int nombreVelosVouluDansStation, Lieu lieu){
		DemandeAssignation  ddeAssignation = new DemandeAssignation(UtilitaireDate.dateCourante(), nombreVelosVouluDansStation, lieu);
		return ddeAssignation;
	}


	@Override
	public boolean equals(Object o) {
		Administrateur a =(Administrateur) o;
		return this.getCompte().equals(a.getCompte());
	}


	@Override
	public String toString(){
		return this.getCompte().toString();
	}

}
