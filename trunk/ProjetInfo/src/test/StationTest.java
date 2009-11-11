package test;

import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Station;
import metier.Velo;

public class StationTest extends TestCase {
	public void TestcalculerTx() throws SQLException, ClassNotFoundException{
		Station s =new Station("id","adresse",50);
		Velo v = new Velo();
		s.ajouterVelo(v);
		int a=1/50;
		int b = s.calculerTx("id");
		assertEquals(a,b);
	}

	
}
