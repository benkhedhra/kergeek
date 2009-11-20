package test.testMetier;

import java.sql.SQLException;
import java.util.Date;

import junit.framework.TestCase;
import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.Velo;

import org.junit.Test;

public class TestEmprunt extends TestCase {
	@Test
	public void testProposerDemanderIntervention() throws SQLException, ClassNotFoundException{
		Velo v = new Velo();
		Utilisateur u = new Utilisateur();
		Station s = new Station();
		Date dateEmprunt = null;
		Emprunt e = new Emprunt(u, v, dateEmprunt, s);
		Boolean b = Emprunt.proposerDemanderIntervention(v, u);
		assertEquals((Boolean)true,(Boolean) b);
	}
}
