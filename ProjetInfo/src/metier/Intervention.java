package metier;

import java.util.Date;

public class Intervention {
	
	//Attributs
	
	private Date date;
	private TypeIntervention typeOperation;
	public static final int PNEU_CREVE=1;
	public static final int LUMIERE_CASSEE=2;
	
	
	
	
	//Constructeur
	
	public Intervention() {
		super();
	}

	
	public Intervention(Date date, TypeIntervention typeOperation) {
		super();
		this.date = date;
		this.typeOperation = typeOperation;
	}
	
	

	//Accesseurs
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public TypeIntervention getTypeOperation() {
		return typeOperation;
	}
	public void setTypeOperation(TypeIntervention typeOperation) {
		this.typeOperation = typeOperation;
	}

	

}
