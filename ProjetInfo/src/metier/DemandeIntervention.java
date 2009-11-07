package metier;

import java.util.Date;

public class DemandeIntervention {

	//Attributs

	private String id;
	private Date date;
	private Velo velo;
	private Utilisateur utilisateur;

	//Constructeur

	
	public DemandeIntervention(Velo velo, Utilisateur utilisateur) {
		super();
		this.setVelo(velo);
		this.setUtilisateur(utilisateur);
	}
	
	//Accesseurs
	
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

	public Velo getVelo() {
		return velo;
	}

	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
