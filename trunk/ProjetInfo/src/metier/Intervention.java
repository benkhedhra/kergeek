package metier;

import java.util.Date;

public class Intervention {
	
	//Attributs
	
	private String id;
	private Velo velo;
	private Date date;
	private TypesIntervention typeIntervention;
	
	
	
	
	//Constructeur
	
	public Intervention(Velo velo, Date date) {
		super();
		this.setVelo(velo);
		this.setDate(date);
		this.setTypeIntervention(null);
	}

	
	public Intervention(Velo velo,Date date, TypesIntervention typeIntervention) {
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
	public TypesIntervention getTypeIntervention() {
		return typeIntervention;
	}
	public void setTypeIntervention(TypesIntervention typeIntervention) {
		this.typeIntervention = typeIntervention;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	
	

}
