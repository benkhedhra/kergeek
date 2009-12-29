package test.testMetier;

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
		Station s = new Station( "adresse", 50);
		s.setId("id");
		Velo v = new Velo(s);
		s.ajouterVelo(v);
		assertEquals(s, v.getLieu());

	}
	
	@Test
	public void testEnleverVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("adresse", 50);
		s.setId("id");
		Velo v = new Velo(s);
		s.ajouterVelo(v);

		s.enleverVelo(v);
		assertEquals(Lieu.ID_SORTIE,v.getLieu().getId());
	}
	
	@Test
	public void testEqualsLieu(){
		Station s1 = new Station("2 rue verte", 10);
		Station s2 = new Station("3 rue bleu", 10);
		Station s3 = new Station("3 rue bleu", 11);
		Station s4 = new Station(Garage.ADRESSE_GARAGE, Garage.CAPACITE_GARAGE);
		
		assertTrue(s1.equals(s1));
		assertFalse(s1.equals(s2));
		assertFalse(s1.equals(s3));
		assertFalse(s4.equals(Garage.getInstance()));
		s4.setId(Garage.ID_GARAGE);
		assertFalse(s4.equals(Garage.getInstance()));
	}
	
	
}
