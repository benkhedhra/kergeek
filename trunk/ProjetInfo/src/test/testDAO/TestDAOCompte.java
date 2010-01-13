package test.testDAO;

import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;

import org.junit.Test;

public class TestDAOCompte extends TestCase{
	@Test
	public void testcreateCompte() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c1 = new Compte(1,"test@gmail.com");
		Boolean b = DAOCompte.createCompte(c1);
		assertEquals(true,(boolean) b);
	}
	
	@Test
	public void testUpdateCompte() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c1 = new Compte(1,"test@gmail.com");
		DAOCompte.createCompte(c1);
		Boolean b = DAOCompte.updateCompte(c1);
		assertEquals(true,(boolean) b);
	}
	@Test
	public void testgetCompteById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		
		Compte c2 = new Compte(3,"mail");
		DAOCompte.createCompte(c2);
		String s = c2.getId();
		Compte c3 = DAOCompte.getCompteById(s);
		assertEquals(s, c3.getId());
		assertEquals(c2.getMotDePasse(), c3.getMotDePasse());
		assertEquals(c2.getAdresseEmail(), c3.getAdresseEmail());
	}
	@Test
	public void testgetCompteByAdresseEmail() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c1 = DAOCompte.getCompteByAdresseEmail("mathieuchedid@gmail.com").get(0);
		System.out.println(c1.getAdresseEmail());
		System.out.println(c1.getId());
		System.out.println(c1.getMotDePasse());
		System.out.println(c1.getType());
		Compte c2 = new Compte();
		c2.setActif(true);
		c2.setAdresseEmail("mathieuchedid@gmail.com");
		c2.setId("u2");
		c2.setMotDePasse("koala");
		c2.setType(Compte.TYPE_UTILISATEUR);
		assertTrue(c1.equals(c2));
	}
}
