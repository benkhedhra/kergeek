package metier;


public class Station extends Lieu {
	
	//Attributs
	
	public static final float TAUX_OCCUPATION_MIN = 1/10;
	public static final float TAUX_OCCUPATION_MAX = 5/10;
	
	
	//Constructeur
	
	
	public Station() {
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
	
	//Methodes
	
	public String toString(){
		return this.getId();
	}


}
