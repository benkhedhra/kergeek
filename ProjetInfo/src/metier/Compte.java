package metier;

import java.util.Random;

/**
 * La classe compte repr�sente le compte d'un Administrateur, d'un Technicien ou d'un Utilisateur,
 *  qui contient les donn�es relatives � ces diff�rents acteurs.
 * @see Administrateur
 * @see Utilisateur
 * @see Technicien
 * @author KerGeek
 */

public class Compte {

	
	
	//Attributs
	/**
	 * L'identifiant d'un compte est unique. Une fois attribu�, il ne doit pas etre modifi�.
	 */
	private String id;
	
	/**
	 * @see Compte#getMotDePasse()
	 * @see Compte#setMotDePasse(String)
	 * @see Compte#genererMotDePasse()
	 */
	private String motDePasse;
	
	/**
	 * Un compte devient inactif lorsqu'il est r�sili�.
	 * @see Compte#isActif()
	 * @see Compte#setActif(Boolean)
	 * @see Administrateur#resilierCompte(Compte)
	 */
	private Boolean actif;
	
	/**
	 * Le type d'un compte nous renseigne sur la qualit� de celui qui le poss�de, � si c'est un {@link Administrateur},
	 *  un {@link Technicien} ou un {@link Utilisateur}
	 * @see Compte#TYPE_ADMINISTRATEUR
	 * @see Compte#TYPE_TECHNICIEN
	 * @see Compte#TYPE_UTILISATEUR
	 */
	private int type;
	
	/**
	 * L'adresseEmail d'un compte nous permet de contacter le propri�taire du compte si besoin.
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
	 * Constructeur par d�faut d'un compte.
	 */
	public Compte() {
	}
	
	/**
	 * Cr�ation d'un compte � partir d'un {@link Compte#type} et d'une {@link Compte#adresseEmail}, 
	 * automatiquement initialis� actif et dont le {@link Compte#motDePasse} est g�n�r� automatiquement.
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
	 * @return true si le compte est actif, false sinon (c'est-�-dire si le compte � �t� r�sili�)
	 * @see Compte#actif
	 * @see Administrateur#resilierCompte(Compte)
	 */
	public Boolean isActif() {
		return actif;
	}
	
	/**
	 * Active ou d�sactive le compte.
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
	 * @return l'{@link Compte#adresseEmail} du propri�taire du compte
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

	//M�thodes
	/**
	 * G�n�re automatiquement un mot de passe pour le compte en question et initialise l'attribut motDePasse de celui-ci.
	 * 
	 * Le mot de passe g�n�r� comporte 6 caract�res minuscules choisis al�atoirement entre dans l'alphabet 
	 * auquel s'ajoute les 10 chiffres arabes.
	 * <br><br>On g�nere un nombre al�atoire entre 0 et 1, qu'on multiplie par la taille de la biblioth�que de caract�res
	 *  dans laquelle on pioche pour g�n�r� le mot de passe, on prends sa partie enti�re, et on r�cup�re le caract�re
	 *  correspondant � cet indice dans notre biblioth�que. On r�p�te cette action 6 fois en concate�nant les caract�res
	 *  au fur et � mesure de leur obtention pour former le motDePasse.
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
	 * V�rifie l'�galit� entre deux instances de la classe Compte en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe Compte ont les m�mes valeurs pour chacun de leurs attributs,
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
