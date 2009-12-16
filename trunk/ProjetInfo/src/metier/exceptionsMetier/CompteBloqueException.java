package metier.exceptionsMetier;

import metier.Compte;
import metier.Utilisateur;
import metier.Velo;

/**
 * L�exception CompteBloqueException est soulev�e lorsqu�un {@link Utilisateur} dont le {@link Compte} est bloqu� 
 * (parce qu�il a d�pass� le temps maximum d�emprunt d�un v�lo par exemple) essaye n�anmoins d�emprunter un {@link Velo}.
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
