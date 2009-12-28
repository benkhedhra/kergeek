package metier;

/**
 * 
 * @author KerGeek
 */
public class Velo {

	// Attributs

	private String id;
	private Lieu lieu;
	private boolean enPanne;
	private Emprunt empruntEnCours;

	
	// Constructeurs

	public Velo() {
		super();
	}

	public Velo(Lieu lieu) {
		this.setLieu(lieu);
	}

	public Velo(Lieu lieu, boolean enPanne) {
		this.setLieu(lieu);
		this.setEnPanne(enPanne);
	}



	//Accesseurs et Mutateurs

	public String getId() {
		return id;
	}

	public void setId(String idVelo) {
		this.id = idVelo;
	}


	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	public boolean isEnPanne() {
		return enPanne;
	}

	public void setEnPanne(boolean enPanne) {
		this.enPanne = enPanne;
	}


	public Emprunt getEmpruntEnCours() {
		return empruntEnCours;
	}


	public void setEmpruntEnCours(Emprunt emprunt){
		this.empruntEnCours = emprunt;
	}
	
	
	//Méthodes
	
	/**
	 * Vérifie l'égalité entre deux instances de la classe Velo en comparant les valeurs de leurs attributs respectifs.
	 * @return un booléen
	 * qui vaut vrai si les deux instances de la classe Compte ont les mêmes valeurs pour chacun de leurs attributs,
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
