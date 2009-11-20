package test;

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
		assertEquals("test",(Boolean)b,0);
	}
	
	
}
