package metier;

public class Velo {
	private String id;
	private Lieu lieu;
	private boolean enPanne;
	
	
	
	
	public Velo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public Velo(String identifiant, boolean enPanne) {
		super();
		this.setId(id);
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
	
	

}
