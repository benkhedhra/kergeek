package gestionBaseDeDonnees.exceptionsTechniques;

/**
 * L�exception ConnexionFermeeException est soulev�e lorsque la connection � Oracle est interrompue 
 * au cours d'un acc�s � la base de donn�es.
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