package metier;


import java.sql.Date;

import exception.PasDeDateRetourException;

public class Emprunt {
	private String id;
	private Utilisateur Utilisateur;
	private Velo velo;
	private Date dateEmprunt;
	private Date dateRetour;
	private Lieu lieuEmprunt;
	private Lieu lieuRetour;


	//Constantes

	public static float TPS_EMPRUNT_MAX = 86400000.0f; //24h
	public static float TPS_EMPRUNT_MIN = 120000.0f; //2min


	//Constructeur


	public Emprunt(Utilisateur utilisateur, Velo velo, Date dateEmprunt, Lieu lieuEmprunt) {
		this.setUtilisateur(utilisateur);
		this.setVelo(velo);
		this.setDateEmprunt(dateEmprunt);
		this.setLieuEmprunt(lieuEmprunt);
	}

	public Emprunt(Utilisateur utilisateur, Velo velo, Date dateEmprunt, Lieu lieuEmprunt, Date dateRetour, Lieu lieuRetour) {
		this.setUtilisateur(utilisateur);
		this.setVelo(velo);
		this.setDateEmprunt(dateEmprunt);
		this.setLieuEmprunt(lieuEmprunt);
		this.setDateRetour(dateRetour);
		this.setLieuRetour(lieuRetour);
	}

	public Emprunt(){
	}



	//Accesseurs

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return Utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		Utilisateur = utilisateur;
	}

	public Velo getVelo() {
		return velo;
	}

	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public Date getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Lieu getLieuEmprunt() {
		return lieuEmprunt;
	}

	public void setLieuEmprunt(Lieu lieuEmprunt) {
		this.lieuEmprunt = lieuEmprunt;
	}

	public Lieu getLieuRetour() {
		return lieuRetour;
	}

	public void setLieuRetour(Lieu lieuRetour) {
		this.lieuRetour = lieuRetour;
	}


	//Methodes

	public float getTempsEmprunt() throws PasDeDateRetourException{
		float tpsEmprunt = 0;
		if(dateRetour != null){
			long diff = dateRetour.getTime() - dateEmprunt.getTime();
			tpsEmprunt = diff / 3600000.0f;
		}
		else{
			throw new PasDeDateRetourException();
		}
		return tpsEmprunt;
	}

	@Override
	public boolean equals(Object o) {
		Emprunt e =(Emprunt) o;
		Boolean a =false;
		Boolean b = false;
		if(this.getId() == null){
			a = e.getId() == null;
		}
		else{
			this.getId().equals(e.getId());
		}
		if(this.getDateRetour() == null){
			b = e.getDateRetour() == null && e.getLieuRetour() == null;
		}
		else{
			b = (this.getDateRetour().equals(e.getDateRetour())) && (this.getLieuRetour().equals(e.getLieuRetour()));
		}
		return a && b && (this.getUtilisateur().equals(e.getUtilisateur()))&& (this.getVelo().equals(e.getVelo())) && (this.getDateEmprunt().equals(e.getDateEmprunt()))  && (this.getLieuEmprunt().equals(e.getLieuEmprunt()));
	}


}
