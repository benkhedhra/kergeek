package test.testDAO;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.Intervention;
import metier.TypeIntervention;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

public class TestDAOIntervention extends TestCase{
	@Test
	public void testCreateIntervention() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Velo v = DAOVelo.getVeloById("1");
		TypeIntervention typ= new TypeIntervention("deraillement");
		Intervention i = new Intervention(v,UtilitaireDate.dateCourante(), typ);
		System.out.println(i.getDate());
		Boolean b= DAOIntervention.createIntervention(i);
		assertEquals((Boolean)true, (Boolean)b);
	}
	
	@Test
	public void testGetInterventionById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Intervention i = DAOIntervention.getInterventionById("1");
		assertEquals(1, i.getTypeIntervention().getType());
		assertEquals("1", i.getVelo().getId());
	}
	
	@Test
	public void testGetNombresVelosParTypeIntervention() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<Integer> liste = DAOIntervention.getNombresVelosParTypeIntervention(2);
		assertEquals((int)2,(int) liste.get(0));
		assertEquals((int)12,(int) liste.get(1));
		assertEquals((int)2,(int) liste.get(2));
		assertEquals((int)3,(int) liste.get(3));
		assertEquals((int)3,(int) liste.get(4));
		assertEquals((int)4,(int) liste.get(5));
	}
}
