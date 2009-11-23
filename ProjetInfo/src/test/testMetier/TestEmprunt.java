package test.testMetier;

import java.sql.Date;
import java.sql.SQLException;

import junit.framework.TestCase;

import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

public class TestEmprunt extends TestCase {
	@Test
	public void testcalculTempsEmprunt() throws SQLException, ClassNotFoundException{
		Velo v = new Velo();
		Utilisateur u = new Utilisateur();
		Station s = new Station();
		java.util.Date d = UtilitaireDate.dateCourante();
		Emprunt e = new Emprunt(u, v, d, s,d,s); 
		e.calculTempsEmprunt();
		assertEquals(0,e.getDiff());

	}
}
