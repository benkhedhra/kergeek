package test.testDAO;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.Emprunt;
import metier.Lieu;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

public class TestDAOEmprunt extends TestCase{
	

	@Test
	public void testCreateEmprunt() throws SQLException, ClassNotFoundException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com");
		Velo v = DAOVelo.getVeloById("1");
		Lieu l = DAOLieu.getLieuById("1");
		
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), l,UtilitaireDate.dateCourante(), l);
		Boolean b = DAOEmprunt.createEmprunt(e);
		assertEquals((Boolean)true,(Boolean) b);
	}

	@Test
	public void testUpdateEmprunt() throws SQLException, ClassNotFoundException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com");
		Velo v = DAOVelo.getVeloById("1");
		Lieu l = DAOLieu.getLieuById("1");
		Utilisateur u2 = DAOUtilisateur.getUtilisateurByAdresseEmail("francoiscoquet@gmail.com");
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), l,UtilitaireDate.dateCourante(), l);
		DAOEmprunt.createEmprunt(e);
		e.setUtilisateur(u2);
		Boolean b = DAOEmprunt.updateEmprunt(e);
	}
	
	@Test
	public void testGetEmpruntById() throws SQLException, ClassNotFoundException{
		Emprunt e = DAOEmprunt.getEmpruntById("1");
		assertEquals("2", e.getLieuEmprunt().getId());
		assertEquals("1", e.getLieuRetour().getId());
		Utilisateur u = DAOUtilisateur.getUtilisateurById(e.getUtilisateur().getCompte().getId());
		assertTrue(u.equals(e.getUtilisateur()));
		assertEquals("1", e.getVelo().getId().toString());
	}
	
	@Test
	public void testGetNombreEmpruntParUtilisateurParMois() throws SQLException, ClassNotFoundException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com");
		List <Integer> liste = DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 3);
		assertEquals((int)0, (int)liste.get(0));
		assertEquals((int)2, (int)liste.get(1));
		assertEquals((int)1, (int)liste.get(2));
	}
}
