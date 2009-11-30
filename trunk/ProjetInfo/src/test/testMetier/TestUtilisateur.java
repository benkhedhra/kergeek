package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Emprunt;
import metier.Lieu;
import metier.Station;
import metier.Utilisateur;
import metier.Velo;

import org.junit.Test;

import exception.PasDeVeloEmprunteException;

public class TestUtilisateur extends TestCase{
	
	@Test
	public void testEmprunteVelo() throws SQLException, ClassNotFoundException{
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
		Station s = new Station("id","adresse", 50);
		Velo v = new Velo(s,false);
		
		u.emprunteVelo(v, s);
		
		assertEquals(null, v.getLieu());
		assertFalse(null == u.getEmpruntEnCours());
		assertTrue(v == u.getEmpruntEnCours().getVelo());
		assertFalse(v.getEmpruntEnCours() == null);
		assertFalse(v.getLieu() == s);
		assertTrue(v.getLieu() == Lieu.SORTI);

	}
	
	@Test
	public void testRendreVelo() throws SQLException, ClassNotFoundException, PasDeVeloEmprunteException{
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
		Station s = new Station("id","adresse", 50);
		Velo v = new Velo(s,false);
		u.emprunteVelo(v, s);
		Emprunt e = u.rendreVelo(s);
		assertFalse(e == u.getEmpruntEnCours());
		assertTrue(null == u.getEmpruntEnCours());
		assertFalse(v.getLieu() == Lieu.SORTI);
		assertTrue(v.getLieu() == s);
		assertTrue(v.getEmpruntEnCours() == null);
		assertTrue(e != null);
	}
	
	
	@Test
	public void testEqualsUtil(){
		Compte c1 = new Compte(Compte.TYPE_UTILISATEUR, "email");
		Utilisateur u1 = new Utilisateur(c1);
		Compte c2 = new Compte(Compte.TYPE_UTILISATEUR, "email");
		Utilisateur u2 = new Utilisateur(c2);
		Utilisateur u3 = new Utilisateur(c2);
		assertFalse(u1.equals(u2));
		assertTrue(u2.equals(u3));
		assertFalse(u1.equals(u3));
		u2.getCompte().setMotDePasse(c1.getMotDePasse());
		assertTrue(u1.equals(u2));
		u2.getCompte().setAdresseEmail("email2");
		assertFalse(u1.equals(u2));
		u2.getCompte().setActif(false);
		assertFalse(u1.equals(u2));
	}
}