package metier;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TypeIntervention {

	//Attributs

	private Map<Integer,String> typeIntervention;


	//Constructeur

	public TypeIntervention() throws SQLException, ClassNotFoundException {
		this.setTypeIntervention(new HashMap<Integer,String>());
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


	@Override
	public boolean equals(Object o) {
		TypeIntervention t = (TypeIntervention) o;
		return this.getTypeIntervention().equals(t.getTypeIntervention());
		/*TODO
		 * a verifier
		 */
	}
	
	

}
