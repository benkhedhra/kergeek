package metier;

/**La classe Sortie utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle représente le lieu (imaginaire) où se trouve les vélos lorsqu'ils sont empruntés par les utilisateurs.
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
	 * Création de l'instance de la classe Sortie à partir des constantes définit dans la classe mère {@link Lieu}
	 * @see Lieu#ID_SORTIE
	 * @see Lieu#ADRESSE_SORTIE
	 */
	public Sortie() {
		this.setId(Lieu.ID_SORTIE);
		this.setAdresse(Lieu.ADRESSE_SORTIE);
	}
	
	//Méthode
	
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
