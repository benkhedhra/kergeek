package metier;

import java.util.Date;

public class DemandeAssignation {
	
	private Date date;
	private boolean priseEnCharge;
	private int nombreVelos;
	private Lieu lieu;
	
	//ajout==true correspond ˆ une demande d'ajout de velo
	//ajout==false correspond ˆ une demande de retrait de velo

	
	//Constructeur
	
	public DemandeAssignation(Date date, boolean ajout, int nombre, Lieu lieu) {
		super();
		this.setDate(date);
		this.setPriseEnCharge(priseEnCharge);
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


	public boolean isPriseEnCharge() {
		return priseEnCharge;
	}


	public void setPriseEnCharge(boolean priseEnCharge) {
		this.priseEnCharge = priseEnCharge;
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
