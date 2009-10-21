package metier;

public class TypeIntervention {
	
	//Attributs
	
	private int id;
	private String description;
	
	
	//Constructeur
	
	public TypeIntervention(int numero, String description) {
		super();
		this.id = numero;
		this.description = description;
	}


	//Accesseurs
	
	public int getNumero() {
		return id;
	}


	public void setNumero(int numero) {
		this.id = numero;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
