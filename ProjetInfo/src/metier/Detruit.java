package metier;


/**La classe Detruit utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle représente le lieu (imaginaire) où se trouve les vélos une fois qu'ils sont retirés du parc 
 * (lorsqu'il trop endommagés pour continuer à être utilisés).
 * @see Lieu
 * @see Lieu#ID_DETRUIT
 * @see Lieu#ADRESSE_DETRUIT
 * @author KerGeek
 */

public class Detruit extends Lieu{

	//Attribut

	private static Detruit detruit = null;
	
	//Constructeur
	
	/**
	 * Création de l'instance de la classe Detruit à partir des constantes définit dans la classe mère {@link Lieu}
	 * @see Lieu#ID_DETRUIT
	 * @see Lieu#ADRESSE_DETRUIT
	 */
	private Detruit() {
		this.setId(ID_DETRUIT);
		this.setAdresse(ADRESSE_DETRUIT);
	}


	//Méthode
	
	/**
	 * @return l'unique instance de la classe Sortie.
	 */
	public static Detruit getInstance(){
		if (detruit == null){
			detruit = new Detruit();
		}
		return detruit;
	}

		
}
