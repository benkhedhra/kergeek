package test.testDAO;

import java.sql.SQLException;
import java.util.Date;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import metier.Intervention;
import metier.TypeIntervention;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDAOIntervention extends TestCase{
	@Test
	public void testCreateIntervention() throws SQLException, ClassNotFoundException{
		Velo v = DAOVelo.getVeloById("1");
		Date d =UtilitaireDate.dateCourante();
		TypeIntervention typ= new TypeIntervention("deraillement");
		Intervention i = new Intervention(v, (java.sql.Date)d, typ);
		Boolean b= DAOIntervention.createIntervention(i);
		assertEquals((Boolean)true, (Boolean)b);
	}
}
