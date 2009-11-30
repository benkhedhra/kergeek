package metier;

public class Sortie extends Lieu {

	//Attributs
	
	private static Sortie sortie = null;
	
	public Sortie() {
		this.setId(Lieu.ID_SORTIE);
		this.setAdresse(Lieu.ADRESSE_SORTIE);
	}
	
//Methodes
	
	public static Sortie getInstance(){
		if (sortie == null){
			sortie = new Sortie();
		}
		return sortie;
	}
	
	

}
