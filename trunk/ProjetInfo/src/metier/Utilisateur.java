package metier;

import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOVelo;
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

	/*TODO
	 * attribut emprunt? comment gerer les emprunts en cours?
	 * je pense que velo est suffisant mais ˆ creuser
	 */
	
	//Constructeurs

	

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
	
	/*
	 public Utilisateur(Compte compte, String nom, String prenom) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setBloque(false);
		this.setVelo(null);
	}
	*/

	



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

	public boolean emprunteVelo(Velo velo, Station station) throws SQLException, ClassNotFoundException{
		station.enleverVelo(velo);
		this.setVelo(velo);
		velo.setEmprunt(new Emprunt(this, velo, UtilitaireDate.dateCourante() ,velo.getLieu()));
		return(DAOEmprunt.createEmprunt(velo.getEmprunt()) & DAOVelo.updateVelo(velo));
	}
	
	

	public boolean rendreVelo(Station station) 
	throws SQLException, ClassNotFoundException, VeloNonSortiException, PasDeVeloEmprunteException{

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
				velo.getEmprunt().setDateRetour(UtilitaireDate.dateCourante());
				velo.getEmprunt().setLieuRetour(station);
				effectue = DAOEmprunt.updateEmprunt(velo.getEmprunt());
				
				if (velo.getEmprunt().getTpsEmprunt() >= Emprunt.TPS_EMPRUNT_MAX){
					this.setBloque(true);
				}
				else if (velo.getEmprunt().getTpsEmprunt() <= Emprunt.TPS_EMPRUNT_MIN) {
					Emprunt.proposerDemanderIntervention(velo, this);
				}
				velo.setEmprunt(null);
			}
		}
		
		return effectue;
	}


}
