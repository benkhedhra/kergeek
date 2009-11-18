package gestionBaseDeDonnees;

/**
 * @author KerGeek
 * @version 1.0
 */

//cr�ation des tables dans la base, � faire avant de lancer l'application

import java.sql.SQLException;
import java.sql.Statement;

import metier.Garage;

public class CreationTables {

	static public void main (String argv[]) throws SQLException,ClassNotFoundException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		/*TODO
		 * Il nous manque la table Utilisateur
		 *
		 */
		try{

			s.executeUpdate ("CREATE SEQUENCE seqAdministrateur INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate ("CREATE SEQUENCE seqUtilisateur INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate ("CREATE SEQUENCE seqTechnicien INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");

			s.executeUpdate(
					"CREATE TABLE Compte (idCompte char(4),	"+
					"motDePasse varchar2(20),"+
					"nom char(20) NOT NULL,"+
					"prenom char(20),"+
					"adressePostale varchar2(250),"+
					"adresseMail varchar2(30),"+
					"actif number,"+
					"bloque number,"+
					"type number(1),"+
			"CONSTRAINT pk_Compte  PRIMARY KEY(idCompte))");


			s.executeUpdate (
			"CREATE SEQUENCE seqLieu INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Lieu (idLieu char(4),	"+
					"adresseLieu varchar2(250),"+
					"capacite number(4),"+
			"CONSTRAINT pk_Lieu  PRIMARY KEY(idLieu))");


			s.executeUpdate (
			"CREATE SEQUENCE seqVelo INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Velo (idVelo char(4),	"+
					"enPanne number,"+
					"idLieu char(4),"+
					"CONSTRAINT pk_Velo  PRIMARY KEY(idVelo),"+
			"CONSTRAINT fk_Velo_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");

			s.executeUpdate (
			"CREATE SEQUENCE seqDemandeIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE DemandeIntervention (idDemandeI char(4)," +
					"dateDemandeI date NOT NULL," +
					"idVelo char(4),"+
					"idCompte char(4),"+
					"CONSTRAINT pk_DemandeIntervention  PRIMARY KEY(idDemandeI),"+
					"CONSTRAINT fk_DemandeIntervention_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
			"CONSTRAINT fk_DemandeIntervention_Compte FOREIGN KEY(idCompte) REFERENCES Compte)");

			s.executeUpdate (
			"CREATE SEQUENCE seqTypeIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE TypeIntervention (idTypeIntervention char(4)," +
					"numero number(2)," +
					"description char(250)," +
			"CONSTRAINT pk_TypeIntervention  PRIMARY KEY(idTypeIntervention))");

			s.executeUpdate (
			"CREATE SEQUENCE seqIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Intervention (idIntervention char(4)," +
					"dateIntervention date NOT NULL," +
					"idTypeIntervention char(4)," +
					"idVelo char(4),"+
					"CONSTRAINT pk_Intervention  PRIMARY KEY(idIntervention),"+
					"CONSTRAINT fk_Inter_TypeInter FOREIGN KEY(idTypeIntervention) REFERENCES TypeIntervention," +
			"CONSTRAINT fk_Intervention_Velo FOREIGN KEY(idVelo) REFERENCES Velo)");

			s.executeUpdate (
			"CREATE SEQUENCE seqDemandeAssignation INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE DemandeAssignation (idDemande char(4),	"+
					"dateAssignation date NOT NULL,"+
					"ajout number,"+
					"nombre number(2),"+
					"idLieu char(4),"+
					"CONSTRAINT pk_DemandeAssignation  PRIMARY KEY(idDemande),"+
			"CONSTRAINT fk_DemandeAssignation_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");

			s.executeUpdate (
			"CREATE SEQUENCE seqEmprunt INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Emprunt (idEmprunt char(4)," +
					"dateEmprunt date NOT NULL," +
					"dateRetour date NOT NULL, "+
					"lieuEmprunt varchar2(250) NOT NULL," +
					"lieuRetour varchar2(250) NOT NULL, "+
					"idCompte char(4),"+
					"idVelo char(4),"+
					"idLieu char(4),"+
					"CONSTRAINT pk_Empunt  PRIMARY KEY(idEmprunt),"+
					"CONSTRAINT fk_Emprunt_Compte FOREIGN KEY(idCompte) REFERENCES Compte," +
					"CONSTRAINT fk_Emprunt_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
			"CONSTRAINT fk_Emprunt_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");

			s.executeUpdate("COMMIT");

			System.out.println("Base creee");



			// Insertion administrateur
			s.executeUpdate("insert into Compte values(CONCAT('1',seqAdministrateur.nextval),'lapin','Ker','Geek','Ensai 35 172 Bruz', 'kergeek@gmail.com', '1', '0','1')");


			// Insertion utilisateurs
			s.executeUpdate("insert into Compte values(CONCAT('3',seqUtilisateur.nextval),'kangourou','Vincent','Francky','69 rue de la passion 75 001 Paris', 'franckyvincent@gmail.com', '1', '0','3')");
			s.executeUpdate("insert into Compte values(CONCAT('3',seqUtilisateur.nextval),'koala','Chedid','Mathieu','10 rue Machistador 17 000 La Rochelle', 'mathieuchedid@gmail.com', '1', '0','3')");
			s.executeUpdate("insert into Compte values(CONCAT('3',seqUtilisateur.nextval),'bison','Brassens','Georges','1 square des copains 34 000 Sete', 'georgesbrassens@gmail.com', '1', '0','3')");
			s.executeUpdate("insert into Compte values(CONCAT('3',seqUtilisateur.nextval),'putois','Marley','Bob','6 rue Marie-Jeanne 13 000 Marseille', 'bobmarley@gmail.com', '1', '0','3')");
			s.executeUpdate("insert into Compte values(CONCAT('3',seqUtilisateur.nextval),'fouine','Hilton','Paris','12 avenue de la pouf 06 400 Cannes', 'parishilton@gmail.com', '1', '0','3')");


			// Insertion techniciens
			s.executeUpdate("insert into Compte values(CONCAT('2',seqTechnicien.nextval),'chacal','Repartout','Didier','45 boulevard du cambouis 59 000 Lille', 'didierrepartout@gmail.com', '1', '0','2')");
			s.executeUpdate("insert into Compte values(CONCAT('2',seqTechnicien.nextval),'castor','Debrouille','Jacky','26 all�e de la vidange 57 000 Metz', 'jackydebrouille@gmail.com', '1', '0','2')");


			// Insertion lieus
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Gare du Campus','15')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Forum du Campus','10')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'ENSAI','10')");
			DAOLieu.createLieu(Garage.getInstance());
			s.executeUpdate("COMMIT");
			
