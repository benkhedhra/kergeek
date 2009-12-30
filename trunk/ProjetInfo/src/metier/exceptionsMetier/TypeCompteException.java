package metier.exceptionsMetier;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;
import metier.Utilisateur;

/**
 * L�exception TypeCompteException est soulev�e lorsqu�un {@link Administrateur} essaye de cr�er un {@link Compte}
 * de type {@link Administrateur}, {@link Technicien} ou {@link Utilisateur}  mais qu�il utilise un type qui n�est
 * pas associ� � celui qu�il veut cr�er.
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
