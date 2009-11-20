package gestionBaseDeDonnees;


import java.sql.SQLException;

import metier.Administrateur;
import metier.Compte;

public class MainConnexionBdd {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		//System.out.println("Date courante : " + UtilitaireDate.dateCourante().toString());

		gestionBaseDeDonnees.ConnexionOracleViaJdbc.ouvrir();
		System.out.println("Ouvert");

		/*System.out.println("id = "+Garage.getInstance().getId());
		 System.out.println("adresse = "+Garage.getInstance().getAdresse());
		 System.out.println("capacité = "+Garage.getInstance().getCapacite());
		 System.out.println(DAOLieu.createLieu(Garage.getInstance()));
		 */
		/*
		 Compte c =new Compte(2);
		 System.out.println(c.getType());
		 System.out.println(DAOCompte.createCompte(c));
		 System.out.println(c.getId());
		 System.out.println(DAOCompte.getCompteById(c.getId()));
		 c.setMotDePasse("trucBidule");
		 System.out.println(c.getMotDePasse());
		 System.out.println(DAOCompte.updateCompte(c));
		 System.out.println(c.getId());
		 String id = c.getId();
		 System.out.println(DAOCompte.getCompteByAdresseEmail(c.getAdresseEmail()));
		 System.out.println(c.getId());
		 System.out.println(id.compareTo(c.getId()));
		 */
		Compte c1 = new Compte();
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(1,"mail2");
		Compte c3 = DAOCompte.getCompteByAdresseEmail("mail2");
		System.out.println("Id c2 :" + c2.getId() + " alors que Id c3:" + c3.getId());

		/*Velo velo =new Velo();
		 System.out.println(DAOVelo.createVelo(velo));
		 System.out.println(velo.getId());
		 System.out.println(DAOVelo.getVeloById(velo.getId()));
		 */
		gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		System.out.println("Ferme");

	}  

}
