package metier;


import java.sql.Date;

import metier.exceptionsMetier.PasDeDateRetourException;

/**
 * La classe Emprunt repr�sente les emprunts effectu�s par les utilisateurs du parc  v�los.
 * @author KerGeek
 */
public class Emprunt {
	/**
	 * L'identifiant d'un Emprunt est unique. Une fois attribu�, il ne doit pas �tre modifi�.
	 * @see Emprunt#getId()
	 * @see Emprunt#setId(String)
	 */
	private String id;
	
	/**
	 * L'{@link Utilisateur} � l'origine de l'Emprunt.
	 * @see Emprunt#getUtilisateur()
	 * @see Emprunt#setUtilisateur(Utilisateur)
	 */
	private Utilisateur utilisateur;
	
	/**
	 * Le {@link Velo} emprunt�.
	 * @see Emprunt#getVelo()
	 * @see Emprunt#setVelo(Velo)
	 */
	private Velo velo;
	
	/**
	 * La date � laquelle le {@link Emprunt#velo} a �t� emprunt�.
	 * @see Emprunt#getDateEmprunt()
	 * @see Emprunt#setDateEmprunt(Date)
	 */
	private Date dateEmprunt;
	
	/**
	 * La date � laquelle le {@link Emprunt#velo} a �t� rendu.
	 * @see Emprunt#getDateRetour()
	 * @see Emprunt#setDateRetour(Date)
	 */
	private Date dateRetour;
	
	/**
	 * Le lieu o� le {@link Emprunt#velo} a �t� emprunt�.
	 *@see Emprunt#getStationEmprunt()
	 *@see Emprunt#setStationEmprunt(Lieu)
	 */
	private Station stationEmprunt;
	
	/**
	 * Le lieu o� le {@link Emprunt#velo} a �t� rendu.
	 * @see Emprunt#getStationRetour()
	 * @see Emprunt#setStationRetour(Lieu)
	 */
	private Station stationRetour;


	//Constantes
	
	/**
	 * Temps au del� duquel un emprunt est cosid�r� comme long, c'est-�-dire sup�rieur � 2 heures.
	 */
	public static long TPS_EMPRUNT_MAX = 7200; //2h
	
	/**
	 * Temps en dec� duquel un emprunt est cosid�r� comme court, c'est-�-dire inf�rieur � 2 minutes.
	 */
	public static long TPS_EMPRUNT_MIN = 120; //2min


	//Constructeurs

	/**
	 * Constructeur par d�faut d'un Emprunt.
	 */
	public Emprunt(){
	}
	
	/**
	 * Cr�ation d'un Emprunt � partir des �l�ments suivants : {@link Emprunt#utilisateur}, {@link Emprunt#velo},
	 *  {@link Emprunt#dateEmprunt} et {@link Emprunt#stationEmprunt}. 
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
	 * Cr�ation d'un Emprunt � partir des �l�ments suivants : {@link Emprunt#utilisateur}, {@link Emprunt#velo},
	 *  {@link Emprunt#dateEmprunt}, {@link Emprunt#stationEmprunt}, {@link Emprunt#dateRetour} et {@link Emprunt#stationRetour}.
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
	 * Cr�ation d'un Emprunt � partir d'un autre Emprunt. Les deux emprunts ont alors les m�mes attributs.
	 * @param utilisateur
	 * @param velo
	 * @param dateEmprunt
	 * @param stationEmprunt
	 * @param dateRetour
	 * @param stationRetour
	 */
	public Emprunt(Emprunt e) {
		this.id = e.getId();
		this.utilisateur = e.getUtilisateur();
		this.velo = e.getVelo();
		this.dateEmprunt = e.getDateEmprunt();
		this.dateRetour = e.getDateRetour();
		this.stationEmprunt = e.getStationEmprunt();
		this.stationRetour = e.getStationRetour();
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


	//M�thodes

	/**
	 * @return la dur�e de l'Emprunt en faisant la diff�rence entre {@link Emprunt#dateRetour} et {@link Emprunt#dateRetour}.
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
	 * V�rifie l'�galit� entre deux instances de la classe Emprunt en comparant les valeurs de leurs attributs respectifs.
	 * @return un bool�en
	 * qui vaut vrai si les deux instances de la classe compte ont les m�me valeurs pour chacun de leurs attributs,
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
			this.getId().equals(e.getId());
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
