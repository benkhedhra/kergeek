package metier;

import java.sql.Date;

public class Intervention {
	
	//Attributs
	
	private String id;
	private Velo velo;
	private Date date;
	private TypeIntervention typeIntervention;
	
	
	
	
	//Constructeurs
	
	
	public Intervention() {
		super();
	}
	
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


	public java.sql.Date getDate() {
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


	
	
	
	@Override
	public boolean equals(Object o) {
		Intervention i =(Intervention) o;
		Boolean a =false;
		if(this.getId() == null){
			a = i.getId() == null;
		}
		else{
			a = this.getId().equals(i.getId());
		}
		return a && (this.getVelo().equals(i.getVelo())) && (this.getDate().equals(i.getDate())) && (this.getTypeIntervention().equals(i.getTypeIntervention()));
	}

	
	

}
