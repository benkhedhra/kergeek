package test.testMetier;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Compte;
import metier.Station;
import metier.Utilisateur;
import metier.Velo;
import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;
import gestionBaseDeDonnees.DAOVelo;
import org.junit.Test;

public class TestUtilisateur extends TestCase{
	
	@Test
	public void testEmprunteVelo() throws SQLException, ClassNotFoundException{
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
		Station s = new Station("id","adresse", 50);
		Velo v = new Velo("v",s,false);
		DAOVelo.createVelo(v);
		Boolean b = u.emprunteVelo(v, s);
		assertEquals((Boolean)true,(Boolean) b);
	}
	
	@Test
	public void testRendreVelo() throws SQLException, ClassNotFoundException, VeloNonSortiException, PasDeVeloEmprunteException{
		Compte c =new Compte();
		Utilisateur u = new Utilisateur(c);
		Station s = new Station("id","adresse", 50);
		Velo v = new Velo("v",s,false);
		DAOVelo.createVelo(v);
		u.emprunteVelo(v, s);
		Boolean b = u.rendreVelo(s);
		assertEquals((Boolean)true,(Boolean) b);
	}
}
