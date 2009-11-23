package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;

import metier.Emprunt;

import metier.Lieu;

import metier.Station;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;
import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;

import org.junit.Test;

public class TestUtilisateur extends TestCase{
	
	@Test
	public void testEmprunteVelo() throws SQLException, ClassNotFoundException{
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
		Station s = new Station("id","adresse", 50);
		Velo v = new Velo("v",s,false);

		u.emprunteVelo(v, s);
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), s);
		assertEquals(null, v.getLieu());

		assertTrue(v == u.getVelo());
		assertFalse(v.getEmpruntEnCours() == null);
		assertFalse(v.getLieu() == s);
		assertTrue(v.getLieu() == Lieu.SORTI);

	}
	
	@Test
	public void testRendreVelo() throws SQLException, ClassNotFoundException, VeloNonSortiException, PasDeVeloEmprunteException{
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
		Station s = new Station("id","adresse", 50);
		Velo v = new Velo("v",s,false);
		u.emprunteVelo(v, s);
		u.rendreVelo(s);
		assertFalse(v == u.getVelo());
		assertFalse(v.getLieu() == Lieu.SORTI);
		assertTrue(v.getLieu() == s);
		assertTrue(v.getEmpruntEnCours() == null);
	}
}
