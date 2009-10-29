package metier;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.SQLException;

public class Technicien {

	//Attributs

	private Compte compte;


	//Constructeur

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

	//M�thodes

	/**enregisterVelo()
	 * @param Velo
	 * @return vrai si le velo a bien �t� ajouter a la base de donn�es, faux sinon
	 */
	public boolean enregistrerVelo(Velo velo) throws SQLException, ClassNotFoundException{
		return(DAOVelo.createVelo(velo));
	}


	public boolean intervenir(Velo velo) throws SQLException, ClassNotFoundException{
		//Lorsqu'un technicien retire un velo d'une station (pour le reparer)
		Lieu.enleverVelo(velo);
		Intervention intervention = new Intervention(velo, UtilitaireDate.dateCourante());
		return(DAOIntervention.createIntervention(intervention));

	}
}


