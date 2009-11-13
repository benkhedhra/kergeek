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
 * @see enregistrerVelo
 * @see intervenir
 */

public class Technicien {

	//Attributs

	private Compte compte;


	//Constructeur

	public Technicien(Compte compte) {
		super();
		this.setCompte(compte);
	}
	
	
	public Technicien() {
		super();
		compte.setType(Compte.TYPE_TECHNICIEN);
	}


	//Accesseurs

	public Compte getCompte() {
		return this.compte;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	//Méthodes

	/**enregisterVelo()
	 * @return le nouveau velo  qui a ete ajoute a la base de donnees
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


	public boolean intervenir(Velo velo, Lieu lieu) throws SQLException, ClassNotFoundException{
		//Lorsqu'un technicien retire un velo d'une station (pour le reparer)
		lieu.enleverVelo(velo);
		velo.setLieu(Garage.getInstance());
		Intervention intervention = new Intervention(velo, UtilitaireDate.dateCourante());
		return(DAOIntervention.createIntervention(intervention));
	}
}


