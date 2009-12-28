package metier;

/**
 * 
 * @author KerGeek
 */
public class TypeIntervention {

	//Constantes
	
	/**
	 * @see TypeIntervention#numero
	 */
	public static final int TYPE_NON_JUSTIFIEE = 1;
	
	/**
	 * @see TypeIntervention#numero
	 */
	public static final int TYPE_DESTRUCTION = 2;

	
	//Attributs
	
	/**
	 * 
	 */
	private int numero;
	
	/**
	 * 
	 */
	private String description;
	

	//Constructeurs
	
	/**
	 * Constructeur par défaut d'un TypeIntervention.
	 */
	public TypeIntervention() {
		this.setNumero(0);
	}
	
	/**
	 * 
	 * @param descritpion
	 */
	public TypeIntervention(String descritpion) {
		this.setDescription(descritpion);
		this.setNumero(0);
	}


	//Accesseurs et Mutateurs
	
	/**
	 * @return {@link TypeIntervention#numero}
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Initialise {@link TypeIntervention#numero}.
	 * @param numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @return {@link TypeIntervention#description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Initialise {@link TypeIntervention#description}.
	 * @param descritpion
	 */
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
		if(this.getNumero() == 0){
			a = t.getNumero() == 0;
		}
		else{
			a = this.getNumero() == t.getNumero();
		}
		return a && (this.getDescription().equals(t.getDescription()));
	}
	
	/**
	 * Affiche {@link TypeIntervention#numero} suivi de {@link TypeIntervention#description}.
	 */
	@Override
	public String toString(){
		return this.getNumero()+" - "+this.getDescription();
	}
	

}
