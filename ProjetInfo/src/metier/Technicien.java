package metier;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.SQLException;

/** 
 * Technicien est la classe representant un technicien du parc a velos
 * Un technicien est caracterise par un compte
 * 
 * @see Compte
 * @see Velo
 * @see Lieu
 * @see Intervention
 * @see DAOIntervention
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
		super();
		this.setCompte(compte);
	}
	
	/**Constructeur par defaut du technicien
	 * 
	 * @see Compte
	 */
	
	public Technicien() {
		super();
		compte.setType(Compte.TYPE_TECHNICIEN);
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
	 * @see DAOVelo 
	 * @throws SQLException, si SQL Server retourne un avertissement ou une erreur
	 * @throws ClassNotFoundException, si SQL Server ne retrouve pas la classe DAOVelo
	 */
	
	public Velo enregistrerVelo() throws SQLException, ClassNotFoundException{
		Velo velo = new Velo();
		/*TODO 
		 * COMMENT GENERER LES IDENTIFIANT EN SQL?
		 *velo.setId();
		 */
		velo.setEnPanne(false);
		velo.setLieu(Garage.getInstance());
		DAOVelo.createVelo(velo);
		return velo;
	}

	/**
	 * Retire un velo d'une station au garage pour reparation.
	 * Ajoute une ligne dans la table intervention de la bdd
	 * 
	 * @param velo
	 * @param lieu
	 * @return booleen 
	 * @see Velo
	 * @see Lieu
	 * @see Intervention
	 * @see DAOIntervention
	 * @see UtlitaireDate
	 * @see enleverVelo
	 * @throws SQLException, si SQL Server retourne un avertissement ou une erreur
	 * @throws ClassNotFoundException, si SQL Server ne retrouve pas la classe DAOIntervention
	 */
	
	public boolean intervenir(Velo velo, Lieu lieu) throws SQLException, ClassNotFoundException{
		lieu.enleverVelo(velo);
		velo.setLieu(Garage.getInstance());
		Intervention intervention = new Intervention(velo, UtilitaireDate.dateCourante());
		return(DAOIntervention.createIntervention(intervention));
	}
}


