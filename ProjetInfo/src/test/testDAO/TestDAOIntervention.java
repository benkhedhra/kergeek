package test.testDAO;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOTypeIntervention;
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
		TypeIntervention typ= DAOTypeIntervention.getTypeInterventionById(3);
		Intervention i = new Intervention(v,UtilitaireDate.dateCourante(), typ);
		System.out.println(i.getDate());
		Boolean b= DAOIntervention.createIntervention(i);
		assertTrue(b);
	}
	
	@Test
	public void testGetInterventionById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Intervention i = DAOIntervention.getInterventionById("1");
		assertTrue(i instanceof Intervention);
		//TODO
	}
	
	@Test
	public void testGetNombresVelosParTypeIntervention() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<List<Integer>> liste = DAOIntervention.getNombresVelosParTypeIntervention(2);
		//TODO
		
		
	}
}
