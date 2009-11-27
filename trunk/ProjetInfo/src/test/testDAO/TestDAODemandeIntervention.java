package test.testDAO;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;

import java.sql.SQLException;

import junit.framework.TestCase;
import metier.DemandeIntervention;
import metier.Lieu;
import metier.Utilisateur;
import metier.Velo;

import org.junit.Test;

public class TestDAODemandeIntervention extends TestCase{
	
	@Test
	public void testCreateDemandeIntervention() throws SQLException, ClassNotFoundException{
		Utilisateur u = DAOUtilisateur.getUtilisateurById("u1");
		Lieu l = DAOLieu.getLieuById("1");
		Velo v = new Velo(l, false);
		l.ajouterVelo(v);
		DemandeIntervention d = new DemandeIntervention(v, u);
		System.out.println("coucou");
		Boolean b = DAODemandeIntervention.createDemandeIntervention(d);
		System.out.println("coucou2");
		assertEquals((Boolean)true, b);
	}

}
