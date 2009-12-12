package test.testDAO;


import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOLieu;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.DemandeAssignation;
import metier.Lieu;

import org.junit.Test;

public class TestDAODemandeAssignation extends TestCase{

	@Test
	public void testCreateDemandeAssignation() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Lieu l =DAOLieu.getLieuById("1");
		DemandeAssignation dmde = new DemandeAssignation(2, l);
		Boolean b = DAODemandeAssignation.createDemandeAssignation(dmde);
		assertEquals((Boolean)true,(Boolean) b);
	}
	

	@Test
	public void testGetDemandeAssignationById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		DemandeAssignation dmde = DAODemandeAssignation.getDemandeAssignationById("1");
		assertEquals("1", dmde.getLieu().getId());
		assertEquals(5, dmde.getNombreVelosVoulusDansLieu());
		assertEquals("2009-11-23", dmde.getDate().toString());
		assertEquals("1", dmde.getLieu().getId());
	}
	
	@Test
	public void testUpdateDemandeAssignation() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		
		DemandeAssignation dmde = DAODemandeAssignation.getDemandeAssignationById("5");
		dmde.setPriseEnCharge(true);
		Boolean b = DAODemandeAssignation.updateDemandeAssignation(dmde);
		assertEquals((Boolean) true, b);
		
	}
	
	@Test
	public void testGetAllDemandesAssignation() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<DemandeAssignation> liste = DAODemandeAssignation.getAllDemandesAssignation();
		assertEquals(5, liste.get(0).getNombreVelosVoulusDansLieu());
		assertEquals(4, liste.get(1).getNombreVelosVoulusDansLieu());
		assertEquals(10, liste.get(2).getNombreVelosVoulusDansLieu());
		assertEquals(4, liste.get(3).getNombreVelosVoulusDansLieu());
	}
	
	@Test
	public void testGetDemandesAssignationEnAttente() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<DemandeAssignation> liste =DAODemandeAssignation.getDemandesAssignationEnAttente();
		System.out.println(liste.size());
		for (int i = 0; i < liste.size(); i++) {
			assertEquals((Boolean)false, liste.get(i).isPriseEnCharge());
			System.out.println(liste.get(i).getNombreVelosVoulusDansLieu());
		}
		assertEquals(5, liste.get(0).getNombreVelosVoulusDansLieu());
		assertEquals(10, liste.get(1).getNombreVelosVoulusDansLieu());
		assertTrue(liste.get(0).getLieu().equals(DAOLieu.getLieuById("1")));
		assertTrue(liste.get(1).getLieu().equals(DAOLieu.getLieuById("3")));
	}
}
