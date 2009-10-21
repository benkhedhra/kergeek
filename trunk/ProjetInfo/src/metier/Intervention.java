package metier;

import java.sql.Time;

public class Intervention {
	private Time date;
	private TypeIntervention typeOperation;
	public static final int PNEU_CREVE=1;
	public static final int LUMIERE_CASSEE=2;
	
	
	
	
	public Intervention() {
		super();
	}



	public Intervention(Time date, TypeIntervention typeOperation) {
		super();
		this.date = date;
		this.typeOperation = typeOperation;
	}
	
	
	
	public Time getDate() {
		return date;
	}
	public void setDate(Time date) {
		this.date = date;
	}
	public TypeIntervention getTypeOperation() {
		return typeOperation;
	}
	public void setTypeOperation(TypeIntervention typeOperation) {
		this.typeOperation = typeOperation;
	}

	

}
