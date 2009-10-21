package metier;

public abstract class Lieu {

	//Attributs
	
	private String id;
	private int type;
	private String adresse;
	private int capacite;

	
	//Constantes
	
	public static final int TYPE_STATION=1;
	public static final int TYPE_GARAGE=2;
	public static final int CAPACITE_GARAGE=1000;
	
	
	//Accesseurs
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	
	//Methodes
	

	public void enleverVelo(Velo velo){
		
	}
	
	public void ajouterVelo(Velo velo){
		
	}

}
