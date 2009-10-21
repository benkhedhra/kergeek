package metier;

public class Station extends Lieu {
	
	//Attributs
	
	static final float TAUX_OCCUPATION_MIN = 1/10;
	static final float TAUX_OCCUPATION_MAX = 5/10;
	
	
	//Constructeur
	
	public Station(String id, String adresse,int capacite) {
		super();
		this.setType(TYPE_STATION);
		this.setId(id);
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	//Méthodes

	public int calculerTx(String idStation){
		int a = 0;
		return a;
	}



}
