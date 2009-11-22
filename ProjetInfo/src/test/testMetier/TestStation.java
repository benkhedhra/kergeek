package test.testMetier;

import gestionBaseDeDonnees.DAOLieu;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Station;
import metier.Velo;

import org.junit.Test;

public class TestStation extends TestCase {
	
	@Test
	public void testCalculerTx() throws SQLException, ClassNotFoundException{
		Station s =new Station("id","adresse",50);
		Velo v = new Velo();
		s.ajouterVelo(v);
		int a=1/50;
		int b = DAOLieu.calculerTx(s);
		assertEquals(a,b);
	}


}
