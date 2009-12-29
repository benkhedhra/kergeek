package metier.exceptionsMetier;

import metier.Compte;
import metier.Utilisateur;
import metier.Velo;

/**
 * LÕexception CompteBloqueException est soulevée lorsquÕun {@link Utilisateur} dont le {@link Compte} est bloqué 
 *  (parce quÕil a dépassé le temps maximum dÕemprunt dÕun vélo par exemple) essaye néanmoins dÕemprunter un {@link Velo}.
 * @see Utilisateur#emprunteVelo(metier.Velo)
 * @author KerGeek
 */
public class CompteBloqueException extends Exception {

	private static final long serialVersionUID = 1L;

	public CompteBloqueException() {
		super();
	}

	public CompteBloqueException(String msg) {
		super(msg);
	}

}
