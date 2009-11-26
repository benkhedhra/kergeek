package metier;

import java.sql.SQLException;

import exception.PasDeVeloEmprunteException;

public class Utilisateur {

	//Attributs
	private Compte compte;
	private String nom;
	private String prenom;
	private String adressePostale;
	private boolean bloque;
	private Velo velo;

	public Utilisateur(){
		this.setBloque(false);
		this.setVelo(null);
	}	

	public Utilisateur(Compte compte) {
		super();
		this.setCompte(compte);
		this.setBloque(false);
		this.setVelo(null);
	}

	public Utilisateur(Compte compte, String nom, String prenom) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
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


	public Boolean isBloque() {
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

	
	public void emprunteVelo(Velo velo, Station station) throws SQLException, ClassNotFoundException{
		station.enleverVelo(velo);
		this.setVelo(velo);
		velo.setEmpruntEnCours(new Emprunt(this, velo, UtilitaireDate.dateCourante() ,velo.getLieu()));
		
		/*TODO a faire dans le controller
		 * DAOEmprunt.createEmprunt(velo.getEmpruntEnCours()) & DAOVelo.updateVelo(velo);
		 */ 
	}



	public Emprunt rendreVelo(Station station) 
	throws PasDeVeloEmprunteException{
		Emprunt emprunt = null;
		Velo velo = this.getVelo();
		try{
			if (velo == null){
				throw new PasDeVeloEmprunteException("L'utilisateur n'a actuellement pas emprunte de velo");
			}
			else{
				station.ajouterVelo(velo);
				velo.getEmpruntEnCours().setDateRetour(UtilitaireDate.dateCourante());
				velo.getEmpruntEnCours().setLieuRetour(station);
				emprunt = new Emprunt(this, velo, velo.getEmpruntEnCours().getDateEmprunt(), velo.getEmpruntEnCours().getLieuEmprunt(), velo.getEmpruntEnCours().getDateRetour(), velo.getEmpruntEnCours().getLieuRetour());
				velo.setEmpruntEnCours(null);
				this.setVelo(null);
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

		Boolean v = false;
		Boolean n = false;
		Boolean p = false;
		Boolean a = false;

		if(this.getVelo() == null){
			v = u.getVelo() == null;
		}
		else{
			v = this.getVelo().equals(u.getVelo());	
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

		return a && v && n && this.getCompte().equals(u.getCompte()) && (this.isBloque().equals(u.isBloque()));
	}



	@Override
	public String toString(){
		return this.getCompte().toString()+"-"+this.getPrenom()+" "+this.getNom();
	}
}
