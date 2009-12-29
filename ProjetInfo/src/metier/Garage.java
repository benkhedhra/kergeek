package metier;


/**La classe Garage utilise le pattern singleton garantissant l'existence d'une seule instance du Garage.
 * @see Lieu
 * @see Lieu#ID_GARAGE
 * @see Lieu#ADRESSE_GARAGE
 * @see Lieu#CAPACITE_GARAGE
 * @author KerGeek
 */

public class Garage extends Lieu{

	//Attribut

	private static Garage garage = null;
	
	//Constructeur
	
	/**
	 * Création de l'instance de la classe Garage à partir des constantes définit dans la classe mère {@link Lieu}
	 * @see Lieu#ID_GARAGE
	 * @see Lieu#ADRESSE_GARAGE
	 * @see Lieu#CAPACITE_GARAGE
	 */
	private Garage() {
		this.setId(ID_GARAGE);
		this.setAdresse(ADRESSE_GARAGE);
		this.setCapacite(CAPACITE_GARAGE);
	}


	//Méthode
	
	/**
	 * @return l'unique instance de la classe Garage.
	 */
	public static Garage getInstance(){
		if (garage == null){
			garage = new Garage();
		}
		return garage;
	}

		
}
