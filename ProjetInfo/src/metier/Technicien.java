package metier;

import gestionBaseDeDonnees.DAOTypeIntervention;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

/** 
 * Technicien est la classe representant un technicien du parc à vélos
 * Un technicien est caracterisé par un {@link Compte}.
 * @see Velo
 * @see Lieu
 * @see Intervention
 */
public class Technicien {

	//Attribut

	/**
	 * Compte du technicien. Ce compte est modifiable.
	 * @see Compte
	 * @see Technicien#Technicien(compte)
	 * @see Technicien#getCompte()
	 * @see Technicien#setCompte(compte)
	 */
	private Compte compte;


	//Constructeurs


	/**
	 * Constructeur  par défaut d'un Technicien.
	 */
	public Technicien() {
		super();
	}
	
	/**
	 * Constructeur d'initialisation du technicien.
	 * L'objet technicien est créé a partir d'un compte.
	 * @param  compte
	 * le compte du technicien
	 * @see Compte
	 * @see Technicien#compte       
	 */
	public Technicien(Compte compte) {
		this.setCompte(compte);
	}


	//Accesseurs et modificateurs

	/**
	 * @return {@link Technicien#compte}
	 */

	public Compte getCompte() {
		return this.compte;
	}

	/**
	 * Initialise {@link Technicien#compte}.
	 * @param compte
	 */

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	//Méthodes

	/**
	 * Ajoute un {@link Velo} au parc.
	 * @param velo
	 * @return velo, le nouveau vélo.
	 */
	public Velo enregistrerVelo(){
		Velo velo = new Velo(Garage.getInstance());
		return velo;
	}
	
	/**
	 * Place un {@link Velo} au {@link Garage}.
	 * @param velo
	 */
	public void retirerVelo(Velo velo){
		velo.setLieu(Garage.getInstance());
	}
	
	/**
	 * Place un {@link Velo} du Garage dans une {@link Station}.
	 * @param velo
	 * @param station
	 */
	public void rajouterVelo(Velo velo, Station station){
		station.ajouterVelo(velo);
	}

	/**
	 * Retire un {@link Velo} d'une {@link Station} vers le {@link Garage} pour le réparer en créant une nouvelle {@link Intervention}.
	 * @param velo
	 * @param lieu
	 * @return booleen
	 * @see UtilitaireDate#dateCourante()
	 * @see Technicien#retirerVelo(Velo)
	 */
	public Intervention intervenir(Velo velo){
		retirerVelo(velo);
		Intervention intervention = new Intervention(velo, UtilitaireDate.dateCourante());
		return intervention;
	}

	/**
	 * Associe un {@link TypeIntervention} à l'{@link Intervention} et marque le vélo concerné par cette {@link Intervention}
	 * comme en état de marche.
	 * @param intervention
	 * en cours
	 * @param typeIntervention
	 * @return l'{@link Intervention} terminée
	 */
	public Intervention terminerIntervention(Intervention intervention, TypeIntervention typeIntervention){
		Intervention i = intervention;
		i.setTypeIntervention(typeIntervention);
		i.getVelo().setEnPanne(false);
		return i;
	}
	
	/**
	 * Retire un {@link Velo} du parc que le Technicien n' a pas pu réparer en le plaçant au {@link Lieu} {@link Detruit}
	 * et en associant le {@link TypeIntervention} {@link TypeIntervention#TYPE_DESTRUCTION}  à l'{@link Intervention} dont 
	 * il faisait l'objet.
	 * @param intervention
	 * @return intervention
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public Intervention retirerDuParc(Intervention intervention) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		intervention.setTypeIntervention(DAOTypeIntervention.getTypeInterventionById(TypeIntervention.TYPE_DESTRUCTION));
		intervention.getVelo().setLieu(Detruit.getInstance());
		return intervention;
	}

	/**
	 * Vérifie l'égalité entre deux instances de la classe Technicien en comparant leur compte respectifs.
	 * @return vrai si les deux instances de la classe Technicien ont le même compte,
	 * faux sinon
	 * @see Compte#equals(Object)
	 */
	@Override
	public boolean equals(Object o) {
		Technicien t =(Technicien) o;
		return this.getCompte().equals(t.getCompte());
	}


	/**
	 * @see Compte#toString()
	 */
	@Override
	public String toString(){
		return this.getCompte().toString();
	}

}


