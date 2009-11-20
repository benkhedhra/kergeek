package test.testDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import gestionBaseDeDonnees.DAOVelo;
import metier.Garage;
import metier.Lieu;
import metier.Station;
import metier.Velo;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDAOVelo extends TestCase {
	

	@Test
	public void testCreateVelo() throws SQLException, ClassNotFoundException{
		Velo v = new Velo();
		Boolean b = DAOVelo.createVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testUpdateVelo(){
	}
	
	@Test
	public void testDeleteVelo() throws SQLException, ClassNotFoundException{
		Velo v = new Velo();
		DAOVelo.createVelo(v);
		Boolean b = DAOVelo.deleteVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testGetVeloById() throws SQLException, ClassNotFoundException{
		Station s = new Station();
		Velo v1 = new Velo();
		DAOVelo.createVelo(v1);
		Velo v2 = DAOVelo.getVeloById(v1.getId());
		assertEquals(v1.getId(), v2.getId());
		assertEquals(v1.getLieu(), v2.getLieu());
	}
	
	@Test
	public void testGetVeloByLieu() throws SQLException, ClassNotFoundException{
		Station s =new Station("id", "adresse", 50);
		Velo v1 = new Velo();
		v1.setLieu(s);
		ArrayList<Velo> l =new ArrayList<Velo>();
		l.add(v1);
		assertEquals(l, DAOVelo.getVeloByLieu(s));
	}
}
