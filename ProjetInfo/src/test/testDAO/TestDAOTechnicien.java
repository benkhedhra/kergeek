package test.testDAO;

import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Technicien;

import org.junit.Test;

public class TestDAOTechnicien extends TestCase{
	@Test
	public void testCreateTechnicien() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Compte c = new Compte(Compte.TYPE_TECHNICIEN, "mail");
		Technicien t = new Technicien(c);
		Boolean b= DAOTechnicien.createTechnicien(t);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testUpdateTechnicien() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Technicien t = DAOTechnicien.getTechnicienByAdresseEmail("mail");
		t.getCompte().setAdresseEmail("email");
		Boolean b= DAOTechnicien.updateTechnicien(t);
		assertEquals((Boolean)true,(Boolean)b ); 
		assertEquals("email", t.getCompte().getAdresseEmail());
		
	}
	
	@Test
	public void testGetTechnicienById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Technicien t = DAOTechnicien.getTechnicienById("t1");
		assertEquals("reparetout", t.getCompte().getMotDePasse());
		assertEquals("id2927@ensai.fr", t.getCompte().getAdresseEmail());
	}

	@Test
	public void testGetTechnicienByAdresseEmail() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Technicien t = DAOTechnicien.getTechnicienByAdresseEmail("id2927@ensai.fr");
		assertEquals("reparetout", t.getCompte().getMotDePasse());
	}
}
