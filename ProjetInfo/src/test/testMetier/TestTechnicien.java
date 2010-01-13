package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Garage;
import metier.Intervention;
import metier.Technicien;
import metier.Velo;

import org.junit.Test;

public class TestTechnicien extends TestCase{
	@Test
	public void testEnregistrerVelo()throws SQLException, ClassNotFoundException{
		Compte c = new Compte(2,"ttest@gmail.com");
		Technicien t = new Technicien(c);
		Velo v = t.enregistrerVelo();
		assertTrue(v instanceof Velo);
		assertTrue(v.getLieu() == Garage.getInstance());
	}
	
	@Test
	public void testIntervenir() throws SQLException, ClassNotFoundException {
		Compte c = new Compte(2,"ttest@gmail.com");
		Technicien t = new Technicien(c);
		Velo v = new Velo();
		Intervention i = t.intervenir(v);
		assertTrue(i instanceof Intervention);
		assertEquals(v , i.getVelo());
		}
	
	@Test
	public void testEqualsTechnicien(){
		Compte c1 = new Compte(Compte.TYPE_TECHNICIEN, "email");
		Technicien t1 = new Technicien(c1);
		Compte c2 = new Compte(Compte.TYPE_TECHNICIEN, "email");
		Technicien t2 = new Technicien(c2);
		Technicien t3 = new Technicien(c2);
		
		assertTrue(t1.equals(t1));
		assertTrue(t2.equals(t3));
		assertFalse(t1.equals(t2));
		assertFalse(t1.equals(t3));
		t2.getCompte().setMotDePasse(c1.getMotDePasse());
		assertTrue(t1.equals(t2));
	}

	
}
