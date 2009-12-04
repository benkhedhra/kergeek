package test.testDAO;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;
import java.util.List;

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
		DAOVelo.createVelo(v);
		DemandeIntervention d = new DemandeIntervention(u,v);
		System.out.println("coucou");
		Boolean b = DAODemandeIntervention.createDemandeIntervention(d);
		System.out.println("coucou2");
		assertEquals((Boolean)true, b);
	}
	
	@Test
	public void testGetDemandesInterventionEnAttente() throws SQLException, ClassNotFoundException{
		List<DemandeIntervention> liste = DAODemandeIntervention.getDemandesInterventionEnAttente();
		for (int i=0;i < liste.size();i++) {
			assertEquals(null, liste.get(i).getIntervention().getId());
		}
	}
	
	@Test
	public void testGetDemandeInterventionById() throws SQLException, ClassNotFoundException{
		DemandeIntervention dmde = DAODemandeIntervention.getDemandeInterventionById("1");
		System.out.println(dmde.getUtilisateur());
		assertTrue(dmde.getUtilisateur().equals(DAOUtilisateur.getUtilisateurById("u1")));
		
	}
}
