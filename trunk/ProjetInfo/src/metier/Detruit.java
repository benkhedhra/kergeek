package metier;


/**La classe Detruit utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle repr�sente le lieu (imaginaire) o� se trouve les v�los une fois qu'ils sont retir�s du parc 
 * (lorsqu'il trop endommag�s pour continuer � �tre utilis�s).
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


	//M�thodes
	
	public static Detruit getInstance(){
		if (detruit == null){
			detruit = new Detruit();
		}
		return detruit;
	}

		
}
