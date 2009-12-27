package metier;


/**La classe Garage utilise le pattern singleton garantissant l'existence d'une seule instance du Garage.
 * @see Lieu
 * @author KerGeek
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


	//Méthodes
	
	public static Garage getInstance(){
		if (garage == null){
			garage = new Garage();
		}
		return garage;
	}

		
}
