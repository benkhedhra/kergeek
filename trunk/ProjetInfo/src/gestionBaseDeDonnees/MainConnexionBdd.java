package gestionBaseDeDonnees;


import java.sql.SQLException;

import metier.Garage;

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
		 System.out.println("id = "+Garage.getInstance().getId());
		 System.out.println("adresse = "+Garage.getInstance().getAdresse());
		 System.out.println("capacit� = "+Garage.getInstance().getCapacite());
		 System.out.println(DAOLieu.createLieu(Garage.getInstance()));
		 gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		 System.out.println("Ferme");
		 
			}  

}
