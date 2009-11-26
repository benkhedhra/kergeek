package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

public class TestEmprunt extends TestCase {
	@Test
	public void testcalculTempsEmprunt() throws SQLException, ClassNotFoundException{
		Velo v = new Velo();
		Utilisateur u = new Utilisateur();
		Station s = new Station();
		java.sql.Date d = UtilitaireDate.dateCourante();
		
		Emprunt e = new Emprunt(u, v, d, s,d,s); 
		
		e.calculTempsEmprunt();
		assertEquals(0,e.getDiff());
	}
	
	@Test
	public void testEqualsEmprunt(){
		
		Compte c1 = new Compte(Compte.TYPE_UTILISATEUR, "email");
		Utilisateur u1 = new Utilisateur(c1,"jean", "bon","adresse");
		Station s1 = new Station("2 rue verte", 10);
		Velo v1 = new Velo(s1);
		java.sql.Date d = UtilitaireDate.dateCourante();
		
		Compte c2 = new Compte(Compte.TYPE_UTILISATEUR, "email2");
		Utilisateur u2 = new Utilisateur(c2,"bernard", "lermite","adresse2");
		Station s2 = new Station("3 rue bleue", 10);
		java.sql.Date d2 = UtilitaireDate.retrancheJour(d, 1);
		
		Velo v2 = new Velo(s2);
		
		Emprunt e1 = new Emprunt(u1, v1, d2, s1, d, s2);
		Emprunt e2 = new Emprunt(u1, v1, d2, s1, d, s2);
		Emprunt e3 = new Emprunt(u2, v1, d2, s1, d, s2);
		Emprunt e4 = new Emprunt(u1, v2, d2, s1, d, s2);
		Emprunt e5 = new Emprunt(u1, v1, d, s1, d, s2);
		Emprunt e6 = new Emprunt(u1, v1, d2, s2, d, s2);
		Emprunt e7 = new Emprunt(u1, v1, d2, s1, d2, s2);
		Emprunt e8 = new Emprunt(u1, v1, d2, s1, d, s1);
		
		assertTrue(e1.equals(e1));
		assertTrue(e1.equals(e2));
		assertFalse(e1.equals(e3));
		assertFalse(e1.equals(e4));
		assertFalse(e1.equals(e5));
		assertFalse(e1.equals(e6));
		assertFalse(e1.equals(e7));
		assertFalse(e1.equals(e8));
	}
}
