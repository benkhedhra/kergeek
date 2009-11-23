package test.testDAO;

import java.sql.SQLException;

import gestionBaseDeDonnees.DAOUtilisateur;
import metier.Compte;
import metier.Utilisateur;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDAOUtilisateur extends TestCase {
	@Test
	public void testcreateUtilisateur() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(1, "e-mail", true);
		Utilisateur u = new Utilisateur(c);
		assertEquals((Boolean)DAOUtilisateur.createUtilisateur(u),(Boolean)true);
	}
	
	@Test
	public void testupdateUtilisateur() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(1, "e-mail", true);
		Utilisateur u = new Utilisateur(c);
		DAOUtilisateur.createUtilisateur(u);
		assertEquals((Boolean)DAOUtilisateur.updateUtilisateur(u),(Boolean)true);
	}
	@Test
	public void testGetUtilisateurById(){
		
	}
	
	@Test
	public void testGetUtilisateurByAdresseEmail(){
		
	}
	@Test
	public void testGetUtilisateurByNom(){
		
	}
}
