package metier;

import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.SQLException;

public class Utilisateur {

	//Attributs
	private Compte compte;
	private String nom;
	private String prenom;
	private String adressePostale;
	private boolean bloque;
	private Velo velo;

	public Utilisateur() {
		//constructeur vide
		super();
		this.setBloque(false);
		this.setVelo(null);
	}	

	public Utilisateur(Compte compte) {
		super();
		this.setCompte(compte);
		this.setBloque(false);
		this.setVelo(null);
	}
	
	public Utilisateur(Compte compte, String nom, String prenom, String adresse) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdressePostale(adresse);
		this.setBloque(false);
		this.setVelo(null);
	}
	
	
	 public Utilisateur(Compte compte, String nom, String prenom, String adressePostale, boolean bloque) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdressePostale(adressePostale);
		this.setBloque(bloque);
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

	public String getAdressePostale() {
		return adressePostale;
	}

	public void setAdressePostale(String adresse) {
		this.adressePostale = adresse;
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

	/*TODO
	 * emprunte
	 */
	public void emprunteVelo(Velo velo, Station station) throws SQLException, ClassNotFoundException{
		station.enleverVelo(velo);
		this.setVelo(velo);
		velo.setEmprunt(new Emprunt(this, velo, UtilitaireDate.dateCourante() ,velo.getLieu()));
		//DAOEmprunt.createEmprunt(velo.getEmpruntEnCours()) & DAOVelo.updateVelo(velo);
		// a faire dans le controller
	}
	
	

	public boolean rendreVelo(Station station) 
	throws VeloNonSortiException, PasDeVeloEmprunteException{
		boolean effectue = false;
		Velo velo = this.getVelo();
		if (velo == null){
			throw new PasDeVeloEmprunteException();
		}
		else{
			if (velo.getLieu() != Lieu.SORTI){
				throw new VeloNonSortiException();
			}
			else{
				station.ajouterVelo(velo);
				velo.getEmpruntEnCours().setDateRetour(UtilitaireDate.dateCourante());
				velo.getEmpruntEnCours().setLieuRetour(station);
				effectue = true;
			}
		}
		
		return effectue;
	}
}
