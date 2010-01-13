package metier;

/**La classe Sortie utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle repr�sente le lieu (imaginaire) o� se trouve les v�los lorsqu'ils sont emprunt�s par les utilisateurs.
 * @see Lieu
 * @see Lieu#ID_SORTIE
 * @see Lieu#ADRESSE_SORTIE
 * @author KerGeek
 */

public class Sortie extends Lieu {

	//Attribut
	
	private static Sortie sortie = null;
	
	
	//Constructeur 
	
	/**
	 * Cr�ation de l'instance de la classe Sortie � partir des constantes d�finit dans la classe m�re {@link Lieu}
	 * @see Lieu#ID_SORTIE
	 * @see Lieu#ADRESSE_SORTIE
	 */
	public Sortie() {
		this.setId(Lieu.ID_SORTIE);
		this.setAdresse(Lieu.ADRESSE_SORTIE);
	}
	
	//M�thode
	
	/**
	 * @return l'unique instance de la classe Sortie.
	 */
	public static Sortie getInstance(){
		if (sortie == null){
			sortie = new Sortie();
		}
		return sortie;
	}
	
	

}
