package metier;

import java.util.Random;

public class Compte {

	//Attributs
	private String id;
	private String motDePasse;
	private Boolean actif;
	private int type;
	private String adresseEmail;

	//Constante

	public static final int TYPE_ADMINISTRATEUR = 1;
	public static final int TYPE_TECHNICIEN = 2;
	public static final int TYPE_UTILISATEUR = 3;


	//Constructeur

	public Compte(int type) {
		super();
		this.genererMotDePasse();
		this.setActif(true);
		this.setType(type);
	}

	public Compte(int type, String adresseEmail) {
		super();
		this.genererMotDePasse();
		this.setActif(true);
		this.setType(type);
		this.setAdresseEmail(adresseEmail);
	}

	public Compte(int type, String adresseEmail, boolean actif) {
		super();
		this.genererMotDePasse();
		this.setActif(true);
		this.setType(type);
		this.setAdresseEmail(adresseEmail);
	}

	public Compte() {
	}


	//Accesseurs


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public Boolean isActif() {
		return actif;
	}
	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	//methodes

	public void genererMotDePasse() {
		String bibliotheque = "abcdefghijklmnopqrstuvwxyz1234567890";
		String motDePasse = null;
		Random rd = new Random();
		for(int x=0; x<6; x++){
			int i = rd.nextInt(bibliotheque.length());
			motDePasse += bibliotheque.charAt(i);
		}
		this.setMotDePasse(motDePasse);
	}
	
	@Override
	public boolean equals(Object o) {
		Compte c = (Compte) o;
		return (this.getId() == c.getId()) && (this.getAdresseEmail() == c.getAdresseEmail()) && (this.isActif() == c.isActif())&& (this.getMotDePasse() == c.getMotDePasse()) && (this.getType() == c.getType());
	}
	

}
