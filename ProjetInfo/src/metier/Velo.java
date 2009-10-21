package metier;

public class Velo {
	private String idVelo;
	private boolean enPanne;
	
	
	
	
	public Velo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public Velo(String idVelo, boolean enPanne) {
		super();
		this.idVelo = idVelo;
		this.enPanne = enPanne;
	}

	
	
	
	public String getIdVelo() {
		return idVelo;
	}

	public void setIdVelo(String idVelo) {
		this.idVelo = idVelo;
	}

	public boolean isEnPanne() {
		return enPanne;
	}

	public void setEnPanne(boolean enPanne) {
		this.enPanne = enPanne;
	}
	
	

}
