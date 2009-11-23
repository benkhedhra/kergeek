package test.testMetier;

import java.sql.Date;
import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;

import metier.Intervention;

import metier.Garage;


import metier.Station;
import metier.Technicien;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

public class TestTechnicien extends TestCase{
	@Test
	public void testEnregistrerVelo()throws SQLException, ClassNotFoundException{
		Compte c = new Compte(2);
		Technicien t = new Technicien(c);
		Velo v = t.enregistrerVelo();
		assertTrue(v instanceof Velo);
		assertTrue(v.getLieu() == Garage.getInstance());
	}
	
	@Test
	public void testIntervenir() throws SQLException, ClassNotFoundException {
		Compte c = new Compte(2);
		Technicien t = new Technicien(c);
		Velo v = new Velo();
		Station s = new Station();

		Intervention i = t.intervenir(v, s);
		java.util.Date d = UtilitaireDate.dateCourante();
		//ce test sur la date ne marche bien que la date soit la meme,cmt faire?
		assertEquals(d , i.getDate());
		assertTrue(t.intervenir(v, s) instanceof Intervention);
		assertEquals(v , i.getVelo());
		

	}
}
