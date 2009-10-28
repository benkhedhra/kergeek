package metier;


import gestionBaseDeDonnees.ConnexionOracleViaJdbc;
import gestionBaseDeDonnees.UtilitaireDate;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Emprunte {
	private Date dateEmprunt;
	private Date dateRetour;
	private Lieu lieuEmprunt;
	private Lieu lieuRetour;
	long diff = dateRetour.getTime() - dateEmprunt.getTime();
	float tpsEmprunt = diff / 3600000.0f;

	//Constructeur


	public Emprunte(Date dateEmprunt, Lieu lieuEmprunt) {
		super();
		this.setDateEmprunt(dateEmprunt);
		this.setLieuEmprunt(lieuEmprunt);
	}

	//Accesseurs

	public Date getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public Date getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Lieu getLieuEmprunt() {
		return lieuEmprunt;
	}

	public void setLieuEmprunt(Lieu lieuEmprunt) {
		this.lieuEmprunt = lieuEmprunt;
	}

	public Lieu getLieuRetour() {
		return lieuRetour;
	}

	public void setLieuRetour(Lieu lieuRetour) {
		this.lieuRetour = lieuRetour;
	}
	
	public float getTpsEmprunt() {
		return tpsEmprunt;
	}

	public void setTpsEmprunt(float tpsEmprunt) {
		this.tpsEmprunt = tpsEmprunt;
	}


	//Methodes

	public static void proposerDemanderIntervention(Velo velo, Utilisateur utilisateur) throws SQLException,ClassNotFoundException{
		System.out.println("Souhaitez-vous demander une intervention sur ce vélo?\n Oui : 1\n Non : 2");
		Scanner sc= new Scanner(System.in);

		try {
			int rep = Integer.parseInt(sc.next());
			if (rep == 1){

				ConnexionOracleViaJdbc.ouvrir();
				Statement s = ConnexionOracleViaJdbc.createStatement();
				s.executeUpdate("INSERT into DemandeIntervention values ('" 
						+ UtilitaireDate.dateCourante() + "', '"+ velo + "', '" 
						+ velo.getLieu().getId() + "', '" + utilisateur.getCompte().getId() + "')");
				ConnexionOracleViaJdbc.fermer();
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Je n'ai pas compris votre reponse, veuillez entrer soit un 1 soit un 2 s'il vous plait.");
			proposerDemanderIntervention(velo, utilisateur);
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();

		}

		/*TODO
		 * rendreVelo : faire le test sur le temps d'emprunt puis appeler cette methode le cas echeant
		 */

	}

}
