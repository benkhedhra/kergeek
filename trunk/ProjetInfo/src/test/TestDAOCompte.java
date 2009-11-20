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
		Compte c2 = a.creerCompte(3,"mail1");
		Compte c3 = DAOCompte.getCompteById(c2.getId());
		assertEquals(c2.getId(), c3.getId());
		assertEquals(c2.getMotDePasse(), c3.getMotDePasse());
		assertEquals(c2.getType(), c3.getType());
		assertEquals(c2.getAdresseEmail(), c3.getAdresseEmail());
		assertEquals(c2.isActif(), c3.isActif());
	}
	@Test
	public void testgetCompteByAdresseEmail() throws SQLException, ClassNotFoundException{
		Compte c1 = new Compte(1,"mail2");
		DAOCompte.createCompte(c1);
		c1 = DAOCompte.getCompteById(c1.getId());
		Compte c2 = DAOCompte.getCompteByAdresseEmail("mail2");
		assertEquals(c1.getId(), c2.getId());
		assertEquals(c1.getMotDePasse(), c2.getMotDePasse());
		assertEquals(c1.getType(), c2.getType());
		assertEquals(c1.getAdresseEmail(), c2.getAdresseEmail());
		assertEquals(c1.isActif(), c2.isActif());
	}
}
