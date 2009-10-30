package test;

import java.sql.SQLException;

import gestionBaseDeDonnees.UtilitaireDate;

public class testMain {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println(UtilitaireDate.dateCourante().toString());
		gestionBaseDeDonnees.ConnexionOracleViaJdbc.ouvrir();
		gestionBaseDeDonnees.ConnexionOracleViaJdbc.fermer();
	}

}
