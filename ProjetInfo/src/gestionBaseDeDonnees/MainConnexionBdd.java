package gestionBaseDeDonnees;


import java.sql.SQLException;

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
		 
		 Compte c =new Compte(1,"");
		 System.out.println(DAOCompte.createCompte(c));
		 System.out.println(c.getId());
		 System.out.println(DAOCompte.getCompteById(c.getId()));
		 
		 gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		 System.out.println("Ferme");
		 
			}  

}
