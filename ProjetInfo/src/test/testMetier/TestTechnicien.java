package test.testMetier;

import java.sql.SQLException;

import metier.Compte;
import metier.Station;
import metier.Technicien;
import metier.Velo;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import junit.framework.TestCase;

public class TestTechnicien extends TestCase{
	@Test
	public void testEnregistrerVelo()throws SQLException, ClassNotFoundException{
		Compte c = new Compte(2);
		Technicien t = new Technicien(c);
		assertTrue(t.enregistrerVelo() instanceof Velo);
	}
	
	@Test
	public void testIntervenir() throws SQLException, ClassNotFoundException {
		Compte c = new Compte(2);
		Technicien t = new Technicien(c);
		Velo v = new Velo();
		Station s = new Station();
		Boolean b = t.intervenir(v, s);
		assertEquals((Boolean)true,(Boolean) b);
	}
}
