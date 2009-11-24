package test.testMetier;

import java.sql.SQLException;
import java.util.Date;

import metier.Intervention;
import metier.TypesIntervention;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

import junit.framework.TestCase;

public class TestTypeIntervention extends TestCase{
	@Test
	public void testGetType() throws SQLException, ClassNotFoundException{
		Velo v = new Velo();
		Date d = UtilitaireDate.dateCourante();
		TypesIntervention t = new TypesIntervention();
		Intervention i = new Intervention(v, d, t);
		assertEquals(t, i.getTypeIntervention());
	}
	
	@Test
	public void testgetDescription(){
		
	}
}
