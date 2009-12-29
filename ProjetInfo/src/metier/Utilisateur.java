package metier;

import java.sql.SQLException;

import metier.exceptionsMetier.CompteBloqueException;
import metier.exceptionsMetier.PasDeDateRetourException;
import metier.exceptionsMetier.PasDeVeloEmprunteException;

/**
 * Utilisateur est la classe representant un abonné du parc à vélos
 * @see Compte
 * @author KerGeek
 */
public class Utilisateur {

	//Attributs
	
	/**
	 * Compte de l'Utilisateur. Ce compte est modifiable.
	 * @see Utilisateur#getCompte()
	 * @see Utilisateur#setCompte(Compte)
	 */
	private Compte compte;
	
	/**
	 * Nom de famille de l'Utilisateur.
	 * @see Utilisateur#getNom()
	 * @see Utilisateur#setNom(String)
	 */
	private String nom;
	
	/**
	 * Prénom de l'Utilisateur.
	 * @see Utilisateur#getPrenom()
	 * @see Utilisateur#setPrenom(String)
	 */
	private String prenom;
	
	/**
	 * Adresse postale de l'Utilisateur.
	 * @see Utilisateur#getAdressePostale()
	 * @see Utilisateur#setAdressePostale(String)
	 */
	private String adressePostale;
	
	/**
	 * Est vrai si le compte de l'Utilisateur à été bloqué (suite à un emprunt trop long par exemple),
	 * faux si ce dernier peut utilisé le parc de vélos normalement.
	 * @see Utilisateur#isBloque()
	 * @see Utilisateur#setBloque(boolean)
	 * @see Utilisateur#emprunteVelo(Velo)
	 * @see Utilisateur#rendreVelo(Station)
	 */
	private boolean bloque;
	
	/**
	 * Attribué lorsque l'Utilisateur a emprunté un {@link Velo} et ne l'a pas encore rendu.
	 * @see Utilisateur#getEmpruntEnCours()
	 * @see Utilisateur#setEmpruntEnCours(Emprunt)
	 * @see Utilisateur#emprunteVelo(Velo)
	 */	private Emprunt empruntEnCours;

	 
	//Constructeurs
	
	/**
	 * Constructeur par défaut d'un Utilisateur. 
	 *  Il n'a pas d'{@link Utilisateur#empruntEnCours} et {@link Utilisateur#bloque} est faux.
	 */
	public Utilisateur(){
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}	

	/**
	 * Création d'un Utilisateur à partir de {@link Utilisateur#compte}. 
	 *  Il n'a pas d'{@link Utilisateur#empruntEnCours} et {@link Utilisateur#bloque} est faux.
	 * @param compte
	 */
	public Utilisateur(Compte compte) {
		super();
		this.setCompte(compte);
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}
	
