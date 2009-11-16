package test;

import junit.framework.TestCase;
import metier.Compte;
import metier.Utilisateur;

import org.junit.Test;

public class TestUtilisateur extends TestCase{
	
	@Test
	public void testRendreVeloTest(){
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
	}
}
