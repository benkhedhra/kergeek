package metier.exceptionsMetier;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;
import metier.Utilisateur;

/**
 * LÕexception TypeCompteException est soulevée lorsquÕun {@link Administrateur} essaye de créer un {@link Compte}
 * de type {@link Administrateur}, {@link Technicien} ou {@link Utilisateur}  mais quÕil utilise un type qui nÕest
 * pas associé à celui quÕil veut créer.
 * @author KerGeek
 */
public class TypeCompteException extends Exception {

	private static final long serialVersionUID = 1L;

	public TypeCompteException() {
		super();
	}

	public TypeCompteException(String message) {
		super(message);
	}

}
