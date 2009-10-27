package metier;

import java.util.Date;

public class DemandeAssignation {
	
	private Date date;
	private boolean ajout;
	private Lieu lieu;
	
	//ajout==true correspond ˆ une demande d'ajout de vlo
	//ajout==false correspond ˆ une demande de retrait de vlo

	
	//Constructeur
	
	
	public DemandeAssignation(Date date, boolean ajout, Lieu lieu) {
		super();
		this.setDate(date);
		this.setAjout(ajout);
		this.setLieu(lieu);
	}

	
	// Accesseur
	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public boolean isAjout() {
		return ajout;
	}


	public void setAjout(boolean ajout) {
		this.ajout = ajout;
	}


	public Lieu getLieu() {
		return lieu;
	}


	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	
	
	

}
