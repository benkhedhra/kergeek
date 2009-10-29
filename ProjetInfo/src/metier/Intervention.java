package metier;

import java.util.Date;

public class Intervention {
	
	//Attributs
	
	private String id;
	private Velo velo;
	private Date date;
	private TypeIntervention typeIntervention;
	
	
	
	
	//Constructeur
	
	public Intervention(Velo velo, Date date) {
		super();
		this.setVelo(velo);
		this.setDate(date);
		this.setTypeIntervention(null);
	}

	
	public Intervention(Velo velo,Date date, TypeIntervention typeIntervention) {
		super();
		this.setVelo(velo);
		this.setDate(date);
		this.setTypeIntervention(typeIntervention);
	}
	
	

	//Accesseurs
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Velo getVelo() {
		return velo;
	}


	public void setVelo(Velo velo) {
		this.velo = velo;
	}


	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public TypeIntervention getTypeIntervention() {
		return typeIntervention;
	}
	public void setTypeIntervention(TypeIntervention typeIntervention) {
		this.typeIntervention = typeIntervention;
	}

	

}
