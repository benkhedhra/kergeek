package test;

import junit.framework.TestCase;
import metier.Compte;
import metier.Utilisateur;

public class UtilisateurTest extends TestCase{
	public void rendreVeloTest(){
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
	}
}
