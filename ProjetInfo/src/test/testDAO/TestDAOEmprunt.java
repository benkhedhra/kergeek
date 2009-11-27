package test.testDAO;

import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;

import java.sql.SQLException;

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
		Emprunt e = new Emprunt(u, v, UtilitaireDate.dateCourante(), l);
		Boolean b = DAOEmprunt.createEmprunt(e);
		assertEquals((Boolean)true,(Boolean) b);
	}

	@Test
	public void testUpdateEmprunt() throws SQLException, ClassNotFoundException{
		
	}
}
