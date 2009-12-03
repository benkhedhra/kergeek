package metier;


import java.sql.Date;

import exception.PasDeDateRetourException;

public class Emprunt {
	private String id;
	private Utilisateur utilisateur;
	private Velo velo;
	private Date dateEmprunt;
	private Date dateRetour;
	private Lieu lieuEmprunt;
	private Lieu lieuRetour;


	//Constantes

	public static long TPS_EMPRUNT_MAX = 7200000; //2h
	public static long TPS_EMPRUNT_MIN = 120000; //2min


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

	
	
	
	public Emprunt(Emprunt e) {
		this.id = e.getId();
		this.utilisateur = e.getUtilisateur();
		this.velo = e.getVelo();
		this.dateEmprunt = e.getDateEmprunt();
		this.dateRetour = e.getDateRetour();
		this.lieuEmprunt = e.getLieuEmprunt();
		this.lieuRetour = e.getLieuRetour();
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
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur u) {
		this.utilisateur = u;
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

	public long getTempsEmprunt() throws PasDeDateRetourException{
		long diff;
		if(this.getDateRetour() != null){
			System.out.println("dateRetour"+ this.getDateRetour().getTime());
			System.out.println("dateEmprunt"+ this.getDateEmprunt().getTime());
			diff = (this.getDateRetour().getTime() - this.getDateEmprunt().getTime())/100;
			System.out.println(diff);
		}
		else{
			throw new PasDeDateRetourException();
		}
		return diff;
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
