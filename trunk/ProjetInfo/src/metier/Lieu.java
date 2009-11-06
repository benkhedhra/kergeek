package metier;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class Lieu {

	//Attributs

	private String id;
	private int type;
	private String adresse;
	private int capacite;


	//Constantes

	public static final int TYPE_STATION=1;
	public static final int TYPE_GARAGE=2;
	public static final String ID_GARAGE="";
	public static final String ADRESSE_GARAGE="";
	public static final int CAPACITE_GARAGE=1000;
	public static final String SORTI = null;


	//pas de constructeur puisqu'il s'agit d'une classe abstraite


	//Accesseurs

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}


	//Methodes


	public boolean enleverVelo(Velo velo) throws SQLException, ClassNotFoundException{
		velo.setLieu(null);
		return DAOVelo.updateVelo(velo);
		/*TODO
		 * autre requete possible : "UPDATE Velo SET idLieu = '" + SORTI + "' WHERE idVelo = '"+ velo.getId() + "'
		 *moins sur que ca marche
		 */
	}

	public boolean ajouterVelo(Velo velo) throws SQLException, ClassNotFoundException{
		velo.setLieu(this);
		return DAOVelo.createVelo(velo);
		/*TODO
		 *autre requete possible : "UPDATE Velo SET idLieu = '" + this.getId() + "' WHERE idVelo = '"+ velo.getId() + "'
		 *moins sur que ca marche
		 */
	}
}
