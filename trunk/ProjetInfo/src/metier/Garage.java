package metier;



public class Garage extends Lieu{

	//Attributs

	private static final Garage garage = new Garage();
	
	//Constructeurs
	
	public Garage() {
		super();
		this.setType(TYPE_GARAGE);
		this.setId(ID_GARAGE);
		this.setAdresse(ADRESSE_GARAGE);
		this.setCapacite(CAPACITE_GARAGE);
	}
	
	//Accesseurs

	//Methodes
	
	public static Garage getInstance(){
		return garage;
	}

}
