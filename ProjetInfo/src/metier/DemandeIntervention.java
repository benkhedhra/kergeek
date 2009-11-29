package metier;

import java.sql.Date;

public class DemandeIntervention {

	//Attributs

	private String id;
	private Date date;
	private Velo velo;
	private Utilisateur utilisateur;
	//private Lieu lieu = this.getVelo().getLieu();
	private Intervention intervention;

	//Constructeur


	public DemandeIntervention() {
		super();
	}
	
	public DemandeIntervention(Utilisateur utilisateur , Velo velo) {
		super();
		this.setUtilisateur(utilisateur);
		this.setVelo(velo);
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

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	
	
	//Methodes
	
	public Intervention priseEnCharge(TypeIntervention typeIntervention){
		Intervention intervention = new Intervention(this.getVelo(), this.getDate(), typeIntervention);
		this.setIntervention(intervention);
		return intervention;
	}
	
	
	@Override
	public boolean equals(Object o) {
		DemandeIntervention d = (DemandeIntervention) o;
		Boolean a =false;
		Boolean b =false;
		if(this.getId() == null){
			a = d.getId() == null;
		}
		else{
			a =this.getId().equals(d.getId());
		}
		if(this.getIntervention() == null){
			a = d.getIntervention() == null;
		}
		else{
			a =this.getIntervention().equals(d.getIntervention());
		}
		return a && b && (this.getDate().equals(d.getDate())) && (this.getVelo().equals(d.getVelo()))&& (this.getUtilisateur().equals(d.getUtilisateur()));
	}
	
}
