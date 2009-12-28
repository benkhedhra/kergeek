package metier;

/**
 * 
 * @author KerGeek
 */
public class TypeIntervention {

	//Constantes
	
	/**
	 * @see TypeIntervention#type
	 */
	public static final int TYPE_NON_JUSTIFIEE = 1;
	public static final int TYPE_DESTRUCTION = 2;

	
	//Attributs
	
	private int type;
	private String description;
	

	//Constructeurs
	
	/**
	 * Constructeur par défaut d'un TypeIntervention.
	 */
	public TypeIntervention() {
		this.setType(0);
	}

	public TypeIntervention(String descritpion) {
		this.setDescription(descritpion);
		this.setType(0);
	}


	//Accesseurs et Mutateurs

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String descritpion) {
		this.description = descritpion;
	}
	
	//Méthode
	
	
	/**
	 * Vérifie l'égalité entre deux instances de la TypeIntervention en comparant les valeurs de leurs attributs respectifs.
	 * @return un boolŽen
	 * qui vaut vrai si les deux instances de la classe TypeIntervention ont les mêmes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		TypeIntervention t = (TypeIntervention) o;
		Boolean a =false;
		if(this.getType() == 0){
			a = t.getType() == 0;
		}
		else{
			a = this.getType() == t.getType();
		}
		return a && (this.getDescription().equals(t.getDescription()));
	}
	
	public String toString(){
		return this.getType()+" - "+this.getDescription();
	}
	

}
