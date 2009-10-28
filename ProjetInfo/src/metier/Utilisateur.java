package metier;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;

import java.sql.SQLException;
import java.sql.Statement;

public class Utilisateur {
	
	//Attributs
	
	private Compte compte;
	private String nom;
	private String prenom;
	private String adresse;
	private boolean bloque;
	private Velo velo;
	private float tpsEmpruntMax; 
	
	//Constructeur

	
	public Utilisateur(Compte compte, String nom, String prenom,
			String adresse) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdresse(adresse);
		this.setBloque(false);
		this.setVelo(null);
	}

	
	public Utilisateur(Compte compte) {
		super();
		this.setCompte(compte);
		this.setBloque(false);
		this.setVelo(null);
	}
	
	
	
	//Accesseurs
	
	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	
	public boolean isBloque() {
		return bloque;
	}

	public void setBloque(boolean bloque) {
		this.bloque = bloque;
	}
	
	public Velo getVelo() {
		return velo;
	}


	public void setVelo(Velo velo) {
		this.velo = velo;
	}
	
	

	//Méthodes
	
	

	public void emprunteVelo(Velo velo) throws SQLException, ClassNotFoundException{
		Lieu.enleverVelo(velo);
	}
	
	public Velo rendreVelo(Station station){
		/*TODO
		 * mettre ˆ jour bloque si temps d'emprunt trop long
		 */
		/*if (Emprunte.getTpsEmprunt() >= tpsEmpruntMax){	je sais pas comment s'apelle cet attribut, donc je le crée!
			bloque = true;
		}*/
		
		Velo velo = new Velo();
		return(velo);
		
	}

}
