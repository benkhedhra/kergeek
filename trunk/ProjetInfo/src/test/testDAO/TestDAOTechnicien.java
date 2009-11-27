package test.testDAO;

import gestionBaseDeDonnees.DAOTechnicien;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Technicien;

import org.junit.Test;

public class TestDAOTechnicien extends TestCase{
	@Test
	public void testCreateTechnicien() throws SQLException, ClassNotFoundException{
		Compte c = new Compte(Compte.TYPE_TECHNICIEN, "mail", true);
		Technicien t = new Technicien(c);
		Boolean b= DAOTechnicien.createTechnicien(t);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testUpdateTechnicien() throws SQLException, ClassNotFoundException{
		Technicien t = DAOTechnicien.getTechnicienByAdresseEmail("mail");
		t.getCompte().setAdresseEmail("email");
		Boolean b= DAOTechnicien.updateTechnicien(t);
		assertEquals((Boolean)true,(Boolean)b ); 
		assertEquals("email", t.getCompte().getAdresseEmail());
		
	}
	
	@Test
	public void testGetTechnicienById() throws SQLException, ClassNotFoundException{
		Technicien t = DAOTechnicien.getTechnicienById("t1");
		assertEquals("Repartout", t.getCompte().getMotDePasse());
		assertEquals("didierrepartout@gmail.com", t.getCompte().getAdresseEmail());
	}

	@Test
	public void testGetTechnicienByAdresseEmail() throws SQLException, ClassNotFoundException{
		Technicien t = DAOTechnicien.getTechnicienByAdresseEmail("didierrepartout@gmail.com");
		assertEquals("Repartout", t.getCompte().getMotDePasse());
	}
}
