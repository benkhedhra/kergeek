package test.testMetier;

import gestionBaseDeDonnees.DAOCompte;

import java.sql.SQLException;

import org.junit.Test;

import junit.framework.TestCase;
import metier.Administrateur;
import metier.Compte;

public class TestAdministrateur extends TestCase{
	
	@Test
	public void testResilierCompte() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c);
		c.setId("id");
		a.resilierCompte("id");
		Boolean b = DAOCompte.getCompteById("id").isActif();
		assertEquals(null,(Boolean)b);
	}
	
	@Test
	public void testCreerCompte() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(3, "mail");
		assertEquals("mail", c2.getAdresseEmail());
	}
	
	@Test
	public void testDemanderAssignation(){
		
	}
	
}
