package metier;


import java.sql.Date;

import metier.exceptionsMetier.PasDeDateRetourException;

/**
 * La classe Emprunt représente les emprunts effectués par les utilisateurs du parc  vélos.
 * @author KerGeek
 */
public class Emprunt {
	/**
	 * L'identifiant d'un Emprunt est unique. Une fois attribué, il ne doit pas être modifié.
	 * @see Emprunt#getId()
	 * @see Emprunt#setId(String)
	 */
	private String id;
	
	/**
	 * L'{@link Utilisateur} à l'origine de l'Emprunt.
	 * @see Emprunt#getUtilisateur()
	 * @see Emprunt#setUtilisateur(Utilisateur)
	 */
	private Utilisateur utilisateur;
	
	/**
	 * Le {@link Velo} emprunté.
	 * @see Emprunt#getVelo()
	 * @see Emprunt#setVelo(Velo)
	 */
	private Velo velo;
	
	/**
	 * La date à laquelle le {@link Emprunt#velo} a été emprunté.
	 * @see Emprunt#getDateEmprunt()
	 * @see Emprunt#setDateEmprunt(Date)
	 */
	private Date dateEmprunt;
	
	/**
	 * La date à laquelle le {@link Emprunt#velo} a été rendu.
	 * @see Emprunt#getDateRetour()
	 * @see Emprunt#setDateRetour(Date)
	 */
	private Date dateRetour;
	
	/**
	 * Le lieu où le {@link Emprunt#velo} a été emprunté.
	 *@see Emprunt#getStationEmprunt()
	 *@see Emprunt#setStationEmprunt(Lieu)
	 */
	private Station stationEmprunt;
	
	/**
	 * Le lieu où le {@link Emprunt#velo} a été rendu.
	 * @see Emprunt#getStationRetour()
	 * @see Emprunt#setStationRetour(Lieu)
	 */
	private Station stationRetour;


	//Constantes
	
	/**
	 * Temps au delà duquel un emprunt est cosidéré comme long, c'est-à-dire supérieur à 2 heures.
	 */
	public static long TPS_EMPRUNT_MAX = 7200; //2h
	
	/**
	 * Temps en deçà duquel un emprunt est cosidéré comme court, c'est-à-dire inférieur à 2 minutes.
	 */
	public static long TPS_EMPRUNT_MIN = 120; //2min


	//Constructeurs

	/**
	 * Constructeur par défaut d'un Emprunt.
	 */
	public Emprunt(){
	}
	
	/**
	 * Création d'un Emprunt à partir des éléments suivants : {@link Emprunt#utilisateur}, {@link Emprunt#velo},
	 * {@link Emprunt#dateEmprunt} et {@link Emprunt#stationEmprunt}. 
	 * @param utilisateur
	 * @param velo
	 * @param dateEmprunt
	 * @param stationEmprunt
	 */
	public Emprunt(Utilisateur utilisateur, Velo velo, Date dateEmprunt, Station stationEmprunt) {
		this.setUtilisateur(utilisateur);
		this.setVelo(velo);
		this.setDateEmprunt(dateEmprunt);
		this.setStationEmprunt(stationEmprunt);
	}
	
	/**
	 * Création d'un Emprunt à partir des éléments suivants : {@link Emprunt#utilisateur}, {@link Emprunt#velo},
	 * {@link Emprunt#dateEmprunt}, {@link Emprunt#stationEmprunt}, {@link Emprunt#dateRetour} et {@link Emprunt#stationRetour}.
	 * @param utilisateur
	 * @param velo
	 * @param dateEmprunt
	 * @param stationEmprunt
	 * @param dateRetour
	 * @param stationRetour
	 */
	public Emprunt(Utilisateur utilisateur, Velo velo, Date dateEmprunt, Station stationEmprunt, Date dateRetour, Station stationRetour) {
		this.setUtilisateur(utilisateur);
		this.setVelo(velo);
		this.setDateEmprunt(dateEmprunt);
		this.setStationEmprunt(stationEmprunt);
		this.setDateRetour(dateRetour);
		this.setStationRetour(stationRetour);
	}



