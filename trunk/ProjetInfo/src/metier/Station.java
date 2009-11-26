package metier;


public class Station extends Lieu {
	
	//Attributs
	
	public static final double TAUX_OCCUPATION_MIN = 0.1;
	public static final double TAUX_OCCUPATION_MAX = 0.5;
	
	
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
	
	//Methodes
	
	public String toString(){
		return this.getId() + this.getAdresse();
	}


}
