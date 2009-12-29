package metier;

/**
 * 
 * @author KerGeek
 */
public class Station extends Lieu {
	
	//Constatntes
	
	/**
	 * Taux d'occupation de la Station en deç‡ duquel un Administrateur envoie und {@link DemandeAssignation} concernant celle-ci.
	 */
	public static final float TAUX_OCCUPATION_MIN = 0.2f;
	
	/**
	 * Taux d'occupation de la Station au del‡ duquel un Administrateur envoie und {@link DemandeAssignation} concernant celle-ci.
	 */
	public static final float TAUX_OCCUPATION_MAX = 0.8f;
	
	
	//Constructeur
	
	/**
	 * Constructeur par dÈfaut d'une Station.
	 */
	public Station() {
		super();
	}
	
	/**
	 * CrÈation d'un Emprunt ‡ partir des ÈlÈments suivants : {@link Lieu#adresse} et {@link Lieu#capacite},
	 * @param adresse
	 * @param capacite
	 */
	public Station(String adresse,int capacite) {
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	
}
