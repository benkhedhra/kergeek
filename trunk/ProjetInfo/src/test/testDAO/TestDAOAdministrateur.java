package test.testDAO;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Administrateur;
import metier.Compte;

import org.junit.Test;

public class TestDAOAdministrateur extends TestCase{
	@Test
	public void testCreateAdministrateur() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR,"atest@gmail.com");
		Administrateur a = new Administrateur(c);
		DAOCompte.createCompte(c);
		Boolean b = DAOAdministrateur.createAdministrateur(a);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testUpdateAdministrateur() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c = new Compte(Compte.TYPE_ADMINISTRATEUR,"atest@gmail.com");
		Administrateur a = new Administrateur(c);
		DAOAdministrateur.createAdministrateur(a);
		Boolean b = DAOAdministrateur.updateAdministrateur(a);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testGetAdministrateurById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		
		Administrateur admin = DAOAdministrateur.getAdministrateurById("a1");
		assertEquals("kergeek@gmail.com", admin.getCompte().getAdresseEmail());
		
	}
	@Test
	public void testGetAdministrateurByAdresseEmail() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Administrateur admin = DAOAdministrateur.getAdministrateurByAdresseEmail("kergeek@gmail.com");
		assertEquals("lapin", admin.getCompte().getMotDePasse());
	}

}
