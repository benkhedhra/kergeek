package metier;

/** 
 * Administrateur est la classe representant un administrateur du parc a velos.
 * Un administrateur est caracterise par un compte.
 * 
 * @see Compte
 * @see Utilisateur
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
	 */

	public Compte creerCompte(int type, String adresseEmail){
		Compte c = new Compte(type, adresseEmail);
		return c;
	}

	public Utilisateur creerUtilisateur(Compte compte, String nom, String prenom, String adressePostale){
		Utilisateur u = new Utilisateur(compte, nom, prenom, adressePostale);
		return u;
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
	
}
