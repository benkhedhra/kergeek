package metier.exceptionsMetier;

import metier.Compte;
import metier.Utilisateur;
import metier.Velo;

/**
 * LÕexception CompteBloqueException est soulevée lorsquÕun {@link Utilisateur} dont le {@link Compte} est bloqué 
 * (parce quÕil a dépassé le temps maximum dÕemprunt dÕun vélo par exemple) essaye néanmoins dÕemprunter un {@link Velo}.
 *@author KerGeek
 *@see Utilisateur#emprunteVelo(metier.Velo)
 */
public class CompteBloqueException extends Exception {

	public CompteBloqueException() {
		super();
	}

	public CompteBloqueException(String msg) {
		super(msg);
	}

}
