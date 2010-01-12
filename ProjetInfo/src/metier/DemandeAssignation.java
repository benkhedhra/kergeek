package metier;

import java.sql.Date;

/**
 * La classe DemandeAssignation représente les demandes d'assignation faîtes par un {@link Administrateur} lorsqu'il y a trop
 * ou trop peu de vélo dans un {@link Lieu}. Le sytème déterminera ensuite s'il s'agit d'un ajout ou d'un retrait.
 * <br> Une demande d'assignation de vélo au {@link Garage} signifie qu'il faut ajouter des vélos au parc.
 * @see Administrateur#demanderAssignation(int, Lieu)
 * @author KerGeek
 */
public class DemandeAssignation {

	//Attributs

	/**
	 * L'identifiant d'une demandeAssignation est unique. Une fois attribué, il ne doit pas ítre modifié
	 * @see DemandeAssignation#getId()
	 * @see DemandeAssignation#setId(String)
	 */
	private String id;

	/**
	 * La date à laquelle la DemandeAssignation à été faite
	 * @see DemandeAssignation#getDate()
	 * @see DemandeAssignation#setDate(Date)
	 */
	private Date date;

	/**
	 * Initialiser à faux, le booléen priseEnCharge passe à vrai lorsque la DemandeAssignation
	 * @see DemandeAssignation#isPriseEnCharge()
	 * @see DemandeAssignation#setPriseEnCharge(boolean)
	 * @see UtilitaireDate
	 */
	private boolean priseEnCharge;

	/**
	 * Le nombre de vélos souhéaîté dans le lieu, qui sera comparé avec le nombre de vélos dans le lieu en question
	 * @see DemandeAssignation#getNombreVelosVoulusDansLieu()
	 * @see DemandeAssignation#setNombreVelosVoulusDansLieu(int)
	 */
	private int nombreVelosVoulusDansLieu;

	/**
	 * Le lieu où la demande d'assignation est faîte
	 * @see DemandeAssignation#getLieu()
	 * @see DemandeAssignation#setLieu(Lieu)
	 * @see Lieu
	 */
	private Lieu lieu;


	//Constructeurs

	/**
	 * Constructeur par défaut d'une DemandeAssignation.
	 */
	public DemandeAssignation(){
		super();
	}
	
	/**
	 * Création d'une DemandeAssignation à partir d'un {@link DemandeAssignation#nombreVelosVoulusDansLieu}
	 * et d'un {@link DemandeAssignation#lieu}, la {@link DemandeAssignation#date} est initialisée à la date système
	 * et la nouvelle DemandeAssignation n'est pas encore {@link DemandeAssignation#priseEnCharge} .
	 * @param nombre
	 * {@link DemandeAssignation#nombreVelosVoulusDansLieu}
	 * @param lieu
	 */
	public DemandeAssignation(int nombre, Lieu lieu) {
		this.setDate(UtilitaireDate.dateCourante());
		this.setPriseEnCharge(false);
		this.setNombreVelosVoulusDansLieu(nombre);
		this.setLieu(lieu);
	}	


	// Accesseurs et Mutateurs

	/**
	 * @return {@link DemandeAssignation#id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Initialise le {@link DemandeAssignation#id}
	 * @param id
	 * l'identifiant de la DemandeAssignation
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return {@link DemandeAssignation#date}
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Initialise la {@link DemandeAssignation#date}
	 * @param date
	 * la date de la DemandeAssignation
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return {@link DemandeAssignation#priseEnCharge}
	 */
	public Boolean isPriseEnCharge() {
		return priseEnCharge;
	}

	/**
	 * Initialise le {@link DemandeAssignation#priseEnCharge}
	 * @param priseEnCharge
	 */
	public void setPriseEnCharge(boolean priseEnCharge) {
		this.priseEnCharge = priseEnCharge;
	}

	/**
	 * @return {@link DemandeAssignation#nombreVelosVoulusDansLieu}
	 */
	public int getNombreVelosVoulusDansLieu() {
		return nombreVelosVoulusDansLieu;
	}

	/**
	 * Initialise le {@link DemandeAssignation#nombreVelosVoulusDansLieu}
	 * @param nombreVelos
	 * le nombre de vélos souhaîté dans le lieu 
	 */
	public void setNombreVelosVoulusDansLieu(int nombreVelos) {
		this.nombreVelosVoulusDansLieu = nombreVelos;
	}

	/**
	 * @return {@link DemandeAssignation#lieu}
	 * @see Lieu
	 */
	public Lieu getLieu() {
		return lieu;
	}

	/**
	 * Initialise le {@link DemandeAssignation#lieu}
	 * @param lieu
	 * le lieu concerné par la DemandeAssignation
	 * @see Lieu
	 */
	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}


	//Méthode 

	/**
	 * Vérifie l'égalité entre deux instances de la classe DemandeAssignation en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe DemandeAssignation ont les mêmes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		DemandeAssignation d = (DemandeAssignation) o;
		Boolean a =false;
		if(this.getId() == null){
			a = d.getId() == null;
		}
		else{
			this.getId().equals(d.getId());
		}
		return a && (this.getDate().equals(d.getDate())) && (this.isPriseEnCharge().equals(d.isPriseEnCharge()))&& (this.getNombreVelosVoulusDansLieu() == d.getNombreVelosVoulusDansLieu()) && (this.getLieu().equals(d.getLieu()));
	}
}
