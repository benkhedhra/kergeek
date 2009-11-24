package metier;

import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;

import java.sql.SQLException;
import java.util.Date;

public class DemandeAssignation {

	private String id;
	private Date date;
	private boolean priseEnCharge;
	private int nombreVelosVoulusDansStation;
	private Lieu lieu;

	//ajout==true correspond ˆ une demande d'ajout de velo
	//ajout==false correspond ˆ une demande de retrait de velo


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
		return (this.getId().equals(d.getId())) && (this.getDate().equals(d.getDate())) && (this.isPriseEnCharge().equals(d.isPriseEnCharge()))&& (this.getNombreVelosVoulusDansStation() == d.getNombreVelosVoulusDansStation()) && (this.getLieu().equals(d.getLieu()));
	}

	public String toString(){
		String resul = "Demande "+this.getId()+" - Station "+this.getLieu().getId()+" - ";
		try {
			int diff;
			diff = this.getNombreVelosVoulusDansStation()-DAOVelo.getVelosByLieu(this.getLieu()).size();
			String type;
			if(diff<0){type = "retrait";}
			else{type = "ajout";}
			resul = resul+type+ " de "+Math.abs(diff)+" vélos - "+this.getDate().toString();
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}

		return resul;
	}



}
