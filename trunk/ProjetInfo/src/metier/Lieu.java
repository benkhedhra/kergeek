package metier;



public abstract class Lieu {

	//Attributs

	protected String id;
	protected String adresse;
	protected int capacite;

	//Constantes

	public static final String ID_GARAGE="0";
	public static final String ADRESSE_GARAGE="pool de velo";
	public static final int CAPACITE_GARAGE=1000;
	public static final Lieu SORTI = null;


	//pas de constructeur puisqu'il s'agit d'une classe abstraite


	//Accesseurs

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	//M�thodes

	//pour moi il y a redondance d'info
	
	public void enleverVelo(Velo velo){
		velo.setLieu(SORTI);
		/*TODO a faire faire par le controller
		 * DAOVelo.updateVelo(velo);
		 */
	}

	public void ajouterVelo(Velo velo){
		velo.setLieu(this);
		/*TODO a faire faire par le controller
		 * DAOVelo.updateVelo(velo);
		 */

	}


	@Override
	public boolean equals(Object o) {
		Lieu l = (Lieu) o;
		Boolean a =false;
		Boolean b =false;
		if (this.getId() == null){
			a = l.getId() == null;
		}
		else{
			a = this.getId().equals(l.getId());
		}
		if (this instanceof Station){
			b = l instanceof Station;
		}
		else{
			b = (this == Garage.getInstance()) && (l == Garage.getInstance());
		}
		return a && b && (this.getAdresse().equals(l.getAdresse())) && (this.getCapacite() == l.getCapacite());
	}



}
