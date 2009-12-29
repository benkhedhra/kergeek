package metier;

import java.util.Random;

/**
 * La classe compte représente le compte d'un Administrateur, d'un Technicien ou d'un Utilisateur,
 *  qui contient les données relatives à ces différents acteurs.
 * @see Administrateur
 * @see Utilisateur
 * @see Technicien
 * @author KerGeek
 */

public class Compte {

	
	
	//Attributs
	/**
	 * L'identifiant d'un compte est unique. Une fois attribué, il ne doit pas etre modifié.
	 */
	private String id;
	
	/**
	 * @see Compte#getMotDePasse()
	 * @see Compte#setMotDePasse(String)
	 * @see Compte#genererMotDePasse()
	 */
	private String motDePasse;
	
	/**
	 * Un compte devient inactif lorsqu'il est résilié.
	 * @see Compte#isActif()
	 * @see Compte#setActif(Boolean)
	 * @see Administrateur#resilierCompte(Compte)
	 */
	private Boolean actif;
	
	/**
	 * Le type d'un compte nous renseigne sur la qualité de celui qui le possède, à si c'est un {@link Administrateur},
	 *  un {@link Technicien} ou un {@link Utilisateur}
	 * @see Compte#TYPE_ADMINISTRATEUR
	 * @see Compte#TYPE_TECHNICIEN
	 * @see Compte#TYPE_UTILISATEUR
	 */
	private int type;
	
	/**
	 * L'adresseEmail d'un compte nous permet de contacter le propriétaire du compte si besoin.
	 *  @see Compte#getAdresseEmail()
	 *  @see Compte#setAdresseEmail(String)
	 *  @see EnvoieMail  
	 */
	private String adresseEmail;

	
	//Constantes
	
	/**
	 * @see Compte#type
	 */
	public static final int TYPE_ADMINISTRATEUR = 1;
	/**
	 * @see Compte#type
	 */
	public static final int TYPE_TECHNICIEN = 2;
	/**
	 * @see Compte#type
	 */
	public static final int TYPE_UTILISATEUR = 3;


	//Constructeurs

	/**
	 * Constructeur par défaut d'un compte.
	 */
	public Compte() {
	}
	
	/**
	 * Création d'un compte à partir d'un {@link Compte#type} et d'une {@link Compte#adresseEmail}, 
	 * automatiquement initialisé actif et dont le {@link Compte#motDePasse} est généré automatiquement.
	 * @param type
	 * @param adresseEmail
	 * @see Compte#genererMotDePasse()
	 */
	public Compte(int type, String adresseEmail) {
		this.genererMotDePasse();
		this.setActif(true);
		this.setType(type);
		this.setAdresseEmail(adresseEmail);
	}

	
	
	//Accesseurs et Mutateurs

	/**
	 * @return l'{@link Compte#id} du compte
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Initialise l'{@link Compte#id} du compte.
	 * @param id
	 * l'identifiant du compte
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * @return le {@link Compte#motDePasse} du compte.
	 */
	public String getMotDePasse() {
		return motDePasse;
	}
	
	/** 
	 * Initialise le {@link Compte#motDePasse} du compte.
	 * @param motDePasse
	 * le nouveau mot de passe du compte
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	
	/**
	 * @return true si le compte est actif, false sinon (c'est-à-dire si le compte à été résilié)
	 * @see Compte#actif
	 * @see Administrateur#resilierCompte(Compte)
	 */
	public Boolean isActif() {
		return actif;
	}
	
	/**
	 * Active ou désactive le compte.
	 * @param actif
	 * @see Compte#actif
	 * @see Administrateur#resilierCompte(Compte)
	 */
	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	
	
	/**
	 * @return le {@link Compte#type} du compte
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Initialise le {@link Compte#type} du compte
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	
	/**
	 * @return l'{@link Compte#adresseEmail} du propriétaire du compte
	 */
	public String getAdresseEmail() {
		return adresseEmail;
	}
	/**
	 * Initialise l'{@link Compte#adresseEmail} du compte.
	 * @param adresseEmail
	 */
	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	//Méthodes
	/**
	 * Génère automatiquement un mot de passe pour le compte en question et initialise l'attribut motDePasse de celui-ci.
	 * 
	 * Le mot de passe généré comporte 6 caractères minuscules choisis aléatoirement entre dans l'alphabet 
	 * auquel s'ajoute les 10 chiffres arabes.
	 * <br><br>On génere un nombre aléatoire entre 0 et 1, qu'on multiplie par la taille de la bibliothèque de caractères
	 *  dans laquelle on pioche pour généré le mot de passe, on prends sa partie entière, et on récupère le caractère
	 *  correspondant ˆ cet indice dans notre bibliothèque. On répète cette action 6 fois en concateénant les caractères
	 *  au fur et à mesure de leur obtention pour former le motDePasse.
	 * @see Compte#motDePasse
	 * 
	 */
	public void genererMotDePasse() {
		String bibliotheque = "abcdefghijklmnopqrstuvwxyz1234567890";
		String motDePasse="";
		Random rd = new Random();
		for(int x=0; x<6; x++){
			int i = rd.nextInt(bibliotheque.length());
			motDePasse += bibliotheque.charAt(i);
		}
		this.setMotDePasse(motDePasse);
	}

	
	
	/**
	 * Vérifie l'égalité entre deux instances de la classe Compte en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe Compte ont les mêmes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		Compte c = (Compte) o;
		Boolean a = false;
		Boolean b = false;
		if (this.getAdresseEmail() == null){
			a = c.getAdresseEmail() == null;
		}
		else{
			a = this.getAdresseEmail().equals(c.getAdresseEmail());
		}
		
		if (this.getId() == null){
			b = c.getId() == null;
		}
		else{
			b = this.getId().equals(c.getId());
		}
		return a && b  && (this.isActif().equals(c.isActif())) && (this.getMotDePasse().equals(c.getMotDePasse())) && (this.getType() == c.getType());
	}

	
	
	/**
	 * @return l'identifiant du Compte suivi de son adresse email
	 */
	@Override
	public String toString(){
		return this.getId()+" - "+this.getAdresseEmail();
	}

}
