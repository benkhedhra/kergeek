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
				b =	v.getEmpruntEnCours().getId() == null && (this.getEmpruntEnCours().getUtilisateur().equals(v.getEmpruntEnCours().getUtilisateur())) && (this.getEmpruntEnCours().getDateEmprunt().equals(v.getEmpruntEnCours().getDateEmprunt())) && (this.getEmpruntEnCours().getLieuEmprunt().equals(v.getEmpruntEnCours().getLieuEmprunt()));
			}
			else {
				b =	(this.getEmpruntEnCours().getId().equals(v.getEmpruntEnCours().getId())) && (this.getEmpruntEnCours().getUtilisateur().equals(v.getEmpruntEnCours().getUtilisateur())) && (this.getEmpruntEnCours().getDateEmprunt().equals(v.getEmpruntEnCours().getDateEmprunt())) && (this.getEmpruntEnCours().getLieuEmprunt().equals(v.getEmpruntEnCours().getLieuEmprunt()));
			}
		}
		return a && b && (this.getLieu().equals(v.getLieu())) && (this.isEnPanne()== v.isEnPanne());
	}

}
