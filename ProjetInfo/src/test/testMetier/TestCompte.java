package test.testMetier;

import metier.Compte;

import org.junit.Test;

import junit.framework.TestCase;

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
		assertNotSame(s1, s2);
		
	}

}
