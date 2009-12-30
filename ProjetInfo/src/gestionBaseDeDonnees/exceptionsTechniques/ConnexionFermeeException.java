package gestionBaseDeDonnees.exceptionsTechniques;

/**
 * LÕexception ConnexionFermeeException est soulevée lorsque la connection à Oracle est interrompue 
 * au cours d'un accès à la base de données.
 * @author KerGeek
 */
public class ConnexionFermeeException extends Exception {
	
	public ConnexionFermeeException() {
		super();
	}
	
		public ConnexionFermeeException(String message) {
			super(message);
		}

		private static final long serialVersionUID = 1L;
		

	}