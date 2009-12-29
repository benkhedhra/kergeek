package metier;

/**La classe Sortie utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle reprÈsente le lieu (imaginaire) où se trouve les vÈlos lorsqu'ils sont empruntÈs par les utilisateurs.
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
	 * CrÈation de l'instance de la classe Sortie ‡ partir des constantes dÈfinit dans la classe mËre {@link Lieu}
	 * @see Lieu#ID_SORTIE
	 * @see Lieu#ADRESSE_SORTIE
	 */
	public Sortie() {
		this.setId(Lieu.ID_SORTIE);
		this.setAdresse(Lieu.ADRESSE_SORTIE);
	}
	
	//MÈthode
	
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
