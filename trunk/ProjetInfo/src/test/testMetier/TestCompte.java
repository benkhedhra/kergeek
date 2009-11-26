package test.testMetier;

import junit.framework.TestCase;
import metier.Compte;

import org.junit.Test;

public class TestCompte extends TestCase{
	@Test
	public void testGenererMotDePasse(){
		Compte c1 = new Compte(); 
		c1.genererMotDePasse();
		String s1 = c1.getMotDePasse();
		Compte c2 = new Compte(); 
		c2.genererMotDePasse();
		String s2 = c2.getMotDePasse();
		assertTrue(s1 instanceof String);
		assertTrue(s1.length() == 6);
		assertTrue(s2.length() == 6);
		assertNotSame(s1, s2);
		}
	
	@Test
	public void testEqualsCompte(){
		Compte c1 = new Compte(Compte.TYPE_ADMINISTRATEUR, "email");
		Compte c2 = new Compte(Compte.TYPE_ADMINISTRATEUR, "email");
		assertFalse(c1.equals(c2));
		assertTrue(c2.equals(c2));
		c2.setMotDePasse(c1.getMotDePasse());
		assertTrue(c1.equals(c2));
	}

}
