package test.testMetier;

import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Garage;
import metier.Lieu;
import metier.Station;
import metier.Velo;

import org.junit.Test;

public class TestLieu extends TestCase {
	
	@Test
	public void testAjouterVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo("v", Garage.getInstance(), false);
		s.ajouterVelo(v);
		assertTrue(v.getLieu() == s);
	}
	
	@Test
	public void testEnleverVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo("v", s, false);
		s.ajouterVelo(v);
		s.enleverVelo(v);
		assertFalse(v.getLieu() == s);
		assertTrue(v.getLieu() == Lieu.SORTI);
	}
	
	
}
