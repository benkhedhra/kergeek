package metier;

/**
 * 
 * @author KerGeek
 */
public class Station extends Lieu {
	
	//Constatntes
	
	/**
	 * Taux d'occupation de la Station en de�� duquel un Administrateur envoie und {@link DemandeAssignation} concernant celle-ci.
	 */
	public static final float TAUX_OCCUPATION_MIN = 0.2f;
	
	/**
	 * Taux d'occupation de la Station au del� duquel un Administrateur envoie und {@link DemandeAssignation} concernant celle-ci.
	 */
	public static final float TAUX_OCCUPATION_MAX = 0.8f;
	
	
	//Constructeur
	
	/**
	 * Constructeur par d�faut d'une Station.
	 */
	public Station() {
		super();
	}
	
	/**
	 * Cr�ation d'un Emprunt � partir des �l�ments suivants : {@link Lieu#adresse} et {@link Lieu#capacite},
	 * @param adresse
	 * @param capacite
	 */
	public Station(String adresse,int capacite) {
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	
}
