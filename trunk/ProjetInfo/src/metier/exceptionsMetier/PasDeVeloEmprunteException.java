package metier.exceptionsMetier;

import metier.Utilisateur;
import metier.Velo;

/**
 * L�exception PasDeVeloEmprunteException est soulev�e lorsqu�un {@link Utilisateur} essaye de rendre un Velo
 *  alors qu�il n�a pas d�empruntEnCours (et n�a donc pas emprunt� de {@link Velo} actuellement).
 * @see Utilisateur#getEmpruntEnCours()
 * @see Utilisateur#rendreVelo(metier.Station)
 * @author KerGeek
 */
public class PasDeVeloEmprunteException extends Exception{

	public PasDeVeloEmprunteException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
