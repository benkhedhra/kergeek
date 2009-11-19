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
		 
		 Velo velo =new Velo();
		 System.out.println(DAOVelo.createVelo(velo));
		 System.out.println(velo.getId());
		 System.out.println(DAOVelo.getVeloById(velo.getId()));
		 
		 gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		 System.out.println("Ferme");
		 
			}  

}
