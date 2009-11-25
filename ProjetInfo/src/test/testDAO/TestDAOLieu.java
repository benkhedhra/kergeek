package test.testDAO;

import java.sql.SQLException;
import java.util.List;

import gestionBaseDeDonnees.DAOLieu;

import metier.Lieu;
import metier.Station;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDAOLieu extends TestCase{
	@Test
	public void testGetLieuById() throws SQLException, ClassNotFoundException{
		Lieu l1 = DAOLieu.getLieuById("1");
		assertEquals("Gare du Campus", l1.getAdresse());
		assertEquals(15, l1.getCapacite());
		
		Lieu l2 = DAOLieu.getLieuById("2");
		assertEquals("Forum du Campus", l2.getAdresse());
		assertEquals(10, l2.getCapacite());
	}
	
	@Test
	public void testCreateLieu() throws SQLException, ClassNotFoundException{
		Station s = new Station("ENSAI", 50);
		Boolean b = DAOLieu.createLieu(s);
		System.out.println(s.getId());
		System.out.println(s.getAdresse());
		assertEquals((Boolean)true,(Boolean) b) ;
	}
	
	@Test
	public void testGetAllStations() throws SQLException, ClassNotFoundException{
		List<Station> liste = DAOLieu.getAllStations();
		assertEquals("pool de velo", liste.get(0).getAdresse());
		assertEquals("Gare du Campus", liste.get(1).getAdresse());
		assertEquals(10, liste.get(2).getCapacite());
	}
}
