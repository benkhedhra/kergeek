package metier;

import gestionBaseDeDonnees.DAOTypeIntervention;

import java.sql.SQLException;
import java.util.Map;

public class TypeIntervention {

	//Attributs

	private Map<Integer,String> typeIntervention;


	//Constructeur

	public TypeIntervention() throws SQLException, ClassNotFoundException {
		super();
		Map<Integer,String> typeIntervention = DAOTypeIntervention.getAllTypeIntervention();
		this.setTypeIntervention(typeIntervention);
	}


	//Accesseurs

	public Map<Integer,String> getTypeIntervention() {
		return typeIntervention;
	}


	public void setTypeIntervention(Map<Integer,String> typeIntervention) {
		this.typeIntervention = typeIntervention;
	}

	//Methodes

	public int getType(String description) {
		int id = 0;
		for (int cle : this.getTypeIntervention().keySet()) {
			if(this.getTypeIntervention().get(cle) == description){
				id = cle;
			}
		}
		return id;
	}

	public String getDescription(int type) {
		return this.getTypeIntervention().get(type);
	}

}
