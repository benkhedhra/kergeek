package metier;

import java.sql.Date;

/**
 * La classe DemandeAssignation repr�sente les demandes d'assignation fa�tes par un {@link Administrateur} lorsqu'il y a trop
 *  ou trop peu de v�lo dans un {@link Lieu}. Le syt�me d�terminera ensuite s'il s'agit d'un ajout ou d'un retrait.
 *  <br> Une demande d'assignation de v�lo au {@link Garage} signifie qu'il faut ajouter des v�los au parc.
 * @see Administrateur#demanderAssignation(int, Lieu)
 * @author KerGeek
 */
public class DemandeAssignation {

	//Attributs

	/**
	 * L'identifiant d'une demandeAssignation est unique. Une fois attribu�, il ne doit pas �tre modifi�
	 * @see DemandeAssignation#getId()
	 * @see DemandeAssignation#setId(String)
	 */
	private String id;

	/**
	 * La date � laquelle la DemandeAssignation � �t� fa�te
	 * @see DemandeAssignation#getDate()
	 * @see DemandeAssignation#setDate(Date)
	 */
	private Date date;

	/**
	 * Initialiser � faux, le bool�en priseEnCharge passe � vrai lorsque la DemandeAssignation
	 * @see DemandeAssignation#isPriseEnCharge()
	 * @see DemandeAssignation#setPriseEnCharge(boolean)
	 * @see UtilitaireDate
	 */
	private boolean priseEnCharge;

	/**
	 * Le nombre de v�los souh�a�t� dans le lieu, qui sera compar� avec le nombre de v�los dans le lieu en question
	 * @see DemandeAssignation#getNombreVelosVoulusDansLieu()
	 * @see DemandeAssignation#setNombreVelosVoulusDansLieu(int)
	 */
	private int nombreVelosVoulusDansLieu;

	/**
	 * Le lieu o� la demande d'assignation est fa�te
	 * @see DemandeAssignation#getLieu()
	 * @see DemandeAssignation#setLieu(Lieu)
	 * @see Lieu
	 */
	private Lieu lieu;


	//Constructeurs

	/**
	 * Cr�ation d'une DemandeAssignation � partir d'un {@link DemandeAssignation#nombreVelosVoulusDansLieu}
	 *  et d'un {@link DemandeAssignation#lieu}, la {@link DemandeAssignation#date} est initialis�e � la date syst�me
	 *  et la nouvelle DemandeAssignation n'est pas encore {@link DemandeAssignation#priseEnCharge} .
	 */
	public DemandeAssignation(int nombre, Lieu lieu) {
		this.setDate(UtilitaireDate.dateCourante());
		this.setPriseEnCharge(false);
		this.setNombreVelosVoulusDansLieu(nombre);
		this.setLieu(lieu);
	}

	/**
	 * Constructeur vide d'une DemandeAssignation.
	 */
	public DemandeAssignation(){
		super();
	}


	// Accesseurs

	/**
	 * @return l'{@link DemandeAssignation#id} du compte
	 */
	public String getId() {
		return id;
	}

	/**
	 * Initialise l'{@link DemandeAssignation#id} de la DemandeAssignation
	 * @param id
	 * l'identifiant de la DemandeAssignation
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return l'{@link DemandeAssignation#date} de la DemandeAssignation
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Initialise l'{@link DemandeAssignation#date} de la DemandeAssignation
	 * @param date
	 * la date de la DemandeAssignation
	 */
	public void setDate(Date date) {
		this.date = date;
	}


	public Boolean isPriseEnCharge() {
		return priseEnCharge;
	}


	public void setPriseEnCharge(boolean priseEnCharge) {
		this.priseEnCharge = priseEnCharge;
	}

	/**
	 * @return le {@link DemandeAssignation#nombreVelosVoulusDansLieu} de la DemandeAssignation
	 */
	public int getNombreVelosVoulusDansLieu() {
		return nombreVelosVoulusDansLieu;
	}

	/**
	 * Initialise le {@link DemandeAssignation#nombreVelosVoulusDansLieu} de la DemandeAssignation
	 * @param nombreVelos
	 * le nombre de v�los souha�t� dans le lieu 
	 */
	public void setNombreVelosVoulusDansLieu(int nombreVelos) {
		this.nombreVelosVoulusDansLieu = nombreVelos;
	}

	/**
	 * @return le {@link DemandeAssignation#lieu} de la DemandeAssignation
	 * @see Lieu
	 */
	public Lieu getLieu() {
		return lieu;
	}

	/**
	 * Initialise le {@link DemandeAssignation#lieu} de la DemandeAssignation
	 * @param lieu
	 * le lieu concern� par la DemandeAssignation
	 * @see Lieu
	 */
	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}


	//M�thode 

	/**
	 * V�rifie l'�galit� entre deux instances de la classe DemandeAssignation en comparant les valeurs de leurs attributs respectifs.
	 * @return un bool�en
	 * qui vaut vrai si les deux instances de la classe compte ont les m�me valeurs pour chacun de leurs attributs,
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