			// Insertion velos
			s.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + DAOLieu.getAllStation().get(0).getId()+"')");
			s.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + DAOLieu.getAllStation().get(2).getId()+"')");
			s.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Garage.ID_GARAGE + "')");

			
			//Insertion demande intervention
			s.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('06-11-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'31'"+")");
			s.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('21-11-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'33'"+")");
			

			//Insertion types interventions
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'1','pneu creve')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'1','pneu creve')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'2','selle')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'3','pedale')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'4','deraillement')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'5','frein')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'6','autres')");

			
			//Insertion interventions
			s.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('06-11-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'1')");
			s.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('19-12-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'2',"+"'1')");
			s.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('01-09-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'6',"+"'2')");

			
			//Insertion demandes assignation
			s.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('23-11-2009 15:18','DD-MM-YYYY HH24:MI'),"+"'0',"+"'5',"+"'1'"+")");
			s.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('04-11-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'4',"+"'2'"+")");

			/*
			//Insertion emprunts
			s.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('01-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('01-09-2009 9:45','DD-MM-YYYY HH24:MI'), '0001','0002')");
			s.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('05-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('05-09-2009 9:45','DD-MM-YYYY HH24:MI'), '0001','0003')");
			s.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('01-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('01-09-2009 9:22','DD-MM-YYYY HH24:MI'), '0001','0002')");
			s.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('01-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('06-09-2009 9:45','DD-MM-YYYY HH24:MI'), '0001','0002')");

			 */
			s.executeUpdate("COMMIT");
			System.out.println("Update effectuee.");
		}/*
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();
			System.out.println(e.getMessage());
		}*/
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
	}		
}