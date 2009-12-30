package gestionBaseDeDonnees.exceptionsTechniques;


/**
 * LÕexception ConnexionFermeeException est soulevée lorsque l'on parcourt la la base de données
 * à la recherche de quelque chose qui en est absent.
 * @author KerGeek
 */
public class PasDansLaBaseDeDonneeException extends Exception{

	public PasDansLaBaseDeDonneeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
