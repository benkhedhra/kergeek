package test.testDAO;

import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.Compte;
import metier.Utilisateur;

import org.junit.Test;

public class TestDAOUtilisateur extends TestCase {
	
	@Test
	public void testcreateUtilisateur() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c = new Compte(Compte.TYPE_UTILISATEUR, "e-mail1");
		Utilisateur u1 = new Utilisateur(c,"john", "michel", "3 rue jaune");
		Boolean b = DAOUtilisateur.createUtilisateur(u1);
		assertEquals(b,(Boolean)true);
	}
	
	@Test
	public void testUpdateUtilisateur() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Utilisateur u1 = DAOUtilisateur.getUtilisateurById("u1");
		u1.setNom("thierry");
		u1.setPrenom("henry");
		u1.setAdressePostale("4 bld vert");
		DAOUtilisateur.updateUtilisateur(u1);
		Utilisateur u2 = DAOUtilisateur.getUtilisateurById(u1.getCompte().getId());/*
		System.out.println("compte :" + u1.getCompte().equals(u2.getCompte())); 
		System.out.println("prenom :" + u1.getPrenom().equals(u2.getPrenom()));
		System.out.println("prenom u1:" + u1.getPrenom() +"; prenom u2:" + u2.getPrenom());
		System.out.println("nom :" + u1.getNom().equals(u2.getNom()));
		System.out.println("nom u1:" + u1.getNom() +"; nom u2:" + u2.getNom()); 
		System.out.println("bloqué :" + u1.isBloque().equals(u2.isBloque()));
		System.out.println("adresse postale :" + u1.getAdressePostale().equals(u2.getAdressePostale()));
		System.out.println("adresse postale u1:" + u1.getAdressePostale() +"; adresse postale u2:" + u2.getAdressePostale());*/
		assertTrue(u1.equals(u2));
	}
	
	@Test
	public void testGetUtilisateurById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c = new Compte(Compte.TYPE_UTILISATEUR, "e-mail3");
		Utilisateur u1 = new Utilisateur(c,"john", "michel", "3 rue jaune");
		DAOUtilisateur.createUtilisateur(u1);
		Utilisateur u2 = DAOUtilisateur.getUtilisateurById(u1.getCompte().getId());
		assertTrue(u1.equals(u2));
	}

	@Test
	public void testGetUtilisateurByAdresseEmail() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com").get(0);
		assertEquals("Mathieu", u.getPrenom());
	}
	
	@Test
	public void testGetUtilisateurByNom() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Utilisateur> l =  DAOUtilisateur.getUtilisateurByNom("Chedid");
		assertEquals("mathieuchedid@gmail.com", l.get(0).getCompte().getAdresseEmail());
	}
	
	@Test
	public void testGetUtilisateurByPrenom() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Utilisateur> l =  DAOUtilisateur.getUtilisateurByPrenom("Mathieu");
		assertEquals("mathieuchedid@gmail.com", l.get(0).getCompte().getAdresseEmail());
	}
}
