package metier;

import java.sql.SQLException;

import gestionBaseDeDonnees.DAOCompte;

public class Administrateur {
	
	//Attributs
	
	private Compte compte;
	
	
	//Constructeur
	
	public Administrateur() {
		super();
		compte.setType(Compte.TYPE_ADMINISTRATEUR);
	}
	
	
	
	//Accesseurs
	
	public Compte getCompte() {
		return this.compte;
	}
	
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	
	//Methodes



	public Compte creerCompte(){
		Compte c = new Compte();
		/*TODO
		 * Compte.NUMERO_DERNIER_ID_CREE = Compte.NUMERO_DERNIER_ID_CREE+1;
		 */
		return c;
		
		
	}
	
	
	public void resilierCompte(String idCompte) throws SQLException, ClassNotFoundException{
		DAOCompte.getCompteById(idCompte).setActif(false);	
	}
	
	//editerCompte correspond ˆ l'ensemble des setters
	
	public void demanderAssignation(boolean ajout, Lieu lieu){
		/*TODO
		 * 
		 */
	}

	
	
	
	
	
}
