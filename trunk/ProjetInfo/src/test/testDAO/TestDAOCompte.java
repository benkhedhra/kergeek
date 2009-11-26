package test.testDAO;

import gestionBaseDeDonnees.DAOCompte;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;

import org.junit.Test;

public class TestDAOCompte extends TestCase{
	@Test
	public void testcreateCompte() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(1);
		Boolean b = DAOCompte.createCompte(c1);
		assertEquals(true,(boolean) b);
	}
	
	@Test
	public void testUpdateCompte() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(1);
		DAOCompte.createCompte(c1);
		Boolean b = DAOCompte.updateCompte(c1);
		assertEquals(true,(boolean) b);
	}
	@Test
	public void testgetCompteById() throws SQLException, ClassNotFoundException{
		
		Compte c2 = new Compte(3,"mail");
		DAOCompte.createCompte(c2);
		String s = c2.getId();
		Compte c3 = DAOCompte.getCompteById(s);
		assertEquals(s, c3.getId());
		assertEquals(c2.getMotDePasse(), c3.getMotDePasse());
		//assertEquals(c2.getType(), c3.getType(),"type");
		assertEquals(c2.getAdresseEmail(), c3.getAdresseEmail());
	}
	@Test
	public void testgetCompteByAdresseEmail() throws SQLException, ClassNotFoundException{

		Compte c2 = new Compte(3,"mail");
		DAOCompte.createCompte(c2);
		Compte c3 = DAOCompte.getCompteByAdresseEmail("mail");
		assertEquals(c2.getAdresseEmail(), c3.getAdresseEmail());
		assertEquals(c2.getMotDePasse(), c3.getMotDePasse());
	}
}
