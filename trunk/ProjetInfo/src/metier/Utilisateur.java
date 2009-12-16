package metier;

import java.sql.SQLException;

import metier.exceptionsMetier.CompteBloqueException;
import metier.exceptionsMetier.PasDeDateRetourException;
import metier.exceptionsMetier.PasDeVeloEmprunteException;

public class Utilisateur {

	//Attributs
	private Compte compte;
	private String nom;
	private String prenom;
	private String adressePostale;
	private boolean bloque;
	private Emprunt empruntEnCours;

	public Utilisateur(){
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}	

	public Utilisateur(Compte compte) {
		super();
		this.setCompte(compte);
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}

	public Utilisateur(Compte compte, String nom, String prenom) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}

	public Utilisateur(Compte compte, String nom, String prenom, String adresse) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdressePostale(adresse);
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}


	public Utilisateur(Compte compte, String nom, String prenom, String adressePostale, boolean bloque) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdressePostale(adressePostale);
		this.setBloque(bloque);
		this.setEmpruntEnCours(null);
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


	public Boolean isBloque() {
		return bloque;
	}

	public void setBloque(boolean bloque) {
		this.bloque = bloque;
	}

	public Emprunt getEmpruntEnCours() {
		return empruntEnCours;
	}

	public void setEmpruntEnCours(Emprunt emprunt) {
		this.empruntEnCours = emprunt;
	}


	//Méthodes


	public void emprunteVelo(Velo velo, Station station) throws SQLException, ClassNotFoundException, CompteBloqueException{
		if (!this.bloque){
			velo.setEmpruntEnCours(new Emprunt(this, velo, UtilitaireDate.dateCourante(), velo.getLieu()));
			this.setEmpruntEnCours(velo.getEmpruntEnCours());
			station.enleverVelo(velo);
		}
		else{
			throw new CompteBloqueException();
		}
	}


	public Emprunt rendreVelo(Station station) throws PasDeVeloEmprunteException, PasDeDateRetourException{
		Emprunt emprunt = null;
		// cet emprunt sera une trace de l'ancien emprunt pour pallier au this.setVelo(null);
		Velo velo = this.getEmpruntEnCours().getVelo();
		try{
			if (velo == null){
				throw new PasDeVeloEmprunteException("L'utilisateur n'a actuellement pas emprunté de velo");
			}
			else{
				station.ajouterVelo(velo);
				emprunt = new Emprunt(empruntEnCours);
				emprunt.setDateRetour(UtilitaireDate.dateCourante());
				emprunt.setLieuRetour(station);
				if (emprunt.getTempsEmprunt()>Emprunt.TPS_EMPRUNT_MAX){
					//emprunt trop long
					this.setBloque(true);
					System.out.println("compte bloqué");
					//TODO
					System.out.println(emprunt.getTempsEmprunt());
					System.out.println(emprunt.getDateEmprunt());
					System.out.println(emprunt.getDateRetour());
				}
				velo.setEmpruntEnCours(null);
				this.setEmpruntEnCours(null);
			}
		}
		catch(PasDeVeloEmprunteException e){
			System.out.println(e.getMessage());
		}
		return emprunt;
	}


	@Override
	public boolean equals(Object o) {

		Utilisateur u =(Utilisateur) o;

		Boolean e = false;
		Boolean n = false;
		Boolean p = false;
		Boolean a = false;

		if(this.getEmpruntEnCours() == null){
			e = u.getEmpruntEnCours() == null;
		}
		else{
			e = this.getEmpruntEnCours().equals(u.getEmpruntEnCours());	
		}

		if(this.getNom() == null){
			n = u.getNom() == null;
		}
		else{
			n = this.getNom().equals(u.getNom());
		}

		if(this.getPrenom() == null){
			p = u.getPrenom() == null;
		}
		else{
			p = this.getPrenom().equals(u.getPrenom());
		}

		if(this.getAdressePostale() == null){
			a = u.getAdressePostale() == null;
		}
		else{
			a = this.getAdressePostale().equals(u.getAdressePostale());
		}
		return a && e && p && n && this.getCompte().equals(u.getCompte()) && (this.isBloque().equals(u.isBloque()));
	}



	@Override
	public String toString(){
		return this.getCompte().toString()+"-"+this.getPrenom()+" "+this.getNom();
	}
}
