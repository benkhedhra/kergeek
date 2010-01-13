package test.testDAO;

import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.Lieu;
import metier.Velo;

import org.junit.Test;

public class TestDAOVelo extends TestCase {
	

	@Test
	public void testCreateVelo() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Lieu l = DAOLieu.getLieuById("1");
		Velo v = new Velo(l);
		v.setEnPanne(true);
		
		Boolean b = DAOVelo.createVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testUpdateVelo() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Velo v = DAOVelo.getVeloById("1");
		
		Boolean b =DAOVelo.updateVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	
	@Test
	public void testGetVeloById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Velo v = DAOVelo.getVeloById("1");
		Lieu l = DAOLieu.getLieuById("1");
		assertEquals(l.getAdresse(), v.getLieu().getAdresse());
		assertEquals(false, v.isEnPanne());
	}
	
	@Test
	public void testGetVeloByLieu() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Lieu l = DAOLieu.getLieuById("2");
		List<Velo> liste = DAOVelo.getVelosByLieu(l);
		
		assertEquals(0,liste.size());
		
	}
}
