package metier;

import java.util.Date;

public class DemandeAssignation {
	
	private Date date;
	private boolean ajout;
	private int nombreVelos;
	private Lieu lieu;
	
	//ajout==true correspond à une demande d'ajout de vélo
	//ajout==false correspond à une demande de retrait de vélo

	
	//Constructeur
	
	
	public DemandeAssignation(Date date, boolean ajout, int nombre, Lieu lieu) {
		super();
		this.setDate(date);
		this.setAjout(ajout);
		this.setNombreVelos(nombre);
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


	public int getNombreVelos() {
		return nombreVelos;
	}


	public void setNombreVelos(int nombreVelos) {
		this.nombreVelos = nombreVelos;
	}


	public Lieu getLieu() {
		return lieu;
	}


	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	
	
	

}
