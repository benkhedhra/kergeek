package metier;




/** 
 * Technicien est la classe representant un technicien du parc a velos
 * Un technicien est caracterise par un compte
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
	 * L'objet technicien est creer a partir d'un compte
	 * 
	 * @param  compte, le compte du technicien
	 * @see Compte
	 * @see Technicien#compte       
	 */
	public Technicien(Compte compte) {
		this.setCompte(compte);
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
	 * Met a jour le compte du technicien
	 * 
	 * @param compte, le nouveau compte du technicien
	 * @see Compte
	 */

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	// Methodes

	/**
	 * Ajoute un velo 
	 * 
	 * @param velo
	 * @return velo, le nouveau velo  qui a ete ajoute a la base de donnees
	 * @see Velo
	 */

	public Velo enregistrerVelo(){
		Velo velo = new Velo();
		velo.setEnPanne(false);
		velo.setLieu(Garage.getInstance());
		return velo;
	}

	/**
	 * Retire un velo d'une station vers le garage pour reparation.
	 * 
	 * @param velo
	 * @param lieu
	 * @return booleen 
	 * @see Velo
	 * @see Lieu
	 * @see Intervention
	 * @see UtlitaireDate
	 * @see enleverVelo
	 */

	public Intervention intervenir(Velo velo, Lieu lieu){
		lieu.enleverVelo(velo);
		velo.setLieu(Garage.getInstance());
		Intervention intervention = new Intervention(velo, UtilitaireDate.dateCourante());
		return intervention;
	}
	
	@Override
	public boolean equals(Object o) {
		Technicien t =(Technicien) o;
		return this.getCompte().equals(t.getCompte());
	}
}


