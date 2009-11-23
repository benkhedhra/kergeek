package metier;


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


	public Velo(Lieu lieu, boolean enPanne) {
		this.setLieu(lieu);
		this.setEnPanne(enPanne);
	}



	//Accesseurs

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


	public void setEmprunt(Emprunt emprunt){
		this.empruntEnCours = emprunt;
	}

	@Override
	public boolean equals(Object o) {
		Velo v =(Velo) o;
		return (this.getId()== v.getId()) && (this.getLieu()== v.getLieu())&& (this.isEnPanne()== v.isEnPanne()) && (this.getEmpruntEnCours().equals(v.getEmpruntEnCours()) ) && (this.getEmpruntEnCours().getId().equals(v.getEmpruntEnCours().getId())) && (this.getEmpruntEnCours().getUtilisateur().equals(v.getEmpruntEnCours().getUtilisateur())) && (this.getEmpruntEnCours().getDateEmprunt().equals(v.getEmpruntEnCours().getDateRetour())) && (this.getEmpruntEnCours().getLieuEmprunt().equals(v.getEmpruntEnCours().getLieuEmprunt())) && (this.getEmpruntEnCours().getLieuRetour().equals(v.getEmpruntEnCours().getLieuRetour()));
	}
	
}
