package metier;

/**
 * 
 * @author KerGeek
 */
public class Station extends Lieu {
	
	//Attributs
	
	public static final float TAUX_OCCUPATION_MIN = 0.1f;
	public static final float TAUX_OCCUPATION_MAX = 0.8f;
	
	
	//Constructeur
	
	
	public Station() {
		super();
	}
	
	public Station(String adresse,int capacite) {
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	public Station(String id, String adresse,int capacite) {
		this.setId(id);
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	//Méthodes
	
	public String toString(){
		return this.getId() +" - "+ this.getAdresse();
	}
	
}
