package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Administrateur;
import metier.Compte;

import org.junit.Test;

public class TestAdministrateur extends TestCase{
	
	@Test
	public void testResilierCompte() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c1);
		Compte c2 = new Compte(Compte.TYPE_UTILISATEUR);
		a.resilierCompte(c2);
		assertFalse(c2.isActif());
	}
	
	@Test
	public void testCreerCompte() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(Compte.TYPE_UTILISATEUR, "mail");
		assertEquals("mail", c2.getAdresseEmail());
		assertEquals(Compte.TYPE_UTILISATEUR, c2.getType());
	}
	
	@Test
	public void testDemanderAssignation(){
		
	}
	
}