	/**
	 * Création d'un Emprunt à partir d'un autre Emprunt. Les deux emprunts ont alors les mêmes attributs.
	 * @param emprunt 
	 * @param utilisateur
	 * @param velo
	 * @param dateEmprunt
	 * @param stationEmprunt
	 * @param dateRetour
	 * @param stationRetour
	 */
	public Emprunt(Emprunt emprunt) {
		this.id = emprunt.getId();
		this.utilisateur = emprunt.getUtilisateur();
		this.velo = emprunt.getVelo();
		this.dateEmprunt = emprunt.getDateEmprunt();
		this.dateRetour = emprunt.getDateRetour();
		this.stationEmprunt = emprunt.getStationEmprunt();
		this.stationRetour = emprunt.getStationRetour();
	}



	//Accesseurs et Mutateurs

	/**
	 * @return {@link Emprunt#id}
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Initialise {@link DemandeIntervention#id}.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return {@link Emprunt#utilisateur}
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	/**
	 * Initialise {@link Emprunt#utilisateur}.
	 * @param u
	 */
	public void setUtilisateur(Utilisateur u) {
		this.utilisateur = u;
	}
	
	/**
	 * @return {@link Emprunt#velo}
	 */
	public Velo getVelo() {
		return velo;
	}
	
	/**
	 * Initialise {@link Emprunt#velo}.
	 * @param velo
	 */
	public void setVelo(Velo velo) {
		this.velo = velo;
	}
	
	/**
	 * @return {@link Emprunt#dateEmprunt}
	 */
	public Date getDateEmprunt() {
		return dateEmprunt;
	}
	
	/**
	 * Initialise {@link Emprunt#dateEmprunt}.
	 * @param dateEmprunt
	 */
	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	/**
	 * @return {@link Emprunt#dateRetour}
	 */
	public Date getDateRetour() {
		return dateRetour;
	}
	
	/**
	 * Initialise {@link Emprunt#dateRetour}.
	 * @param dateRetour
	 */
	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}
	
	/**
	 * @return {@link Emprunt#stationEmprunt}
	 */
	public Station getStationEmprunt() {
		return stationEmprunt;
	}
	
	/**
	 * Initialise {@link Emprunt#stationEmprunt}.
	 * @param stationEmprunt
	 */
	public void setStationEmprunt(Station stationEmprunt) {
		this.stationEmprunt = stationEmprunt;
	}
	
	/**
	 * @return {@link Emprunt#stationRetour}
	 */
	public Station getStationRetour() {
		return stationRetour;
	}
	
	/**
	 * Initialise {@link Emprunt#stationRetour}.
	 * @param stationRetour
	 */
	public void setStationRetour(Station stationRetour) {
		this.stationRetour = stationRetour;
	}


	//Méthodes

	/**
	 * @return la durée de l'Emprunt en faisant la différence entre {@link Emprunt#dateRetour} et {@link Emprunt#dateRetour}.
	 * @throws PasDeDateRetourException
	 */
	public long getTempsEmprunt() throws PasDeDateRetourException{
		long diff;
		if(this.getDateRetour() != null){

			diff = (this.getDateRetour().getTime() - this.getDateEmprunt().getTime())/1000;
		}
		else{
			throw new PasDeDateRetourException();
		}
		return diff;
	}
	
	/**
	 * Vérifie l'égalité entre deux instances de la classe Emprunt en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe Emprunt ont les mêmes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		Emprunt e =(Emprunt) o;
		Boolean a =false;
		Boolean b = false;
		if(this.getId() == null){
			a = e.getId() == null;
		}
		else{
			a = this.getId().equals(e.getId());
		}
		if(this.getDateRetour() == null){
			b = e.getDateRetour() == null && e.getStationRetour() == null;
		}
		else{
			b = (this.getDateRetour().equals(e.getDateRetour())) && (this.getStationRetour().equals(e.getStationRetour()));
		}
		return a && b && (this.getUtilisateur().equals(e.getUtilisateur()))&& (this.getVelo().equals(e.getVelo())) && (this.getDateEmprunt().equals(e.getDateEmprunt()))  && (this.getStationEmprunt().equals(e.getStationEmprunt()));
	}


}
