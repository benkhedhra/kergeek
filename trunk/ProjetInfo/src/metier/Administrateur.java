package metier;

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
	
	
	//Méthodes



	public Compte creerCompte(){
		Compte c = new Compte();
		/*TODO
		 * Compte.NUMERO_DERNIER_ID_CREE = Compte.NUMERO_DERNIER_ID_CREE+1;
		 */
		return c;
		
		
	}
	
	public void resilierCompte(String idCompte){
		/*TODO
		 * getCompteById(idCompte).set()
		 */
		
	}
	
	public void editerCompte(String idCompte){
		/*TODO
		 * 
		 */
	}
	
	public void demanderAssignation(boolean ajout, Lieu lieu){
		/*TODO
		 * 
		 */
	}

	
	
	
	
	
}
