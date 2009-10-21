package metier;

import java.sql.Time;

public class Emprunte {
	private Time dateEmprunt;
	private Time dateRetour;
	private Lieu lieuEmprunt;
	private Lieu lieuRetour;
	
	//Constructeur
	
	public Emprunte(Time dateEmprunt, Lieu lieuEmprunt) {
		super();
		this.setDateEmprunt(dateEmprunt);
		this.setLieuEmprunt(lieuEmprunt);
	}
	
	//Accesseurs
	
	public Time getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(Time dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public Time getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(Time dateRetour) {
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
	
	public void proposerDemanderIntervention(Velo velo, Utilisateur utilisateur){
		
	}

}
