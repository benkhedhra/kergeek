package gestionBaseDeDonnees.exceptionsTechniques;

import gestionBaseDeDonnees.UtilitaireSQL;

/**
 * L�exception ExceptionAuthentification est soulev�e lorsque l'on essaye de se connecter � avec 
 * un identifiant et/ou un mot de passe on valide.
 * @see UtilitaireSQL#tester(String, String)
 * @author sbalmand
 */
public class ExceptionAuthentification extends Exception {

	public ExceptionAuthentification(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
	

}
