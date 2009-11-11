package test;

import junit.framework.TestCase;
import metier.Administrateur;
import metier.Compte;

public class AdministrateurTest extends TestCase{
	public void TestCreerCompte(){
		Compte c = new Compte();
		 assertEquals(c,Administrateur.creerCompte());
	}
}
