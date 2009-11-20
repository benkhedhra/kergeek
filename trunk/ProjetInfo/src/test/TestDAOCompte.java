package test;

import gestionBaseDeDonnees.DAOCompte;

import java.sql.SQLException;

import metier.Administrateur;
import metier.Compte;

import org.junit.Test;

import junit.framework.TestCase;

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
		Compte c1 = new Compte();
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(3,"mail");
		String s = c2.getId();
		Compte c3 = DAOCompte.getCompteById(s);
		assertEquals(s, c3.getId());
		assertEquals(c2.getMotDePasse(), c3.getMotDePasse());
		//assertEquals(c2.getType(), c3.getType(),"type");
		assertEquals(c2.getAdresseEmail(), c3.getAdresseEmail());
	}
	@Test
	public void testgetCompteByAdresseEmail() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte();
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(3,"mail");
		Compte c3 = DAOCompte.getCompteByAdresseEmail("mail");
		assertEquals(c2.getAdresseEmail(), c3.getAdresseEmail());
		assertEquals(c2.getMotDePasse(), c3.getMotDePasse());
	}
}
