package metier;


/**La classe Garage est un peu particuliere car il n'existe qu'une seule instance du garage, qui ne 
 * peut etre modifiee (c'est pourquoi on override les setter et on utilise un constructeur prive
 * 
 * @author boris
 *
 */
public class Garage extends Lieu{

	//Attributs

	private static final Garage garage = new Garage();
	
	//Constructeurs
	
	private Garage() {
		super();
		this.setType(TYPE_GARAGE);
		this.setId(ID_GARAGE);
		this.setAdresse(ADRESSE_GARAGE);
		this.setCapacite(CAPACITE_GARAGE);
	}
	
	//Accesseurs

	@Override
	public void setAdresse(String adresse) {
	}

	@Override
	public void setCapacite(int capacite) {
	}

	@Override
	public void setId(String id) {
	}

	@Override
	public void setType(int type) {
	}
	
	//Methodes
	
	public static Garage getInstance(){
		return garage;
	}

		
}
