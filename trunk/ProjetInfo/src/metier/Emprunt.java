package metier;


import gestionBaseDeDonnees.DAODemandeIntervention;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

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
	
	public static boolean proposerDemanderIntervention(Velo velo, Utilisateur utilisateur) throws SQLException,ClassNotFoundException{
		System.out.println("Souhaitez-vous demander une intervention sur ce velo?\n Oui : 1\n Non : 2");
		Scanner sc= new Scanner(System.in);
		boolean acceptee = false;
		try {
			int rep = Integer.parseInt(sc.next());
			if (rep == 1){
				DemandeIntervention ddeIntervention = new DemandeIntervention(velo,utilisateur);
				DAODemandeIntervention.createDemandeIntervention(ddeIntervention);
				acceptee = true;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Je n'ai pas compris votre reponse, " +
					"veuillez entrer soit un 1, soit un 2 s'il vous plait.");
			proposerDemanderIntervention(velo, utilisateur);
		}
		return acceptee;
	}
	
	
	
	
}
