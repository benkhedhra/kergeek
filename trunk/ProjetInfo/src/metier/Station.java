package metier;

import java.sql.SQLException;

import gestionBaseDeDonnees.DAOVelo;

public class Station extends Lieu {
	
	//Attributs
	
	static final float TAUX_OCCUPATION_MIN = 1/10;
	static final float TAUX_OCCUPATION_MAX = 5/10;
	
	
	//Constructeur
	
	
	public Station() {
		super();
		this.setType(TYPE_STATION);
	}
	
	public Station(String id, String adresse,int capacite) {
		super();
		this.setType(TYPE_STATION);
		this.setId(id);
		this.setAdresse(adresse);
		this.setCapacite(capacite);
	}
	
	//Methodes

	public int calculerTx(String idStation) throws SQLException, ClassNotFoundException{
		
		int nbVelo = DAOVelo.getVeloByLieu(this).size();
		int a = nbVelo/this.getCapacite();
		return a;
	}
	
	public String toString(){
		return this.getId();
	}


}
