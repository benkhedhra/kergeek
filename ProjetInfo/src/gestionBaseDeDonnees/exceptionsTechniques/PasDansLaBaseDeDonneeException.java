package gestionBaseDeDonnees.exceptionsTechniques;


/**
 * L�exception ConnexionFermeeException est soulev�e lorsque l'on parcourt la la base de donn�es
 * � la recherche de quelque chose qui en est absent.
 * @author KerGeek
 */
public class PasDansLaBaseDeDonneeException extends Exception{

	public PasDansLaBaseDeDonneeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
