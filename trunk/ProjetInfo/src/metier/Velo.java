package metier;

/**
 * La classe Velo repr�sente les v�los du parc.
 * @author KerGeek
 */
public class Velo {

	// Attributs
	
	/**
	 * L'identifiant d'un Velo est unique. Une fois attribu�, il ne doit pas �tre modifi�.
	 * @see Velo#getId()
	 * @see Velo#setId(String)
	 */
	private String id;
	
	/**
	 * Le lieu o� est parqu� le Velo
	 * @see Velo#getLieu()
	 * @see Velo#setLieu(Lieu)
	 */
	private Lieu lieu;
	
	/**
	 * Est vrai si le Velo n'est pas en �tat de marche, c'est-�-dire est en panne,
	 * faux sinon.
	 * @see Technicien#intervenir(Velo)
	 * @see Velo#isEnPanne()
	 * @see Velo#setEnPanne(boolean)
	 */
	private boolean enPanne;
	
	/**
	 * Attribu� lorsque le Velo est emprunt�.
	 * @see Utilisateur#emprunteVelo(Velo)
	 */
	private Emprunt empruntEnCours;

	
	// Constructeurs
	
	/**
	 * Constructeur par d�faut d'un Velo.
	 */
	public Velo() {
		super();
	}
	
	/**
	 * Cr�ation d'un Velo en �tat de marche � partir d'un {@link Lieu} auquel il est directement assign�.
	 * @param lieu
	 */
	public Velo(Lieu lieu) {
		this.setLieu(lieu);
		this.setEnPanne(false);
	}


	//Accesseurs et Mutateurs
	
	/**
	 * @return {@link Velo#id}
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Initialise {@link Velo#id}.
	 * @param idVelo
	 */
	public void setId(String idVelo) {
		this.id = idVelo;
	}

	/**
	 * @return {@link Velo#lieu}
	 */
	public Lieu getLieu() {
		return lieu;
	}
	
	/**
	 * Initialise {@link Velo#lieu}.
	 * @param lieu
	 */
	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
	
	/**
	 * @return {@link Velo#enPanne}
	 */
	public boolean isEnPanne() {
		return enPanne;
	}
	
	/**
	 * Initialise {@link Velo#enPanne}.
	 * @param enPanne
	 */
	public void setEnPanne(boolean enPanne) {
		this.enPanne = enPanne;
	}

	/**
	 * @return {@link Velo#empruntEnCours}
	 */
	public Emprunt getEmpruntEnCours() {
		return empruntEnCours;
	}

	/**
	 * Initialise {@link Velo#empruntEnCours}.
	 * @param emprunt
	 */
	public void setEmpruntEnCours(Emprunt emprunt){
		this.empruntEnCours = emprunt;
	}
	
	
	//M�thode
	
	/**
	 * V�rifie l'�galit� entre deux instances de la classe Velo en comparant les valeurs de leurs attributs respectifs.
	 * @return vrai si les deux instances de la classe Velo ont les m�mes valeurs pour chacun de leurs attributs,
	 * faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		Velo v =(Velo) o;
		Boolean a = false;
		Boolean b = false;
		if (this.getId() == null){
			a = v.getId() == null;
		}
		else{
			a = this.getId().equals(v.getId());
		}
		if (this.getEmpruntEnCours() == null){
			b = v.getEmpruntEnCours() == null;
		}
		else{
			if (this.getEmpruntEnCours().getId() ==null){
				b =	v.getEmpruntEnCours().getId() == null && (this.getEmpruntEnCours().getUtilisateur().equals(v.getEmpruntEnCours().getUtilisateur())) && (this.getEmpruntEnCours().getDateEmprunt().equals(v.getEmpruntEnCours().getDateEmprunt())) && (this.getEmpruntEnCours().getStationEmprunt().equals(v.getEmpruntEnCours().getStationEmprunt()));
			}
			else {
				b =	(this.getEmpruntEnCours().getId().equals(v.getEmpruntEnCours().getId())) && (this.getEmpruntEnCours().getUtilisateur().equals(v.getEmpruntEnCours().getUtilisateur())) && (this.getEmpruntEnCours().getDateEmprunt().equals(v.getEmpruntEnCours().getDateEmprunt())) && (this.getEmpruntEnCours().getStationEmprunt().equals(v.getEmpruntEnCours().getStationEmprunt()));
			}
		}
		return a && b && (this.getLieu().equals(v.getLieu())) && (this.isEnPanne()== v.isEnPanne());
	}

}
