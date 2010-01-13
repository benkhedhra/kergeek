package metier;

/**
 * 
 * @author KerGeek
 */
public class Station extends Lieu {
	
	//Constatntes
	
	/**
	 * Taux d'occupation de la Station en deçà duquel un Administrateur envoie und {@link DemandeAssignation} concernant celle-ci.
	 */
	public static final float TAUX_OCCUPATION_MIN = 0.2f;
	
	/**
	 * Taux d'occupation de la Station au delà duquel un Administrateur envoie und {@link DemandeAssignation} concernant celle-ci.
	 */
	public static final float TAUX_OCCUPATION_MAX = 0.8f;
	
	
	//Constructeur
	
	/**
	 * Constructeur par défaut d'une Station.
	 */
	public Station() {
		super();
	}
	
	/**
	 * Création d'un Emprunt à partir des éléments suivants : {@link Lieu#adresse} et {@link Lieu#capacite},
	 * @param adresse
	 * @param capacite
	 */
	public Station(String adresse,int capacite) {
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	
}