	/**
	 * Création d'un Utilisateur à partir des éléments suivants : {@link Utilisateur#compte},
	 *  {@link Utilisateur#nom} et {@link Utilisateur#prenom}. 
	 *  Il n'a pas d'{@link Utilisateur#empruntEnCours} et {@link Utilisateur#bloque} est faux.
	 * @param compte
	 * @param nom
	 * @param prenom
	 */
	public Utilisateur(Compte compte, String nom, String prenom) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}
	
	/**
	 * Création d'un Utilisateur à partir des éléments suivants : {@link Utilisateur#compte},
	 *  {@link Utilisateur#nom}, {@link Utilisateur#prenom} et {@link Utilisateur#adressePostale}. 
	 *  Il n'a pas d'{@link Utilisateur#empruntEnCours} et {@link Utilisateur#bloque} est faux.
	 * @param compte
	 * @param nom
	 * @param prenom
	 * @param adresse
	 */
	public Utilisateur(Compte compte, String nom, String prenom, String adresse) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdressePostale(adresse);
		this.setBloque(false);
		this.setEmpruntEnCours(null);
	}

	/**
	 * Création d'un Utilisateur à partir des éléments suivants : {@link Utilisateur#compte},
	 *  {@link Utilisateur#nom}, {@link Utilisateur#prenom}, {@link Utilisateur#adressePostale} et {@link Utilisateur#bloque}. 
	 *  Il n'a pas d'{@link Utilisateur#empruntEnCours}.
	 * @param compte
	 * @param nom
	 * @param prenom
	 * @param adressePostale
	 * @param bloque
	 */
	public Utilisateur(Compte compte, String nom, String prenom, String adressePostale, boolean bloque) {
		super();
		this.setCompte(compte);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAdressePostale(adressePostale);
		this.setBloque(bloque);
		this.setEmpruntEnCours(null);
	}

	
	//Accesseurs et Mutateurs
	
	/**
	 * @return {@link Utilisateur#compte}
	 */
	public Compte getCompte() {
		return compte;
	}
	
	/**
	 * Initialise {@link Utilisateur#compte}.
	 * @param compte
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	/**
	 * @return {@link Utilisateur#nom}
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Initialise {@link Utilisateur#nom}.
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * @return {@link Utilisateur#prenom}
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Initialise {@link Utilisateur#prenom}.
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * @return {@link Utilisateur#adressePostale}
	 */
	public String getAdressePostale() {
		return adressePostale;
	}
	
	/**
	 * Initialise {@link Utilisateur#adressePostale}.
	 * @param adresse
	 */
	public void setAdressePostale(String adresse) {
		this.adressePostale = adresse;
	}

	/**
	 * @return vrai si le compte de l'Utilisateur est desactivé,
	 *  faux sinon.
	 */
	public Boolean isBloque() {
		return bloque;
	}
	
	/**
	 * Initilaise {@link Utilisateur#bloque}.
	 * @param bloque
	 */
	public void setBloque(boolean bloque) {
		this.bloque = bloque;
	}
	
	/**
	 * @return {@link Utilisateur#empruntEnCours}
	 */
	public Emprunt getEmpruntEnCours() {
		return empruntEnCours;
	}
	
	/**
	 * Initialise {@link Utilisateur#empruntEnCours}.
	 * @param emprunt
	 */
	public void setEmpruntEnCours(Emprunt emprunt) {
		this.empruntEnCours = emprunt;
	}


	//Méthodes
	
	/**
	 * L'utilisateur emprunte un {@link Velo} du parc.
	 * @param velo 
	 * le vélo que l'Utilisateur emrunte
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompteBloqueException
	 * @see Emprunt#Emprunt(Utilisateur, Velo, java.sql.Date, Station)
	 * @see UtilitaireDate#dateCourante()
	 * @see Velo#setEmpruntEnCours(Emprunt)
	 * @see Lieu#enleverVelo(Velo)
	 */
	public void emprunteVelo(Velo velo) throws SQLException, ClassNotFoundException, CompteBloqueException{
		if (!this.bloque){
			/* Un Utilisateur ayant déjà un empruntEnCours ne se verra jamais proposer d'emprunter un Velo, 
			 * il n'y a donc pas lieu de tester cela ici.
			 */
			velo.setEmpruntEnCours(new Emprunt(this, velo, UtilitaireDate.dateCourante(), (Station) velo.getLieu()));
			this.setEmpruntEnCours(velo.getEmpruntEnCours());
			velo.getLieu().enleverVelo(velo);
		}
		else{
			throw new CompteBloqueException();
		}
	}

	/**
	 * L'Utilisateur rend un {@link Velo} emprunté au préalable. S'il l'a emprunté trop longtemps, son compte se bloque.
	 * @param station
	 * la station o le vélo est rendu
	 * @return l'emprunt complet (avec sa DateRetour et sa stationRetour assignés)
	 * @throws PasDeVeloEmprunteException
	 * @throws PasDeDateRetourException
	 * @see Lieu#ajouterVelo(Velo)
	 * @see Emprunt
	 * @see Utilisateur#bloque
	 */
	public Emprunt rendreVelo(Station station) throws PasDeVeloEmprunteException, PasDeDateRetourException{
		Emprunt emprunt = null;
		// cet emprunt sera une trace de l'ancien emprunt pour pallier au this.setVelo(null);
			if (this.getEmpruntEnCours() == null){
				throw new PasDeVeloEmprunteException("L'utilisateur n'a actuellement pas emprunté de velo");
			}
			else{
				Velo velo = this.getEmpruntEnCours().getVelo();
				station.ajouterVelo(velo);
				emprunt = new Emprunt(empruntEnCours);
				emprunt.setDateRetour(UtilitaireDate.dateCourante());
				emprunt.setStationRetour(station);
				if (emprunt.getTempsEmprunt()>Emprunt.TPS_EMPRUNT_MAX){
					//emprunt trop long
					this.setBloque(true);
					System.out.println("compte bloqué");
					//TODO
					System.out.println("date de l'emprunt : " + emprunt.getDateEmprunt());
					System.out.println("date de retour : " + emprunt.getDateRetour());
					System.out.println("durée de l'emprunt : " + emprunt.getTempsEmprunt());
					
				}
				velo.setEmpruntEnCours(null);
				this.setEmpruntEnCours(null);
			}
		return emprunt;
	}

	
	/**
	 * Vérifie l'égalité entre deux instances de la classe Utilisateur en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe Utilisateur ont les mêmes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
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


	/**
	 * @return l'identifiant du Compte de l'utilisateur suivi de son adresse email,
	 *  de {@link Utilisateur#prenom} et de {@link Utilisateur#nom}.
	 * @see Compte#toString()
	 */
	@Override
	public String toString(){
		return this.getCompte().toString()+"-"+this.getPrenom()+" "+this.getNom();
	}
}
