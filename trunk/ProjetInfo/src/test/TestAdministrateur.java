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
		Administrateur a = new Administrateur();
		Compte c = new Compte("id","id",3);
		a.resilierCompte("id");
		Boolean b = DAOCompte.getCompteById("id").isActif();
		assertEquals("test",(Boolean)b,0);
	}
	
	
}
