package metier;


/**
 * La classe Lieu est une classe abstraîte qui représente les différents lieus dans lesquels peut être placé un Vélo.
 * @see Detruit
 * @see Garage
 * @see Sortie
 * @author KerGeek
 */
public abstract class Lieu {

	//Attributs

	/**
	 * L'identifiant d'un Lieu est unique. Une fois attribué, il ne doit pas être modifié.
	 * @see Lieu#getId()
	 * @see Lieu#setId(String)
	 */
	protected String id;
	
	/**
	 * L'adresse d'un Lieu.
	 * @see Lieu#getAdresse()
	 * @see Lieu#setAdresse(String)
	 */
	protected String adresse;
	
	/**
	 * Le nombre maximum de vélo que ce Lieu peut contenir . {@link Sortie} et {@link Detruit} n'ont pas cette limite,
	 * leur attribut capacite vaut 0.
	 * @see Lieu#getCapacite()
	 * @see Lieu#setCapacite(int)
	 */
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
	
	/**
	 * @return {@link Lieu#id}
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Initialise {@link Lieu#id}.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return {@link Lieu#adresse}
	 */
	public String getAdresse() {
		return adresse;
	}
	
	/**
	 * Initialise {@link Lieu#adresse}.
	 * @param adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	/**
	 * @return {@link Lieu#capacite}.
	 */
	public int getCapacite() {
		return capacite;
	}
	
	/**
	 * Initialise  {@link Lieu#capacite}.
	 * @param capacite
	 */
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	//Méthodes
	
	/**
	 * Retire un {@link Velo} de ce Lieu pour le placer en {@link Sortie}.
	 * @param velo
	 * @see Velo#setLieu(Lieu)
	 * @see Sortie
	 */
	public void enleverVelo(Velo velo){
		velo.setLieu(Sortie.getInstance());
	}

	/**
	 * place un {@link Velo} dans ce Lieu.
	 * @param velo
	 * @see Velo#setLieu(Lieu)
	 */
	public void ajouterVelo(Velo velo){
		velo.setLieu(this);
	}

	/**
	 * Vérifie l'égalité entre deux instances de la classe Lieu en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe Lieu ont les mêmes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
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
	
	/**
	 * @return {@link Lieu#id} suivi de {@link Lieu#adresse}
	 */
	@Override
	public String toString(){
		return this.getId() +" - "+ this.getAdresse();
	}


}
