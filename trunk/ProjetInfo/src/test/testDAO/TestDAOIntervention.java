package test.testDAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
		TypeIntervention typ= new TypeIntervention("deraillement");
		Intervention i = new Intervention(v,UtilitaireDate.dateCourante(), typ);
		System.out.println(i.getDate());
		Boolean b= DAOIntervention.createIntervention(i);
		assertEquals((Boolean)true, (Boolean)b);
	}
	
	@Test
	public void testGetInterventionById() throws SQLException, ClassNotFoundException{
		Intervention i = DAOIntervention.getInterventionById("1");
		assertEquals(1, i.getTypeIntervention().getType());
		assertEquals("1", i.getVelo().getId());
	}
	
	@Test
	public void testGetNombresVelosParTypeIntervention() throws SQLException, ClassNotFoundException{
		List<Integer> liste = DAOIntervention.getNombresVelosParTypeIntervention(2);
		assertEquals((int)2,(int) liste.get(0));
		assertEquals((int)12,(int) liste.get(1));
		assertEquals((int)2,(int) liste.get(2));
		assertEquals((int)3,(int) liste.get(3));
		assertEquals((int)3,(int) liste.get(4));
		assertEquals((int)4,(int) liste.get(5));
	}
}