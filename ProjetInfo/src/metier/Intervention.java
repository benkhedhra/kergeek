package metier;

import java.sql.Date;

/**
 * La classe Intervention repr�sente les interventions effectu�es par un {@link Technicien}.
 * @author KerGeek
 */
public class Intervention {
	
	//Attributs
	
	/**
	 * L'identifiant d'une Intervention est unique. Une fois attribu�, il ne doit pas �tre modifi�.
	 * @see Intervention#getId()
	 * @see Intervention#setId(String)
	 */
	private String id;
	
	/**
	 * Le {@link Velo} concern� par l'Intervention.
	 * @see Intervention#getVelo()
	 * @see Intervention#setVelo(Velo)
	 */
	private Velo velo;
	
	/**
	 * La date de l'Intervention.
	 * @see Intervention#getDate()
	 * @see Intervention#setDate(Date)
	 */
	private Date date;
	
	/**
	 * Le {@link TypeIntervention} de l'Intervention.
	 * @see Intervention#getTypeIntervention()
	 * @see Intervention#setTypeIntervention(TypeIntervention)
	 */
	private TypeIntervention typeIntervention;
	
	
	
	
	//Constructeurs
	
	/**
	 * Constructeur par d�faut d'une Intervention.
	 */
	public Intervention() {
		super();
	}
	
	/**
	 * Cr�ation d'un Emprunt � partir des �l�ments suivants : {@link Intervention#velo} et {@link Intervention#date}.
	 * @param velo
	 * @param date
	 */
	public Intervention(Velo velo, Date date) {
		super();
		this.setVelo(velo);
		this.setDate(date);
		this.setTypeIntervention(null);
	}

	/**
	 * Cr�ation d'un Emprunt � partir des �l�ments suivants : {@link Intervention#velo}, {@link Intervention#date} et {@link Intervention#typeIntervention}.
	 * @param velo
	 * @param date
	 * @param typeIntervention
	 */
	public Intervention(Velo velo,Date date, TypeIntervention typeIntervention) {
		super();
		this.setVelo(velo);
		this.setDate(date);
		this.setTypeIntervention(typeIntervention);
	}
	
	

	//Accesseurs et Mutateurs

	/**
	 * @return {@link Intervention#id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Initialise  {@link Intervention#id}.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return {@link Intervention#velo}
	 */
	public Velo getVelo() {
		return velo;
	}

	/**
	 * Initialise {@link Intervention#velo}
	 * @param velo
	 */
	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	/**
	 * @return {@link Intervention#date}
	 */
	public java.sql.Date getDate() {
		return date;
	}
	
	/**
	 * Initialise {@link Intervention#date}.
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return {@link Intervention#typeIntervention}
	 */
	public TypeIntervention getTypeIntervention() {
		return typeIntervention;
	}
	
	/**
	 * Initialise {@link Intervention#typeIntervention}
	 * @param typeIntervention
	 */
	public void setTypeIntervention(TypeIntervention typeIntervention) {
		this.typeIntervention = typeIntervention;
	}


	//M�thodes
	
	/**
	 * V�rifie l'�galit� entre deux instances de la classe Intervention en comparant les valeurs de leurs attributs respectifs.
	 * @return un bool�en
	 * qui vaut vrai si les deux instances de la classe compte ont les m�me valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		Intervention i =(Intervention) o;
		Boolean a =false;
		if(this.getId() == null){
			a = i.getId() == null;
		}
		else{
			a = this.getId().equals(i.getId());
		}
		return a && (this.getVelo().equals(i.getVelo())) && (this.getDate().equals(i.getDate())) && (this.getTypeIntervention().equals(i.getTypeIntervention()));
	}

	
	

}
