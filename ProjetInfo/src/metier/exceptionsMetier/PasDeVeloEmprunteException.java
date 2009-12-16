package metier.exceptionsMetier;

import metier.Utilisateur;
import metier.Velo;

/**
 * L’exception PasDeVeloEmprunteException est soulevÈe lorsqu’un {@link Utilisateur} essaye de rendre un Velo
 *  alors qu’il n’a pas d’empruntEnCours (et n’a donc pas emprunté de {@link Velo} actuellement).
 * @author KerGeek
 *@see Utilisateur#getEmpruntEnCours()
 *@see Utilisateur#rendreVelo(metier.Station)
 */
public class PasDeVeloEmprunteException extends Exception{

	public PasDeVeloEmprunteException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
