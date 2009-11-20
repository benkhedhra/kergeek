package test.testDAO;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.Date;
import java.sql.SQLException;

import metier.Compte;
import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.Velo;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDAOEmprunt extends TestCase{
	@Test
	public void testCreateEmprunt() throws SQLException, ClassNotFoundException{
		Station s = new Station();
		Compte c = new Compte();
		Utilisateur u = new Utilisateur(c);
		Velo v = new Velo();
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), s);
		assertEquals(true, DAOEmprunt.createEmprunt(e));
	}
	
	@Test
	public void testUpdateEmprunt(){
		
	}
}
