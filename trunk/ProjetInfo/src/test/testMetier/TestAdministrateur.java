package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Administrateur;
import metier.Compte;

import org.junit.Test;

public class TestAdministrateur extends TestCase{
	
	@Test
	public void testResilierCompte() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR,"atest@gmail.com");
		Administrateur a = new Administrateur(c);
		Compte compte = new Compte();
		a.resilierCompte(compte);
		Boolean b = compte.isActif();
		assertEquals((Boolean)false,(Boolean)b);

	}
	
	@Test
	public void testCreerCompte() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(Compte.TYPE_ADMINISTRATEUR,"atest@gmail.com");
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(Compte.TYPE_UTILISATEUR, "mail");
		assertEquals("mail", c2.getAdresseEmail());
		assertEquals(Compte.TYPE_UTILISATEUR, c2.getType());
	}
	

	@Test
	public void testEqualsAdmin(){
		Compte c1 = new Compte(Compte.TYPE_ADMINISTRATEUR, "email");
		Administrateur a1 = new Administrateur(c1);
		Compte c2 = new Compte(Compte.TYPE_ADMINISTRATEUR, "email");
		Administrateur a2 = new Administrateur(c2);
		Administrateur a3 = new Administrateur(c2);
		assertFalse(a1.equals(a2));
		assertTrue(a2.equals(a3));
		assertFalse(a1.equals(a3));
		a2.getCompte().setMotDePasse(c1.getMotDePasse());
		assertTrue(a1.equals(a2));
		a2.getCompte().setAdresseEmail("email2");
		assertFalse(a1.equals(a2));
		a2.getCompte().setActif(false);
		assertFalse(a1.equals(a2));
	}
	
}
