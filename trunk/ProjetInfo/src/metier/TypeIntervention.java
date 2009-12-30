package metier;

/**
 * La classe TypeIntervention sert � r�pertorier les diff�rents types d�op�rations de maintenance r�alisables par un {@link Technicien}
 * sur un {@link Velo}.
 * @see Intervention
 * @author KerGeek
 */
public class TypeIntervention {

	//Constantes
	
	/**
	 * Si une {@link DemandeIntervention} formul�e par un {@link Utilisateur} s'av�re non justifi�e, alors on associe
	 * ce TypeIntervention � l'{@link Intervention} correspondante.
	 * @see TypeIntervention#numero
	 */
	public static final int TYPE_NON_JUSTIFIEE = 1;
	
	/**
	 * Lorsqu'un {@link Technicien} ne parvient pas � r�par� un {@link Velo} et qu'il d�cide de le retirer du parc d�finitivement, 
	 * on associe ce TypeIntervention � l'{@link Intervention} correspondante.
	 * @see TypeIntervention#numero
	 * @see Technicien#retirerDuParc(Intervention)
	 */
	public static final int TYPE_DESTRUCTION = 2;

	
	//Attributs
	
	private int numero;
	
	private String description;
	

	//Constructeurs
	
	/**
	 * Constructeur par d�faut d'un TypeIntervention.
	 */
	public TypeIntervention() {
		super();
	}
	
	/**
	 * Cr�ation d'un nouveau TypeIntervention � partir d'une {@link TypeIntervention#description}
	 * @param descritpion
	 */
	public TypeIntervention(String descritpion) {
		this.setDescription(descritpion);
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
	
	
	//M�thode
	
	/**
	 * V�rifie l'�galit� entre deux instances de la TypeIntervention en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe TypeIntervention ont les m�mes valeurs pour chacun de leurs attributs,
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
