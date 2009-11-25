package test.testDAO;

import java.sql.SQLException;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import metier.Administrateur;
import metier.Compte;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDAOAdministrateur extends TestCase{
	@Test
	public void testCreateAdministrateur() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c);
		DAOCompte.createCompte(c);
		Boolean b = DAOAdministrateur.createAdministrateur(a);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testUpdateAdministrateur() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c);
		DAOAdministrateur.createAdministrateur(a);
		Boolean b = DAOAdministrateur.updateAdministrateur(a);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testGetAdministrateurById() throws SQLException, ClassNotFoundException{
		
		Administrateur admin = DAOAdministrateur.getAdministrateurById("a43");
		System.out.println(admin);
		
	}
	@Test
	public void testGetAdministrateurByAdresseEmail() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR);
		Administrateur a = new Administrateur(c);
		DAOAdministrateur.createAdministrateur(a);
		String s = c.getAdresseEmail();
		Administrateur admin = DAOAdministrateur.getAdministrateurByAdresseEmail(s);
		assertTrue(a.equals(admin));
	}

}
