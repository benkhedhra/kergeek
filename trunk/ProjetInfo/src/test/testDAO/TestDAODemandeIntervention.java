package test.testDAO;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

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
	public void testCreateDemandeIntervention() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Utilisateur u = DAOUtilisateur.getUtilisateurById("u1");
		Lieu l = DAOLieu.getLieuById("1");
		Velo v = new Velo(l);
		l.ajouterVelo(v);
		DAOVelo.createVelo(v);
		DemandeIntervention d = new DemandeIntervention(u,v);
		Boolean b = DAODemandeIntervention.createDemandeIntervention(d);
		assertTrue(b);
	}
	
	@Test
	public void testGetDemandesInterventionEnAttente() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		List<DemandeIntervention> liste = DAODemandeIntervention.getDemandesInterventionEnAttente();
		System.out.println(liste);
		for (int i=0;i < liste.size();i++) {
			assertEquals(null, liste.get(i).getIntervention());
		}
	}
	
	@Test
	public void testGetDemandeInterventionById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		DemandeIntervention dmde = DAODemandeIntervention.getDemandeInterventionById("1");
		System.out.println(DAOUtilisateur.getUtilisateurById("u1"));
		System.out.println(dmde.getUtilisateur());
		Utilisateur u = DAOUtilisateur.getUtilisateurById("u1");
		assertTrue(dmde.getUtilisateur().equals(u));
	}
}
