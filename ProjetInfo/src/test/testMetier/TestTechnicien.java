package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Garage;
import metier.Intervention;
import metier.Station;
import metier.Technicien;
import metier.Velo;

import org.junit.Test;

public class TestTechnicien extends TestCase{
	@Test
	public void testEnregistrerVelo()throws SQLException, ClassNotFoundException{
		Compte c = new Compte(2);
		Technicien t = new Technicien(c);
		Velo v = t.enregistrerVelo();
		assertTrue(v instanceof Velo);
		assertTrue(v.getLieu() == Garage.getInstance());
	}
	
	@Test
	public void testIntervenir() throws SQLException, ClassNotFoundException {
		Compte c = new Compte(2);
		Technicien t = new Technicien(c);
		Velo v = new Velo();
		Station s = new Station();
		Intervention i = t.intervenir(v, s);
		/*TODO
		 * assert???
		 */
	}
}
