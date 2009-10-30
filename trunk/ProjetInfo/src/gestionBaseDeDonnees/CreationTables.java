package gestionBaseDeDonnees;

/**
 * @author sbalmand
 * @version 1.0
 */

//création des tables dans la base, à faire avant de lancer l'application

import java.sql.SQLException;
import java.sql.Statement;

public class CreationTables {

	static public void main (String argv[]) throws SQLException,ClassNotFoundException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		/*TODO
		 * COMMENT GENERER LES IDENTIFIANTS EN SQL?
		 */


		/*TODO
		 Marine (30/11) : Créations de tables ok, je les laisse en commentaires
		 car j'ai des questions à poser qui impliquerait modif


		s.executeUpdate(
				"CREATE TABLE Utilisateur (idCompte number(4),	"+
				"motDePasse varchar2(20),"+
				"nom char(20) NOT NULL,"+
				"prenom char(20),"+
				"adressePostale varchar2(250),"+
				"adresseMail varchar2(30),"+
				"CONSTRAINT pk_Utilisateur  PRIMARY KEY(idCompte)");
		s.executeUpdate(
				"CREATE TABLE Lieu (idLieu number(4),	"+
				"adresseLieu varchar2(250),"+
				"capacite number(3),"+
				"CONSTRAINT pk_Lieu  PRIMARY KEY(idLieu)");
		s.executeUpdate(
				"CREATE TABLE Velo (idVelo number(4),	"+
				"CONSTRAINT pk_Velo  PRIMARY KEY(idVelo),"+
				"CONSTRAINT fk_Velo_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");
		
		s.executeUpdate(
				"CREATE TABLE Intervention (idInterventionntervention number(10)," +
				"dateIntervention date NOT NULL," +
				"typeIntervention NOT NULL, "+
				"CONSTRAINT pk_Intervention  PRIMARY KEY(idIntervention),"+
				"CONSTRAINT fk_Intervention_Velo FOREIGN KEY(idVelo) REFERENCES Velo)");
		s.executeUpdate(
				"CREATE TABLE Empunt (idEmprunt number(10)," +
				"dateEmprunt date NOT NULL," +
				"dateRetour date NOT NULL, "+
				"lieuEmprunt varchar2(250) NOT NULL," +
				"lieuRetour varchar2(250) NOT NULL, "+
				"CONSTRAINT pk_Empunt  PRIMARY KEY(idEmprunt),"+
				"CONSTRAINT fk_Emprunt_Utilisateur FOREIGN KEY(idCompte) REFERENCES Utilisateur," +
				"CONSTRAINT fk_Emprunt_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
				"CONSTRAINT fk_Emprunt_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");
		s.executeUpdate("COMMIT"); // ne pas oublie le commit!
		ConnexionOracleViaJdbc.fermer(); // et ne pas oublie non plus de fermer la base
		System.out.println("Base cree.");*/



		/* TODO
		 
		
		s.executeUpdate("insert into Utilisateur values('1000','kangourou','ligne à grande vitesse')");
		s.executeUpdate("insert into TypeVoie values('2','LE25','ligne électrifiée 25 000 VA')");
		s.executeUpdate("insert into TypeVoie values('3','LE1.5','ligne électrifiée 1 500 VC')");
		s.executeUpdate("insert into TypeVoie values('4','NE','ligne non électrifiée')");


		s.executeUpdate("insert into Passage values(" +
				"'p'||to_char(ClefPassage.nextval,'fm000000000')," +
				"'n12578','m31844','n12268','v1568746',null," +
		"TO_DATE('01-09-2009 8:00','DD-MM-YYYY HH24:MI'),null,null,null,null," +
		"TO_DATE('01-09-2009 8:00','DD-MM-YYYY HH24:MI')," +
		"TO_DATE('01-09-2009 8:04','DD-MM-YYYY HH24:MI'))");

		s.executeUpdate("insert into Passage values(" +
				"'p'||to_char(ClefPassage.nextval,'fm000000000')," +
				"'n12268','m31844','n02492','v1025935'," +
		"TO_DATE('01-09-2009 8:35','DD-MM-YYYY HH24:MI')," +
		"TO_DATE('01-09-2009 8:39','DD-MM-YYYY HH24:MI'),3,6," +
		"TO_DATE('01-09-2009 8:31','DD-MM-YYYY HH24:MI')," +
		"TO_DATE('01-09-2009 8:39','DD-MM-YYYY HH24:MI')," +
		"TO_DATE('01-09-2009 8:39','DD-MM-YYYY HH24:MI')," +
		"TO_DATE('01-09-2009 8:43','DD-MM-YYYY HH24:MI'))"); 
		s.executeUpdate("COMMIT");
		ConnexionOracleViaJdbc.fermer();
		System.out.println("Update effectuée.");
		 */

	}		
}