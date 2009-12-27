package metier;


/**La classe Detruit utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle représente le lieu (imaginaire) o se trouve les vélos une fois qu'ils sont retirés du parc 
 * (lorsqu'il trop endommagés pour continuer à être utilisés).
 * @see Lieu
 * @author KerGeek
 */

public class Detruit extends Lieu{

	//Attributs

	private static Detruit detruit = null;
	
	//Constructeurs
	
	private Detruit() {
		this.setId(ID_DETRUIT);
		this.setAdresse(ADRESSE_DETRUIT);
	}


	//Méthodes
	
	public static Detruit getInstance(){
		if (detruit == null){
			detruit = new Detruit();
		}
		return detruit;
	}

		
}
