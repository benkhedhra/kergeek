package metier;

import java.sql.Time;

public class DemandeAssignation {
	
	private Time date;
	private boolean ajout;

	
	//Constructeur
	
	
	public DemandeAssignation(Time date, boolean ajout) {
		super();
		this.setDate(date);
		this.setAjout(ajout);
	}

	
	// Accesseur
	
	public Time getDate() {
		return date;
	}


	public void setDate(Time date) {
		this.date = date;
	}


	public boolean isAjout() {
		return ajout;
	}


	public void setAjout(boolean ajout) {
		this.ajout = ajout;
	}

	
	
	

}
