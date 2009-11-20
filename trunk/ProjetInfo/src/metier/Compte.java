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

	public void genererMotDePasse() {
		String bibliotheque = "abcdefghijklmnopqrstuvwxyz1234567890";
		String motDePasse = null;
		for(int x=0; x<6; x++){
			int i = (int) Math.floor(Math.random() * bibliotheque.length()-1);
			motDePasse += bibliotheque.charAt(i);
		}
		this.setMotDePasse(motDePasse);
	}

}
