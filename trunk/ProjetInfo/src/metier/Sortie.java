package metier;

/**La classe Sortie utilise le pattern singleton garantissant l'existence d'une seule instance.
 * Elle repr�sente le lieu (imaginaire) o� se trouve les v�los lorsqu'ils sont emprunt�s par les utilisateurs.
 * @author KerGeek
 */

public class Sortie extends Lieu {

	//Attributs
	
	private static Sortie sortie = null;
	
	public Sortie() {
		this.setId(Lieu.ID_SORTIE);
		this.setAdresse(Lieu.ADRESSE_SORTIE);
	}
	
//Methodes
	
	public static Sortie getInstance(){
		if (sortie == null){
			sortie = new Sortie();
		}
		return sortie;
	}
	
	

}
