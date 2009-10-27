package metier;

public class Compte {
	
	//Attributs
	private String id;
	private String motDePasse;
	private Boolean actif;
	private int type;
	public static int NUMERO_DERNIER_ID_CREE;
	
	//Constante
	
	public static final int TYPE_ADMINISTRATEUR = 1;
	public static final int TYPE_TECHNICIEN = 2;
	public static final int TYPE_UTILISATEUR = 3;
	
	
	//Constructeur
	
	public Compte(String id, String motDePasse, Boolean actif, int type) {
		super();
		this.setId(id);
		this.setMotDePasse(motDePasse);
		this.setActif(actif);
		this.setType(type);
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
}
