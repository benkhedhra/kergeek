package metier;




/** 
 * Technicien est la classe representant un technicien du parc a vélos
 * Un technicien est caracterisé par un compte.
 * 
 * @see Compte
 * @see Velo
 * @see Lieu
 * @see Intervention
 */

public class Technicien {

	//Attribut

	/**
	 * Compte du technicien. Ce compte est modifiable.
	 * 
	 * @see Compte
	 * @see Technicien#Technicien(compte)
	 * @see Technicien#getCompte()
	 * @see Technicien#setCompte(compte)
	 */

	private Compte compte;


	// Constructeurs

	/**
	 * Constructeur d'initialisation du technicien.
	 * L'objet technicien est créé a partir d'un compte
	 * 
	 * @param  compte, le compte du technicien
	 * @see Compte
	 * @see Technicien#compte       
	 */
	public Technicien(Compte compte) {
		this.setCompte(compte);
	}
	
	/**
	 * Constructeur vide d'un Technicien.
	 */
	public Technicien() {
		super();
	}


	// Accesseurs et modificateurs

	



	/**
	 * retourne le compte du technicien
	 * 
	 * @return compte, le compte du technicien
	 */

	public Compte getCompte() {
		return this.compte;
	}

	/**
	 * Met à jour le compte du technicien
	 * 
	 * @param compte, le nouveau compte du technicien
	 * @see Compte
	 */

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	// Methodes

	/**
	 * Ajoute un vélo 
	 * 
	 * @param velo
	 * @return velo, le nouveau vélo  qui a été ajouté à la base de données
	 * @see Velo
	 */
	public Velo enregistrerVelo(){
		Velo velo = new Velo(Garage.getInstance(), false);
		return velo;
	}
	
	public void retirerVelo(Velo velo){
		velo.setLieu(Garage.getInstance());
	}
	
	public void remettreVelo(Velo velo, Station station){
		station.ajouterVelo(velo);
	}
	
	/**
	 * Retire un vélo d'une station vers le garage pour le réparer.
	 * @param velo
	 * @param lieu
	 * @return booleen 
	 * @see Velo
	 * @see Lieu
	 * @see Intervention
	 * @see UtlitaireDate
	 * @see enleverVelo
	 */
	public Intervention intervenir(Velo velo){
		retirerVelo(velo);
		Intervention intervention = new Intervention(velo, UtilitaireDate.dateCourante());
		return intervention;
	}
	
	
	public void terminerIntervention(Velo velo, Station station, Intervention intervention, TypeIntervention typeIntervention){
		remettreVelo(velo, station);
		intervention.setTypeIntervention(typeIntervention);
		
	}
	
	@Override
	public boolean equals(Object o) {
		Technicien t =(Technicien) o;
		return this.getCompte().equals(t.getCompte());
	}
	
	
	
	@Override
	public String toString(){
		return this.getCompte().toString();
	}
	
}


