package metier;


/**La classe Garage est un peu particuliere (singleton) car il n'existe qu'une seule instance du garage, qui ne 
 * peut etre modifiee (c'est pourquoi on override les setter et on utilise un constructeur prive)
 * 
 * @author boris
 *
 */

public class Garage extends Lieu{

	//Attributs

	private static Garage garage = null;
	
	//Constructeurs
	
	private Garage() {
		this.setId(ID_GARAGE);
		this.setAdresse(ADRESSE_GARAGE);
		this.setCapacite(CAPACITE_GARAGE);
	}


	//Methodes
	
	public static Garage getInstance(){
		if (garage == null){
			garage = new Garage();
		}
		return garage;
	}

		
}
