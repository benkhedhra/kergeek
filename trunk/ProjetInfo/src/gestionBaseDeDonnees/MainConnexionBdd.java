package gestionBaseDeDonnees;


import java.sql.SQLException;

import metier.Velo;

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
		/*Compte c1 = new Compte();
		Administrateur a = new Administrateur(c1);
		Compte c2 = a.creerCompte(1,"mail2");
		Compte c3 = DAOCompte.getCompteByAdresseEmail("mail2");
		System.out.println("Id c2 :" + c2.getId() + " alors que Id c3:" + c3.getId());
		 */

		/*Velo velo =new Velo();
		System.out.println("creation du velo : " +DAOVelo.createVelo(velo));
		System.out.println("id depart : " + velo.getId());
		System.out.println("velo : " + DAOVelo.getVeloById(velo.getId()));
		velo.setEnPanne(false);
		System.out.println("enPanne mis a : " + velo.isEnPanne());
		System.out.println("velo mis a jour : " + DAOVelo.updateVelo(velo));
		velo = DAOVelo.getVeloById(velo.getId());
		System.out.println("id du velo obtenu apres la mis a jour : " + velo.getId());
		System.out.println("enPanne obtenue apres la mis a jour : " + velo.isEnPanne());
		System.out.println("nouveau velo : " + DAOVelo.getVeloById(velo.getId()));*/
		
		
		/*
		//System.out.println(DAOTypesIntervention.getAllTypesIntervention());
		System.out.println(DAOTypesIntervention.getAllTypesIntervention().keySet());
		for (String value : DAOTypesIntervention.getAllTypesIntervention().values()){
			System.out.println(value);
		}
		//System.out.println(DAOTypesIntervention.getAllTypesIntervention().values());
		System.out.println(DAOIntervention.getNombresVelosParTypeIntervention(2));
		*/
		
		System.out.println(DAOEmprunt.NombreVelosRentres(DAOLieu.getAllStations().get(1), 80));
		
		
		gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		System.out.println("Ferme");

	}  

}
