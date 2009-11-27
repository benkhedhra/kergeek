package metier;

import java.sql.Date;

public class DemandeAssignation {

	private String id;
	private Date date;
	private boolean priseEnCharge;
	private int nombreVelosVoulusDansStation;
	private Lieu lieu;
	
	//Constructeur

	public DemandeAssignation(){
		super();
	}

	public DemandeAssignation(Date date, int nombre, Lieu lieu) {
		this.setDate(date);
		this.setPriseEnCharge(false);
		this.setNombreVelosVoulusDansStation(nombre);
		this.setLieu(lieu);
	}



	// Accesseur

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public Boolean isPriseEnCharge() {
		return priseEnCharge;
	}


	public void setPriseEnCharge(boolean priseEnCharge) {
		this.priseEnCharge = priseEnCharge;
	}


	public int getNombreVelosVoulusDansStation() {
		return nombreVelosVoulusDansStation;
	}


	public void setNombreVelosVoulusDansStation(int nombreVelos) {
		this.nombreVelosVoulusDansStation = nombreVelos;
	}


	public Lieu getLieu() {
		return lieu;
	}


	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	@Override
	public boolean equals(Object o) {
		DemandeAssignation d = (DemandeAssignation) o;
		Boolean a =false;
		if(this.getId() == null){
			a = d.getId() == null;
		}
		else{
			this.getId().equals(d.getId());
		}
		return a && (this.getDate().equals(d.getDate())) && (this.isPriseEnCharge().equals(d.isPriseEnCharge()))&& (this.getNombreVelosVoulusDansStation() == d.getNombreVelosVoulusDansStation()) && (this.getLieu().equals(d.getLieu()));
	}
}
