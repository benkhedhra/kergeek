package test.testDAO;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOLieu;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.DemandeAssignation;
import metier.Lieu;
import metier.UtilitaireDate;

import org.junit.Test;

public class TestDAODemandeAssignation extends TestCase{
	@Test
	public void testDemandeAssignation() throws SQLException, ClassNotFoundException{
		Lieu l =DAOLieu.getLieuById("1");
		java.sql.Date d = UtilitaireDate.dateCourante();
		DemandeAssignation dmde = new DemandeAssignation(d, 2, l);
		Boolean b = DAODemandeAssignation.createDemandeAssignation(dmde);
		assertEquals((Boolean)true,(Boolean) b);
	}
	

	@Test
	public void testGetDemandeAssignationById() throws SQLException, ClassNotFoundException{
		DemandeAssignation dmde = DAODemandeAssignation.getDemandeAssignationById("1");
		assertEquals("1", dmde.getLieu().getId());
		assertEquals(5, dmde.getNombreVelosVoulusDansStation());
		assertEquals("2009-11-23", dmde.getDate().toString());
		assertEquals("1Gare du Campus", dmde.getLieu().toString());
	}
	
	@Test
	public void testUpdateDemandeAssignation() throws SQLException, ClassNotFoundException{
		DemandeAssignation dmde = DAODemandeAssignation.getDemandeAssignationById("1");
		dmde.setNombreVelosVoulusDansStation(7);
		Boolean b = DAODemandeAssignation.updateDemandeAssignation(dmde);
		assertEquals((Boolean) true, b);
		DemandeAssignation dmde2 = DAODemandeAssignation.getDemandeAssignationById("1");
		assertEquals(7, dmde2.getNombreVelosVoulusDansStation());
		dmde2.setNombreVelosVoulusDansStation(5);
		DAODemandeAssignation.updateDemandeAssignation(dmde2);
	}
	
	@Test
	public void testGetAllDemandesAssignation() throws SQLException, ClassNotFoundException{
		List<DemandeAssignation> liste = DAODemandeAssignation.getAllDemandesAssignation();
		assertEquals(5, liste.get(0).getNombreVelosVoulusDansStation());
		assertEquals(4, liste.get(1).getNombreVelosVoulusDansStation());
		assertEquals(10, liste.get(2).getNombreVelosVoulusDansStation());
		assertEquals(4, liste.get(3).getNombreVelosVoulusDansStation());
	}
}
