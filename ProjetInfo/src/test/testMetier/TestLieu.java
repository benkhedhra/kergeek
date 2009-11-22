package test.testMetier;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;
import java.util.Date;

import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.Velo;

import org.junit.Test;

import junit.framework.TestCase;

public class TestLieu extends TestCase {
	
	@Test
	public void testajouterVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo("v", s, false);
		DAOVelo.createVelo(v);
		Boolean b = s.ajouterVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testEnleverVelo() throws SQLException, ClassNotFoundException{
		Station s = new Station("id", "adresse", 50);
		Velo v = new Velo("v", s, false);
		DAOVelo.createVelo(v);
		s.ajouterVelo(v);
		Boolean b = s.enleverVelo(v);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	
}
