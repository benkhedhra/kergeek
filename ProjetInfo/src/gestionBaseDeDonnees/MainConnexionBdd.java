package gestionBaseDeDonnees;


import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;

import metier.exceptionsMetier.PasDeDateRetourException;

public class MainConnexionBdd {

	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, ConnexionFermeeException {

		//System.out.println("Date courante : " + UtilitaireDate.dateCourante().toString());

		gestionBaseDeDonnees.ConnexionOracleViaJdbc.ouvrir();
		System.out.println("Ouvert");
		gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		System.out.println("Ferme");
		
		System.out.println(DAOLieu.getStationsEtGarage());
		System.out.println(DAOLieu.getAllStations());
		

		/*
		GregorianCalendar cal =  new GregorianCalendar(2009,5,5);
		java.util.Date dateUtil = cal.getTime();
		java.sql.Date dateSql =  new java.sql.Date(dateUtil.getTime());
		System.out.println(UtilitaireDate.conversionPourSQL(dateSql));
		Emprunt emprunt = DAOEmprunt.getEmpruntById("1");
		DAOEmprunt.createEmprunt(emprunt);
		
		System.out.println(UtilitaireDate.dateCourante().getTime());
		System.out.println(DAOEmprunt.getEmpruntById("1").getDateEmprunt().getTime());
		System.out.println(DAOEmprunt.getEmpruntById("1").getDateRetour().getTime());
		System.out.println(DAOEmprunt.getEmpruntById("1").getTempsEmprunt());
		*/
		
		/*
		System.out.println(DAOEmprunt.getEmpruntById("55").getUtilisateur());
		System.out.println(DAOEmprunt.getEmpruntById("55").getDateEmprunt());
		System.out.println(DAOEmprunt.getEmpruntById("55").getDateEmprunt().getTime());
		//System.out.println(DAOEmprunt.getEmpruntById("54").getDateRetour());
		//System.out.println(DAOEmprunt.getEmpruntById("54").getTempsEmprunt());
		
		/*
		System.out.println(DAOEmprunt.getEmpruntById("52").getDateEmprunt().getTime());
		System.out.println(DAOEmprunt.getEmpruntById("52").getDateRetour().getTime());
		System.out.println(DAOEmprunt.getEmpruntById("53").getDateEmprunt().getTime());
		System.out.println(DAOEmprunt.getEmpruntById("53").getDateRetour().getTime());
		//System.out.println(UtilitaireDate.dateCourante().getTime());
		
		/*
		System.out.println(DAOUtilisateur.getUtilisateurById("u5"));
		System.out.println(DAOVelo.getVeloById("2"));
		 */

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



		/*
		Station station1 = (Station) DAOLieu.getAllStations().get(1);
		Station station2 = (Station) DAOLieu.getAllStations().get(2);
		Station station3 = (Station) DAOLieu.getAllStations().get(3);

		System.out.println(DAOEmprunt.NombreVelosSortis(station3,100));
		System.out.println(DAOEmprunt.NombreVelosSortis(station1,100));
		System.out.println(DAOEmprunt.NombreVelosSortis(station2,100));
		System.out.println(DAOEmprunt.NombreVelosRentres(station1,100));
		System.out.println(DAOEmprunt.NombreVelosRentres(station3,100));
		 */


		/*System.out.println(DAOIntervention.getNombresVelosParTypeIntervention(4));
		 */

		//Utilisateur u2 = DAOUtilisateur.getUtilisateurById("u2"); 
		//System.out.println(DAOEmprunt.getNombreEmpruntParUtilisateurParMois( u2, 4));

		/*Boolean b = false;
		Boolean c = true;
		System.out.println(-b.compareTo(c));
		 */
		//System.out.println(DAOVelo.getVeloById("2").getLieu());
		
		



	}  

}
