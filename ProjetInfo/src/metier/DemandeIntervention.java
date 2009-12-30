package metier;

import java.sql.Date;

/**
 * La classe DemandeIntervention repr�sente les demandes d'intervention fa�tes par un {@link Utilisateur} 
 * lorsqu'il rend un v�lo apr�s un emprunt court.
 * @see Utilisateur#rendreVelo(Station)
 * @see Emprunt
 * @see Intervention
 * @author KerGeek
 */
public class DemandeIntervention {

	//Attributs

	/**
	 * L'identifiant d'une DemandeIntervention est unique. Une fois attribu�, il ne doit pas �tre modifi�.
	 * @see DemandeIntervention#getId()
	 * @see DemandeIntervention#setId(String)
	 */
	private String id;
	
	/**
	 * La date � laquelle la DemandeIntervention � �t� fa�te.
	 * @see DemandeIntervention#getDate()
	 * @see DemandeIntervention#setDate(Date)
	 */
	private Date date;
	
	/**
	 * Le {@link Velo} concern� par la DemandeIntervention
	 * @see DemandeIntervention#getVelo()
	 * @see DemandeIntervention#setVelo(Velo)
	 */
	private Velo velo;
	
	/**
	 * L'{@link Utilisateur} � l'origine de la DemandeIntervention.
	 */
	private Utilisateur utilisateur;
	
	/**
	 * L'intervention associ�e � la DemandeIntervention lorsque celle ci est prise en charge par un {@link Technicien}.
	 * @see DemandeIntervention#prendreEnCharge(TypeIntervention)
	 */
	private Intervention intervention;

	//Constructeurs

	/**
	 * Constructeur par d�faut d'une DemandeIntervention.
	 */
	public DemandeIntervention() {
		super();
	}
	
	/**
	 * Cr�ation d'une DemandeIntervention � partir d'un {@link DemandeIntervention#utilisateur}
	 * et d'un {@link DemandeIntervention#velo} 
	 * @param utilisateur 
	 * @param velo
	 */
	public DemandeIntervention(Utilisateur utilisateur , Velo velo) {
		super();
		this.setUtilisateur(utilisateur);
		this.setVelo(velo);
	}
	
	
	//Accesseurs et Mutateurs
	
	/**
	 * @return {@link DemandeIntervention#id}
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Initialise {@link DemandeIntervention#id}
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return {@link DemandeIntervention#date}
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Initialise la {@link DemandeIntervention#date}
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/** 
	 * @return {@link DemandeIntervention#velo}
	 */
	public Velo getVelo() {
		return velo;
	}
	
	/**
	 * Initialise le {@link DemandeIntervention#velo}
	 * @param velo
	 */
	public void setVelo(Velo velo) {
		this.velo = velo;
	}
	
	/**
	 * @return {@link DemandeIntervention#utilisateur}
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	/**
	 * Initialise le {@link DemandeIntervention#utilisateur}
	 * @param utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	/**
	 * @return {@link DemandeIntervention#intervention}
	 * qui vaut <code>null</code> si la DemandeIntervention n'a pas �t� prise en charge.
	 */
	public Intervention getIntervention() {
		return intervention;
	}
	
	/**
	 * Initialise la {@link DemandeIntervention#intervention}
	 * @param intervention
	 * @see DemandeIntervention#prendreEnCharge(TypeIntervention)
	 */
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	
	
	//M�thodes
	
	/**
	 * Associe une {@link Intervention} dont la date est la date courante � la DemandeIntervention
	 * @return intervention
	 * l'intervention associ�e � la DemandeIntervention
	 * @param typeIntervention
	 * le type de l'intervention associ�e � la DemandeIntervention
	 * @see UtilitaireDate#dateCourante()
	 */
	public Intervention prendreEnCharge(TypeIntervention typeIntervention){
		Intervention intervention = new Intervention(this.getVelo(), UtilitaireDate.dateCourante(), typeIntervention);
		this.setIntervention(intervention);
		return intervention;
	}
	
	/**
	 * V�rifie l'�galit� entre deux instances de la classe DemandeIntervention en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe DemandeIntervention ont les m�mes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		DemandeIntervention d = (DemandeIntervention) o;
		Boolean a =false;
		Boolean b =false;
		if(this.getId() == null){
			a = d.getId() == null;
		}
		else{
			a =this.getId().equals(d.getId());
		}
		if(this.getIntervention() == null){
			a = d.getIntervention() == null;
		}
		else{
			a =this.getIntervention().equals(d.getIntervention());
		}
		return a && b && (this.getDate().equals(d.getDate())) && (this.getVelo().equals(d.getVelo()))&& (this.getUtilisateur().equals(d.getUtilisateur()));
	}
	
}
