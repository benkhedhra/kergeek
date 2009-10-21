package metier;

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
	
	//Méthodes
	
	public Velo enregistrerVelo(){
		Velo velo = new Velo();
		return velo;
		
	}
}
	

