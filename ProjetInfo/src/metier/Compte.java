package metier;

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
		this.setMotDePasse("test");
		this.setActif(true);
		this.setType(type);
	}

	public Compte(int type, String adresseEmail) {
		super();
		this.setMotDePasse("test");
		this.setActif(true);
		this.setType(type);
		this.setAdresseEmail(adresseEmail);
	}
	
	public Compte(int type, String adresseEmail, boolean actif) {
		super();
		this.setMotDePasse("test");
		this.setActif(true);
		this.setType(type);
		this.setAdresseEmail(adresseEmail);
	}

	public Compte() {
		super();
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

	public String getTypeLettre() {
		String lettre = null;
		switch(this.getType()){
		case TYPE_ADMINISTRATEUR : lettre = "a";
		case TYPE_TECHNICIEN : lettre = "t";
		case TYPE_UTILISATEUR : lettre = "u";
		}
		return lettre;
	}

}
