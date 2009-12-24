package metier;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TypesIntervention {

	//Attributs

	private Map<Integer,String> typesIntervention;


	//Constructeur

	public TypesIntervention() throws SQLException, ClassNotFoundException {
		this.setTypesIntervention(new HashMap<Integer,String>());
	}


	//Accesseurs et Mutateurs

	public Map<Integer,String> getTypesIntervention() {
		return typesIntervention;
	}


	public void setTypesIntervention(Map<Integer,String> typesIntervention) {
		this.typesIntervention = typesIntervention;
	}

	//Méthodes

	public int getType(String description) {
		int id = 0;
		for (int cle : this.getTypesIntervention().keySet()) {
			if(this.getTypesIntervention().get(cle) == description){
				id = cle;
			}
		}
		return id;
	}

	public String getDescription(int type) {
		return this.getTypesIntervention().get(type);
	}

	
	

}
