package test.testMetier;

import java.sql.SQLException;

import metier.Station;
import metier.Velo;

import org.junit.Test;

import junit.framework.TestCase;

public class TestLieu extends TestCase {
	
	@Test
	public void testajouterVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo(s, false);
		Boolean b = s.ajouterVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testEnleverVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo(s, false);
		s.ajouterVelo(v);
		Boolean b = s.enleverVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	
}
