package metier;

public class Velo {
	
	// Attributs
	
	private String id;
	private Lieu lieu;
	private boolean enPanne;
	private Emprunt emprunt;
	
	
	
	// Constructeurs
	
	public Velo() {
		super();
		this.setEnPanne(true);
		this.setLieu(Garage.getInstance());
	}

	
	public Velo(String identifiant, Lieu lieu, boolean enPanne) {
		super();
		this.setId(id);
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


	public Emprunt getEmprunt() {
		return emprunt;
	}


	public void setEmprunt(Emprunt emprunt) {
		this.emprunt = emprunt;
	}
	
	

}
