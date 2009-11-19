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
		super.id = Lieu.ID_GARAGE;
		super.adresse = Lieu.ADRESSE_GARAGE;
		super.capacite = Lieu.CAPACITE_GARAGE;
	}
	
	//Accesseurs


	@Override
	public void setAdresse(String adresse) {
	}

	@Override
	public void setCapacite(int capacite) {
	}

	@Override
	public void setId(String id) {
	}


	//Methodes
	
	public static Garage getInstance(){
		if (garage == null){
			garage = new Garage();
		}
		return garage;
	}

		
}
