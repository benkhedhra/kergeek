package gestionBaseDeDonnees;


import java.sql.SQLException;

public class MainConnexionBdd {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		 System.out.println("Date courante : " + UtilitaireDate.dateCourante().toString());
		 gestionBaseDeDonnees.ConnexionOracleViaJdbc.ouvrir();
		 System.out.println("Ouvert");
		 gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
		 System.out.println("Ferme");
		 
			}  

}
