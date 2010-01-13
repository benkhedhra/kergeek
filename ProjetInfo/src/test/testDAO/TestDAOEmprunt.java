package test.testDAO;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;
import metier.UtilitaireDate;
import metier.Velo;

import org.junit.Test;

public class TestDAOEmprunt extends TestCase{
	

	@Test
	public void testCreateEmprunt() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com").get(0);
		Velo v = DAOVelo.getVeloById("1");
		Station s = (Station) DAOLieu.getLieuById("1");
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), s,UtilitaireDate.dateCourante(), s);
		Boolean b = DAOEmprunt.createEmprunt(e);
		assertEquals((Boolean)true,(Boolean) b);
	}

	@Test
	public void testUpdateEmprunt() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com").get(0);
		Velo v = DAOVelo.getVeloById("1");
		Station s = (Station) DAOLieu.getLieuById("1");
		Utilisateur u2 = DAOUtilisateur.getUtilisateurByAdresseEmail("francoiscoquet@gmail.com").get(0);
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), s,UtilitaireDate.dateCourante(), s);
		DAOEmprunt.createEmprunt(e);
		e.setUtilisateur(u2);
		Boolean b = DAOEmprunt.updateEmprunt(e);
		assertEquals((Boolean)true, (Boolean)b);
	}
	
	@Test
	public void testGetEmpruntById() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Emprunt e = DAOEmprunt.getEmpruntById("1");
		assertEquals("2", e.getStationEmprunt().getId());
		assertEquals("1", e.getStationRetour().getId());
		assertEquals("id3033@ensai.fr", e.getUtilisateur().getCompte().getAdresseEmail());
	}
	
	@Test
	public void testGetNombreEmpruntParUtilisateurParMois() throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		Utilisateur u = DAOUtilisateur.getUtilisateurByAdresseEmail("mathieuchedid@gmail.com").get(0);
		List <Integer> liste = DAOEmprunt.getNombreEmpruntParUtilisateurParMois(u, 3);
		assertEquals((int)1, (int)liste.get(1));
		assertEquals((int)2, (int)liste.get(2));
		assertEquals((int)2, (int)liste.get(3));
	}
}
