package metier;

import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.SQLException;

public class Utilisateur {

	//Attributs

	private Compte compte;
	private String nom;
	private String prenom;
	private String adresse;
	private boolean bloque;
	private Velo velo;
	private Emprunt emprunt;

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
		this.setEmprunt(null);
	}


	public Utilisateur(Compte compte) {
		super();
		this.setCompte(compte);
		this.setBloque(false);
		this.setVelo(null);
		this.setEmprunt(null);
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

	public Emprunt getEmprunt() {
		return emprunt;
	}

	public void setEmprunt(Emprunt emprunt) {
		this.emprunt = emprunt;
	}	




	//Méthodes

	public boolean emprunteVelo(Velo velo) throws SQLException, ClassNotFoundException{
		Lieu.enleverVelo(velo);
		this.setEmprunt(new Emprunt(this, velo, UtilitaireDate.dateCourante() ,velo.getLieu()));
		return(DAOEmprunt.createEmprunt(this.getEmprunt()));
	}

	public boolean rendreVelo(Station station) 
	throws SQLException, ClassNotFoundException, VeloNonSortiException, PasDeVeloEmprunteException{

		boolean effectue = false;
		Velo velo = this.getVelo();

		if (velo == null){
			throw new PasDeVeloEmprunteException();
		}
		else{
			if (velo.getLieu().getId() != Lieu.SORTI){
				throw new VeloNonSortiException();
			}
			else{
				station.ajouterVelo(velo);
				velo.getEmprunt().setDateRetour(UtilitaireDate.dateCourante());
				velo.getEmprunt().setLieuRetour(station);
				boolean a = DAOEmprunt.updateDateRetour(emprunt);
				boolean b = DAOEmprunt.updateLieuRetour(emprunt);
				effectue= a && b ;
				if (velo.getEmprunt().getTpsEmprunt() >= Emprunt.TPS_EMPRUNT_MAX){
					this.setBloque(true);
				}
				velo.setEmprunt(null);
			}
		}
		
		return effectue;
	}


}
