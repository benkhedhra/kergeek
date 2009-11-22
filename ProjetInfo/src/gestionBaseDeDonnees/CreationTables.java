package gestionBaseDeDonnees;

/**
 * @author KerGeek
 * @version 1.0
 */

//création des tables dans la base, à faire avant de lancer l'application

import java.sql.SQLException;
import java.sql.Statement;

import metier.Garage;
import metier.Lieu;

public class CreationTables {

	static public void main (String argv[]) throws SQLException,ClassNotFoundException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();
		
		try{

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


			s.executeUpdate ("CREATE SEQUENCE seqAdministrateur INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate ("CREATE SEQUENCE seqUtilisateur INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate ("CREATE SEQUENCE seqTechnicien INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Compte (idCompte char(4),	"+
					"motDePasse varchar2(20) NOT NULL,"+
					"nom char(20),"+
					"prenom char(20),"+
					"adressePostale varchar2(250),"+
					"adresseMail varchar2(30),"+
					"actif number,"+
					"bloque number,"+
					"type number(1),"+
					"idVelo char(4),"+
					"CONSTRAINT pk_Compte  PRIMARY KEY(idCompte),"+
			"CONSTRAINT fk_Compte_Velo  FOREIGN KEY(idVelo) REFERENCES Velo)");


			s.executeUpdate (
			"CREATE SEQUENCE seqDemandeIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE DemandeIntervention (idDemandeI char(4)," +
					"dateDemandeI date NOT NULL," +
					"idVelo char(4),"+
					"idCompte char(4),"+
					"idLieu char(4),"+
					"CONSTRAINT pk_DemandeIntervention  PRIMARY KEY(idDemandeI),"+
					"CONSTRAINT fk_DemandeIntervention_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
			"CONSTRAINT fk_DemandeIntervention_Compte FOREIGN KEY(idCompte) REFERENCES Compte,"+
			"CONSTRAINT fk_DemandeIntervention_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");

			s.executeUpdate (
			"CREATE SEQUENCE seqTypeIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE TypeIntervention (idTypeIntervention char(4)," +
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
					"CREATE TABLE DemandeAssignation (idDemandeA char(4),	"+
					"dateAssignation date NOT NULL,"+
					"priseEnCharge number,"+
					"nombre number(2),"+
					"idLieu char(4),"+
					"CONSTRAINT pk_DemandeAssignation  PRIMARY KEY(idDemandeA),"+
			"CONSTRAINT fk_DemandeAssignation_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");


			s.executeUpdate (
			"CREATE SEQUENCE seqEmprunt INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Emprunt (idEmprunt char(4)," +
					"dateEmprunt date NOT NULL, " +
					"dateRetour date, "+
					"idLieuEmprunt char(4) NOT NULL," +
					"idLieuRetour char(4),"+
					"idCompte char(4),"+
					"idVelo char(4),"+
					"CONSTRAINT pk_Empunt  PRIMARY KEY(idEmprunt),"+
					"CONSTRAINT fk_Emprunt_Compte FOREIGN KEY(idCompte) REFERENCES Compte," +
					"CONSTRAINT fk_Emprunt_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
					"CONSTRAINT fk_Emprunt_LieuEmprunt FOREIGN KEY(idLieuEmprunt) REFERENCES Lieu (idLieu)," + 
			"CONSTRAINT fk_Emprunt_LieuRetour FOREIGN KEY(idLieuRetour) REFERENCES Lieu (idLieu))");

			s.executeUpdate("COMMIT");

			System.out.println("Base creee");

			
			// Insertion lieus
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Gare du Campus','15')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Forum du Campus','10')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'ENSAI','10')");
			DAOLieu.createLieu(Garage.getInstance());

			
			// Insertion velo
			s.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'1')");
			s.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'3')");
			s.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_GARAGE + "')");

			
			// Insertion administrateur
			s.executeUpdate("insert into Compte values(CONCAT('a',seqAdministrateur.nextval),'lapin','','','', 'kergeek@gmail.com', '1','','1','')");

			// Insertion utilisateurs
			s.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'kangourou','Vincent','Francky','69 rue de la passion 35 000 Bruz', 'franckyvincent@gmail.com', '1', '0','3','')");
			s.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'koala','Chedid','Mathieu','10 rue Machistador 35 170 Bruz', 'mathieuchedid@gmail.com', '1', '0','3','2')");
			s.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'bison','Brassens','Georges','1 square des copains 35 180 Goven', 'georgesbrassens@gmail.com', '1', '0','3','')");
			s.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'putois','Marley','Bob','6 rue Marie-Jeanne 35 250 Guichen', 'bobmarley@gmail.com', '1', '0','3','')");
			s.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'fouine','Hilton','Paris','12 avenue de la pouf 35 040 Chartres', 'parishilton@gmail.com', '1', '0','3','')");

			// Insertion techniciens
			s.executeUpdate("insert into Compte values(CONCAT('t',seqTechnicien.nextval),'Repartout','','','', 'didierrepartout@gmail.com', '1','','2','')");
			s.executeUpdate("insert into Compte values(CONCAT('t',seqTechnicien.nextval),'Debrouille','','','', 'jackydebrouille@gmail.com', '1','','2','')");


			//Insertion demande intervention
			s.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('06-11-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u1',"+"'1'"+")");
			s.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('21-11-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u3',"+ "'2'"+")");


			//Insertion types interventions
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'pneu creve')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'selle manquante')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'pedale cassee')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'deraillement')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'freins')");
			s.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'autres')");


			//Insertion interventions
			s.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('06-11-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'1')");
			s.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('19-12-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'2',"+"'1')");
			s.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('01-09-2009 9:18','DD-MM-YYYY HH24:MI'),"+"'6',"+"'2')");


			//Insertion demandes assignation
			s.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('23-11-2009 15:18','DD-MM-YYYY HH24:MI'),"+"'0',"+"'5',"+"'1'"+")");
			s.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('04-11-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'4',"+"'2'"+")");


			//Insertion emprunts
			s.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"
					+"TO_DATE('01-09-2009 9:21','DD-MM-YYYY HH24:MI')," 
					+"TO_DATE('01-09-2009 9:45','DD-MM-YYYY HH24:MI'),"
					+"'2','1','u1','1')");
			s.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('05-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('05-09-2009 9:45','DD-MM-YYYY HH24:MI'), '3','3','u2','2')");
			
			s.executeUpdate("COMMIT");
			
			System.out.println("Update effectuee.");

		}
		/*catch (SQLException e){
			System.out.println(e.getMessage());
		}*/
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
	}		
}