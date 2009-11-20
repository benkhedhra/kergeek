package test.testMetier;

import java.sql.SQLException;

import org.junit.Test;

import junit.framework.TestCase;
import metier.Station;
import metier.Velo;

public class TestStation extends TestCase {
	
	@Test
	public void testCalculerTx() throws SQLException, ClassNotFoundException{
		Station s =new Station("id","adresse",50);
		Velo v = new Velo();
		s.ajouterVelo(v);
		int a=1/50;
		int b = s.calculerTx("id");
		assertEquals(a,b);
	}


}
