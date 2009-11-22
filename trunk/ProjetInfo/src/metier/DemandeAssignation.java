package metier;

import java.util.Date;

public class DemandeAssignation {
	
	private Date date;
	private boolean priseEnCharge;
	private int nombreVelosVouluDansStation;
	private Lieu lieu;
	
	//ajout==true correspond ˆ une demande d'ajout de velo
	//ajout==false correspond ˆ une demande de retrait de velo

	
	//Constructeur
	
	public DemandeAssignation(Date date, int nombre, Lieu lieu) {
		super();
		this.setDate(date);
		this.setPriseEnCharge(false);
		this.setNombreVelosVoulusDansStation(nombre);
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


	public int getNombreVelosVoulusDansStation() {
		return nombreVelosVouluDansStation;
	}


	public void setNombreVelosVoulusDansStation(int nombreVelos) {
		this.nombreVelosVouluDansStation = nombreVelos;
	}


	public Lieu getLieu() {
		return lieu;
	}


	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	
	
	

}
