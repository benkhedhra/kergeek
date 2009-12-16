package metier.exceptionsMetier;

import metier.Emprunt;

/**
 * L’exception PasDeDateRetourException est soulevÈe lorsqu’on essaye de calculer le temps d’emprunt 
 * d’un emprunt en cours (qui ne possËde pas encore de {@link EmpruntdateRetour}).
 *@author KerGeek
 *@see Emprunt
 *@see Emprunt#getTempsEmprunt()
 */
public class PasDeDateRetourException extends Exception{

}
