package metier;


import java.util.Date;

public class Emprunt {
	private String id;
	private Utilisateur Utilisateur;
	private Velo velo;
	private Date dateEmprunt;
	private Date dateRetour;
	private Lieu lieuEmprunt;
	private Lieu lieuRetour;
	
	
	private long diff;
	private float tpsEmprunt;
	
	
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
	
	public float getTpsEmprunt() {
		return tpsEmprunt;
	}

	public void setTpsEmprunt(float tpsEmprunt) {
		this.tpsEmprunt = tpsEmprunt;
	}
	
	public long getDiff() {
		return diff;
	}

	public void setDiff(long diff) {
		this.diff = diff;
	}


	//Methodes
	
	public void calculTempsEmprunt(){
		this.diff = dateRetour.getTime() - dateEmprunt.getTime();
		this.tpsEmprunt = diff / 3600000.0f;
	}
	
	@Override
	public boolean equals(Object o) {
		Emprunt e =(Emprunt) o;
		return (this.getId().equals(e.getId())) && (this.getUtilisateur().equals(e.getUtilisateur()))&& (this.getVelo().equals(e.getVelo())) && (this.getDateEmprunt().equals(e.getDateRetour())) && (this.getLieuEmprunt().equals(e.getLieuEmprunt())) && (this.getLieuRetour().equals(e.getLieuRetour()));
	}
	
	
}
