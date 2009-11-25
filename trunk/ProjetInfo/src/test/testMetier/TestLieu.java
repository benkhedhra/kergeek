package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Station;
import metier.Velo;

import org.junit.Test;

public class TestLieu extends TestCase {
	
	@Test
	public void testAjouterVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo(s, false);
		s.ajouterVelo(v);
		assertEquals(s, v.getLieu());

	}
	
	@Test
	public void testEnleverVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo(s, false);
		s.ajouterVelo(v);

		s.enleverVelo(v);
		assertEquals(null,v.getLieu());

	}
	
	
}
