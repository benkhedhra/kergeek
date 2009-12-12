package test.testDAO;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOLieu;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.Lieu;
import metier.Station;

import org.junit.Test;

public class TestDAOLieu extends TestCase{
	@Test
	public void testGetLieuById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Lieu l1 = DAOLieu.getLieuById("1");
		assertEquals("Gare du Campus", l1.getAdresse());
		assertEquals(15, l1.getCapacite());
		
		Lieu l2 = DAOLieu.getLieuById("2");
		assertEquals("Forum du Campus", l2.getAdresse());
		assertEquals(10, l2.getCapacite());
	}
	
	@Test
	public void testCreateLieu() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Station s = new Station("R.U", 50);
		Boolean b = DAOLieu.createLieu(s);
		assertEquals((Boolean)true,(Boolean) b) ;
	}
	
	@Test
	public void testGetAllStations() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Station> liste = DAOLieu.getAllStations();
		System.out.println(liste.get(0));
		System.out.println(liste.get(1));
		System.out.println(liste.get(2));
		System.out.println(liste.get(3));
		System.out.println(liste.get(4));
		assertEquals("pool de velo", liste.get(0).getAdresse());
		assertEquals("Gare du Campus", liste.get(1).getAdresse());
		assertEquals(10, liste.get(2).getCapacite());
	}
	
	@Test
	public void testGetStationsSurSous() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		//TODO
	}
	
	@Test
	public void testCalculerTaux()throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		//TODO
	}
}
