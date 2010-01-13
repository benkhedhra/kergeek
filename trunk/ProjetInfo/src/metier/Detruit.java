package metier;


/**La classe Detruit utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle repr�sente le lieu (imaginaire) o� se trouve les v�los une fois qu'ils sont retir�s du parc 
 * (lorsqu'il trop endommag�s pour continuer � �tre utilis�s).
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
	 * Cr�ation de l'instance de la classe Detruit � partir des constantes d�finit dans la classe m�re {@link Lieu}
	 * @see Lieu#ID_DETRUIT
	 * @see Lieu#ADRESSE_DETRUIT
	 */
	private Detruit() {
		this.setId(ID_DETRUIT);
		this.setAdresse(ADRESSE_DETRUIT);
	}


	//M�thode
	
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
