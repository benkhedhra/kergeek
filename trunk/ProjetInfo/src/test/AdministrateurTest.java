package test;

import gestionBaseDeDonnees.DAOCompte;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.Administrateur;
import metier.Compte;

public class AdministrateurTest extends TestCase{
	
	public void TestResilierCompte() throws SQLException, ClassNotFoundException{
		Administrateur a = new Administrateur();
		Compte c = new Compte("id","id",3);
		a.resilierCompte("id");
		Boolean b = DAOCompte.getCompteById("id").isActif();
		assertEquals((Boolean)b,0);
	}
	
	
}
