package metier;

public final class TypeIntervention {
	
	//Attributs
	
	private int type;
	private String description;
	
	
	//Constructeur
	
	public TypeIntervention(int numero, String description) {
		super();
		this.type = numero;
		this.description = description;
	}


	//Accesseurs
	
	public int getType() {
		return type;
	}


	public void setType(int numero) {
		this.type = numero;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}



	
	//Methodes
	
	@Override
	public String toString() {
		return Integer.toString(this.getType());
	}
	

}
