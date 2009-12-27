package metier;


/**
 * 
 * @see Detruit
 * @see Garage
 * @see Sortie
 * @author KerGeek
 */
public abstract class Lieu {

	//Attributs

	protected String id;
	protected String adresse;
	protected int capacite;

	//Constantes

	public static final String ID_GARAGE="0";
	public static final String ADRESSE_GARAGE="pool de vélos";
	public static final int CAPACITE_GARAGE=100;
	
	public static final String ADRESSE_SORTIE="en sortie";
	public static final String ID_SORTIE="-1";
	
	public static final String ADRESSE_DETRUIT="detruit";
	public static final String ID_DETRUIT="-2";

	//pas de constructeur puisqu'il s'agit d'une classe abstraite


	//Accesseurs et Mutateurs

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	//Méthodes
	
	public void enleverVelo(Velo velo){
		velo.setLieu(Sortie.getInstance());
	}

	public void ajouterVelo(Velo velo){
		velo.setLieu(this);
	}


	@Override
	public boolean equals(Object o) {
		Lieu l = (Lieu) o;
		Boolean a =false;
		Boolean b =false;
		if (this.getId() == null){
			a = l.getId() == null;
		}
		else{
			a = this.getId().equals(l.getId());
		}
		if (this instanceof Station){
			b = l instanceof Station;
		}
		else{
			b = (this == Garage.getInstance()) && (l == Garage.getInstance());
		}
		return a && b && (this.getAdresse().equals(l.getAdresse())) && (this.getCapacite() == l.getCapacite());
	}



}
