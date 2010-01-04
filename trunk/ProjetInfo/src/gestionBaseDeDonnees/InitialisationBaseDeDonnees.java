package gestionBaseDeDonnees;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.sql.SQLException;
import java.sql.Statement;

import metier.Detruit;
import metier.Garage;
import metier.Lieu;
import metier.Sortie;
import metier.TypeIntervention;

/**
 * Rassemble les méthodes static permettant d'initialiser la base de données necessaire au fonctionnement de l'application.
 * @see CreationTables
 * @author KerGeek
 */
public class InitialisationBaseDeDonnees {
	
	/**
	 * Création des tables où seront stockées les informations necessaires au fonctionnement de l'application, 
	 * et des séquences SQL que celles-ci utilisent.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	static public void creationTables() throws SQLException,ClassNotFoundException, ConnexionFermeeException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		try{

			s.executeUpdate (
			"CREATE SEQUENCE seqLieu INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE -2");
			s.executeUpdate(
					"CREATE TABLE Lieu (idLieu varchar2(4),	"+
					"adresseLieu varchar2(250) NOT NULL,"+
					"capacite number(4),"+
			"CONSTRAINT pk_Lieu  PRIMARY KEY(idLieu))");


			s.executeUpdate (
			"CREATE SEQUENCE seqVelo INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Velo (idVelo varchar2(4),	"+
					"enPanne number,"+
					"idLieu varchar2(4),"+
					"CONSTRAINT pk_Velo  PRIMARY KEY(idVelo),"+
			"CONSTRAINT fk_Velo_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");


			s.executeUpdate ("CREATE SEQUENCE seqAdministrateur INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate ("CREATE SEQUENCE seqUtilisateur INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate ("CREATE SEQUENCE seqTechnicien INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Compte (idCompte char(4),	"+
					"motDePasse varchar2(20) NOT NULL,"+
					"nom varchar2(20),"+
					"prenom varchar2(20),"+
					"adressePostale varchar2(250),"+
					"adresseMail varchar2(50) NOT NULL,"+
					"actif number,"+
					"bloque number,"+
					"type number(1) NOT NULL,"+
			"CONSTRAINT pk_Compte  PRIMARY KEY(idCompte))");


			s.executeUpdate (
			"CREATE SEQUENCE seqTypeIntervention INCREMENT BY 1 START WITH 3 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE TypeIntervention (idTypeIntervention char(4)," +
					"description varchar2(250)," +
			"CONSTRAINT pk_TypeIntervention  PRIMARY KEY(idTypeIntervention))");


			s.executeUpdate (
			"CREATE SEQUENCE seqIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Intervention (idIntervention char(4)," +
					"dateIntervention date NOT NULL," +
					"idTypeIntervention char(4)," +
					"idVelo varchar2(4),"+
					"CONSTRAINT pk_Intervention  PRIMARY KEY(idIntervention),"+
					"CONSTRAINT fk_Inter_TypeInter FOREIGN KEY(idTypeIntervention) REFERENCES TypeIntervention," +
			"CONSTRAINT fk_Intervention_Velo FOREIGN KEY(idVelo) REFERENCES Velo)");


			s.executeUpdate (
			"CREATE SEQUENCE seqDemandeIntervention INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE DemandeIntervention (idDemandeI char(4)," +
					"dateDemandeI date NOT NULL," +
					"idVelo varchar2(4) NOT NULL,"+
					"idCompte char(4) NOT NULL,"+
					"idLieu varchar2(4),"+
					"idIntervention char(4),"+
					"CONSTRAINT pk_DemandeIntervention  PRIMARY KEY(idDemandeI),"+
					"CONSTRAINT fk_DemandeIntervention_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
					"CONSTRAINT fk_DemandeI_Intervention FOREIGN KEY(idIntervention) REFERENCES Intervention," +
					"CONSTRAINT fk_DemandeIntervention_Compte FOREIGN KEY(idCompte) REFERENCES Compte,"+
			"CONSTRAINT fk_DemandeIntervention_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");

			s.executeUpdate (
			"CREATE SEQUENCE seqDemandeAssignation INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE DemandeAssignation (idDemandeA char(4),	"+
					"dateAssignation date NOT NULL,"+
					"priseEnCharge number,"+
					"nombre number(2),"+
					"idLieu varchar2(4),"+
					"CONSTRAINT pk_DemandeAssignation  PRIMARY KEY(idDemandeA),"+
			"CONSTRAINT fk_DemandeAssignation_Lieu FOREIGN KEY(idLieu) REFERENCES Lieu)");

			s.executeUpdate (
			"CREATE SEQUENCE seqEmprunt INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 0");
			s.executeUpdate(
					"CREATE TABLE Emprunt (idEmprunt char(4)," +
					"dateEmprunt date NOT NULL, " +
					"dateRetour date, " +
					"idLieuEmprunt varchar2(4) NOT NULL," +
					"idLieuRetour varchar2(4)," +
					"idCompte char(4)," +
					"idVelo varchar2(4)," +
					"CONSTRAINT pk_Empunt  PRIMARY KEY(idEmprunt)," +
					"CONSTRAINT fk_Emprunt_Compte FOREIGN KEY(idCompte) REFERENCES Compte," +
					"CONSTRAINT fk_Emprunt_Velo FOREIGN KEY(idVelo) REFERENCES Velo," +
					"CONSTRAINT fk_Emprunt_LieuEmprunt FOREIGN KEY(idLieuEmprunt) REFERENCES Lieu (idLieu)," + 
			"CONSTRAINT fk_Emprunt_LieuRetour FOREIGN KEY(idLieuRetour) REFERENCES Lieu (idLieu))");

			s.executeUpdate("COMMIT");
			System.out.println("Base creee");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
	}		
	
	/**
	 *Insertion des données de tests dans la base de données.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public static void insertionDonneesTests() throws SQLException,ClassNotFoundException, ConnexionFermeeException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();

		try{

			// Insertion lieux
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Gare du Campus','15')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Forum du Campus','10')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'ENSAI','10')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Residence','20')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'Gymnase','10')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'ENS','10')");
			s.executeUpdate("insert into Lieu values(seqLieu.nextval,'EME','5')");
			System.out.println(DAOLieu.createLieu(Garage.getInstance()));
			System.out.println(DAOLieu.createLieu(Sortie.getInstance()));
			System.out.println(DAOLieu.createLieu(Detruit.getInstance()));

			ConnexionOracleViaJdbc.ouvrir();
			Statement s2 = ConnexionOracleViaJdbc.createStatement();


			// Insertion vélos


			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'5')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'2')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1'," + "'5')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1'," + "'2')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'5')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_GARAGE + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'1')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'2')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'2')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'2')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_SORTIE +"')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_SORTIE +"')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_SORTIE +"')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_SORTIE +"')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','3')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_GARAGE +"')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_GARAGE +"')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'4')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'2')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'1')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'7')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'7')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'7')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'7')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0'," + "'7')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_GARAGE + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_GARAGE + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_GARAGE + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_GARAGE + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_DETRUIT + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_DETRUIT + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_DETRUIT + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'1','" + Lieu.ID_DETRUIT + "')");
			s2.executeUpdate("insert into Velo values(seqVelo.nextval,'0','" + Lieu.ID_SORTIE +"')");




			// Insertion ADMINISTRATEUR
			s2.executeUpdate("insert into Compte values(CONCAT('a',seqAdministrateur.nextval),'lapin','','','', 'kergeek@gmail.com', '1','','1')");

			// Insertion UTILISATEUR

			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'kangourou','Vincent','Francky','69 rue de la passion 35 000 Bruz', 'id3033@ensai.fr', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'koala','Chedid','Mathieu','10 rue Machistador 35 170 Bruz', 'mathieuchedid@gmail.com', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'bison','Brassens','Georges','1 square des copains 35 180 Goven', 'id2989@ensai.fr', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'putois','Marley','Bob','6 rue Marie-Jeanne 35 250 Guichen', 'bobmarley@gmail.com', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'fouine','Hilton','Paris','12 avenue de la pouf 35 040 Chartres', 'parishilton@gmail.com', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'colombe','Brel','Jacques','12 rue des amants 35 580 Pontrean', 'breljacques@gmail.com', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'fourmi','Coquet','Francois','1 avenue des boreliens 35 000 Rennes', 'francoiscoquet@gmail.com', '1', '1','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'singe','Benabar','Bruno','10 rue de la poesie 35 170 Bruz', 'brunobenabar@gmail.com', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'requin','Delprat','David','0 rue des etudiants 35 170 Bruz', 'daviddelprat@gmail.com', '1', '0','3')");
			s2.executeUpdate("insert into Compte values(CONCAT('u',seqUtilisateur.nextval),'baleine','Christie','Agatha','9 rue du crime 35 170 Bruz', 'agathachristie@gmail.com', '1', '0','3')");

			// Insertion TECHNICIEN
			s2.executeUpdate("insert into Compte values(CONCAT('t',seqTechnicien.nextval),'reparetout','','','', 'didierrepartout@gmail.com', '1','','2')");
			s2.executeUpdate("insert into Compte values(CONCAT('t',seqTechnicien.nextval),'debrouille','','','', 'jackydebrouille@gmail.com', '1','','2')");


			//Insertion types interventions
			s2.executeUpdate("insert into TypeIntervention values('" + TypeIntervention.TYPE_NON_JUSTIFIEE + "','demande non justifiée')");
			s2.executeUpdate("insert into TypeIntervention values('" + TypeIntervention.TYPE_DESTRUCTION + "','destruction')");			
			s2.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'pneu creve')");
			s2.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'selle manquante')");
			s2.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'pedale cassee')");
			s2.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'deraillement')");
			s2.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'freins')");
			s2.executeUpdate("insert into TypeIntervention values(seqTypeIntervention.nextval,'autres')");

			//Insertion interventions

			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('06-11-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'3',"+"'1')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('27-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('28-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'6',"+"'1')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('13-10-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'5',"+"'3')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('05-07-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'7',"+"'4')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('08-07-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'7',"+"'5')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('22-08-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'4',"+"'6')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('24-08-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'3',"+"'7')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('26-08-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'8')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('27-08-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'5',"+"'9')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('30-08-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'4',"+"'10')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('03-09-2009 09:00','DD-MM-YYYY HH24:MI'),"+"'3',"+"'11')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('03-09-2009 10:00','DD-MM-YYYY HH24:MI'),"+"'7',"+"'12')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('03-09-2009 11:00','DD-MM-YYYY HH24:MI'),"+"'4',"+"'13')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('05-09-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'3',"+"'14')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('10-09-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'4',"+"'15')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('12-09-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'3',"+"'16')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('27-09-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'17')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('02-10-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'7',"+"'18')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('04-10-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'4',"+"'19')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('06-10-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'20')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('15-10-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'1')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('18-10-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'5',"+"'2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('24-10-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'8',"+"'3')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('30-10-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'1')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('03-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'6',"+"'2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('05-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'3',"+"'4')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('10-11-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'4',"+"'5')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('20-11-2009 17:20','DD-MM-YYYY HH24:MI'),"+"'4',"+"'3')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('20-11-2009 18:00','DD-MM-YYYY HH24:MI'),"+"'7',"+"'1')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('23-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'4',"+"'2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('26-10-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'8',"+"'6')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('06-11-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'6',"+"'7')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('27-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'8')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('28-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'4',"+"'1')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('13-11-2009 16:16','DD-MM-YYYY HH24:MI'),"+"'8',"+"'2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('25-11-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'7',"+"'2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('07-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'4',"+"'15')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('11-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'8',"+"'16')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('13-11-2009 13:46','DD-MM-YYYY HH24:MI'),"+"'4',"+"'17')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"SYSDATE-3,"+"'',"+"'26')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"SYSDATE-2,"+"'',"+"'25')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"SYSDATE-34/24,"+"'',"+"'27')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"SYSDATE,"+"'',"+"'28')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('01-12-2009 12:01','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_DESTRUCTION+"','30')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('04-10-2009 18:54','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_DESTRUCTION+"','31')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('19-11-2009 12:59','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_DESTRUCTION+"','32')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('08-09-2009 13:01','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_DESTRUCTION+"','33')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('01-08-2009 12:08','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_NON_JUSTIFIEE+"','2')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('12-10-2009 15:32','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_NON_JUSTIFIEE+"','13')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('02-12-2009 15:40','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_NON_JUSTIFIEE+"','18')");
			s2.executeUpdate("insert into Intervention values(seqIntervention.nextval,"+"TO_DATE('22-09-2009 17:15','DD-MM-YYYY HH24:MI'),'"+TypeIntervention.TYPE_NON_JUSTIFIEE+"','22')");


			//Insertion demandes intervention

			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('06-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u1',"+"'1',"+"'1'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('21-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'2',"+"'u3',"+ "'2',"+"'2'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('27-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u4',"+"'1',"+"'3'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('13-10-2009 16:14','DD-MM-YYYY HH24:MI'),"+"'3',"+"'u1',"+ "'3',"+"'4'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('20-08-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'5',"+"'u5',"+"'5',"+"'7'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('26-08-2009 08:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u2',"+ "'1',"+"'9'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('02-09-2009 09:00','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u3',"+ "'1',"+"'13'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('03-09-2009 08:00','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u2',"+ "'1',"+"'14'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('05-09-2009 11:30','DD-MM-YYYY HH24:MI'),"+"'5',"+"'u1',"+"'5',"+"'15'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('11-09-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u5',"+ "'1',"+"'17'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('26-09-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'3',"+"'u7',"+"'3',"+"'18'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('29-08-2009 14:00','DD-MM-YYYY HH24:MI'),"+"'2',"+"'u2',"+ "'2',"+"'25'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('27-11-2009 10:00','DD-MM-YYYY HH24:MI'),"+"'4',"+"'u6',"+ "'4',"+"'35'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('25-11-2009 09:18','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u2',"+"'1',"+"'36'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('27-11-2009 14:30','DD-MM-YYYY HH24:MI'),"+"'2',"+"'u1',"+ "'2',"+"'34'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('27-11-2009 09:15','DD-MM-YYYY HH24:MI'),"+"'3',"+"'u5',"+"'3',"+"'1'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('06-11-2009 21:00','DD-MM-YYYY HH24:MI'),"+"'1',"+"'u3',"+ "'1',"+"'28'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"TO_DATE('05-10-2009 10:18','DD-MM-YYYY HH24:MI'),"+"'2',"+"'u4',"+ "'2',"+"'21'"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"SYSDATE-1,"+"'4',"+"'u2',"+ "'2',"+"''"+")");
			s2.executeUpdate("insert into DemandeIntervention values(seqDemandeIntervention.nextval,"+"SYSDATE,"+"'3',"+"'u1',"+"'5',"+"''"+")");


			//Insertion demandes assignation
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"SYSDATE-1/24,"+"'0',"+"'5',"+"'1'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"SYSDATE-23/(24*60),"+"'0',"+"'4',"+"'2'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"SYSDATE,"+"'0',"+"'10',"+"'3'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"SYSDATE,"+"'0',"+"'15',"+"'0'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('01-12-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'1',"+"'6'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('21-11-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'3',"+"'2'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('04-10-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'2',"+"'5'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('05-11-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'4',"+"'2'"+")");
			s2.executeUpdate("insert into DemandeAssignation values(seqDemandeAssignation.nextval,"+"TO_DATE('14-12-2009 10:13','DD-MM-YYYY HH24:MI'),"+"'1',"+"'3',"+"'2'"+")");

			//Insertion emprunts

			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('01-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
					"TO_DATE('01-09-2009 9:45','DD-MM-YYYY HH24:MI'),"+"'2','1','u1','1')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('05-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('05-09-2009 9:45','DD-MM-YYYY HH24:MI'), '3','3','u2','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('27-11-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('29-11-2009 9:45','DD-MM-YYYY HH24:MI'), '3','3','u3','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('27-09-2009 9:21','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('27-09-2009 9:22','DD-MM-YYYY HH24:MI'), '3','4','u4','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('12-10-2009 12:00','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('12-10-2009 12:45','DD-MM-YYYY HH24:MI'), '1','1','u1','1')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('12-10-2009 12:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('12-10-2009 13:45','DD-MM-YYYY HH24:MI'), '1','1','u2','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('12-10-2009 14:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('12-10-2009 15:00','DD-MM-YYYY HH24:MI'), '3','3','u2','3')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('03-12-2009 08:13','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('03-12-2009 09:01','DD-MM-YYYY HH24:MI'), '2','4','u1','1')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-((21*60+58)/(24*60))," +
			"SYSDATE - (22*60+17)/(24*60), '1','2','u7','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-((27*60)/(24*60))," +
			"SYSDATE - (27*60+8)/(24*60), '2','5','u3','3')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-((60*2)/(24*60))," +
			"SYSDATE- (60*2+21)/(24*60), '4','2','u4','4')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('03-10-2009 10:03','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('03-10-2009 12:00','DD-MM-YYYY HH24:MI'), '2','3','u10','16')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('04-12-2009 13:24','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('04-12-2009 13:27','DD-MM-YYYY HH24:MI'), '1','2','u6','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('04-12-2009 11:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('04-12-2009 11:30','DD-MM-YYYY HH24:MI'), '1', '2','u7','11')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('03-12-2009 12:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('03-12-2009 12:53','DD-MM-YYYY HH24:MI'), '2','5','u1','1')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('03-12-2009 12:46','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('03-12-2009 12:54','DD-MM-YYYY HH24:MI'), '1','2','u9','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-5," +
			"'', '4','','u1','11')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-(0.0194)," +
			"'', '2','','u2','12')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-(0.39583)," +
			"'', '2','','u3','13')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE," +
			"'', '2','','u4','14')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('11-11-2009 11:24','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('11-11-2009 11:45','DD-MM-YYYY HH24:MI'), '1','2','u6','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('11-11-2009 09:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('11-11-2009 14:24','DD-MM-YYYY HH24:MI'), '1','','u7','11')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('13-11-2009 12:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('13-11-2009 13:00','DD-MM-YYYY HH24:MI'), '2','5','u1','1')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('14-11-2009 12:46','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('14-11-2009 12:59','DD-MM-YYYY HH24:MI'), '1','2','u2','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('18-11-2009 12:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('19-11-2009 08:00','DD-MM-YYYY HH24:MI'), '4','','u3','12')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('20-11-2009 13:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('20-11-2009 13:31','DD-MM-YYYY HH24:MI'), '2','','u4','13')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('21-11-2009 13:12','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('21-11-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u5','14')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('26-11-2009 13:14','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('11-11-2009 13:20','DD-MM-YYYY HH24:MI'), '2','','u6','15')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('26-11-2009 13:15','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('26-11-2009 13:28','DD-MM-YYYY HH24:MI'), '1','','u8','16')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('30-11-2009 13:17','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('30-11-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u1','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('05-06-2009 11:24','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('05-06-2009 11:45','DD-MM-YYYY HH24:MI'), '1','2','u6','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('06-06-2009 09:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('06-06-2009 14:24','DD-MM-YYYY HH24:MI'), '1','','u1','11')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('06-06-2009 12:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('06-06-2009 13:00','DD-MM-YYYY HH24:MI'), '2','5','u1','1')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('14-09-2009 12:46','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('14-09-2009 12:59','DD-MM-YYYY HH24:MI'), '1','2','u2','2')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('15-09-2009 12:45','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('15-09-2009 08:00','DD-MM-YYYY HH24:MI'), '4','','u3','12')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('15-09-2009 13:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('15-09-2009 13:31','DD-MM-YYYY HH24:MI'), '2','','u1','13')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('20-09-2009 13:12','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('20-09-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u5','14')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('23-09-2009 13:14','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('23-09-2009 13:20','DD-MM-YYYY HH24:MI'), '2','','u6','15')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('25-10-2009 13:15','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('25-10-2009 13:28','DD-MM-YYYY HH24:MI'), '1','','u8','16')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('26-10-2009 13:17','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('26-10-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u1','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('25-10-2009 13:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('15-10-2009 13:31','DD-MM-YYYY HH24:MI'), '2','','u1','13')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('01-09-2009 13:12','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('01-09-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u1','14')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('04-09-2009 13:14','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('04-09-2009 13:20','DD-MM-YYYY HH24:MI'), '2','','u1','15')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('06-09-2009 13:15','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('06-09-2009 13:28','DD-MM-YYYY HH24:MI'), '1','','u1','16')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('07-09-2009 13:17','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('07-09-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u1','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('01-10-2009 13:05','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('01-10-2009 13:31','DD-MM-YYYY HH24:MI'), '2','','u1','13')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('02-10-2009 13:12','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('02-10-2009 15:24','DD-MM-YYYY HH24:MI'), '2','','u1','14')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('04-10-2009 13:14','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('04-10-2009 13:20','DD-MM-YYYY HH24:MI'), '2','','u1','15')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('10-10-2009 13:15','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('10-10-2009 13:28','DD-MM-YYYY HH24:MI'), '1','','u1','16')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"TO_DATE('07-09-2009 15:17','DD-MM-YYYY HH24:MI')," +
			"TO_DATE('07-09-2009 16:24','DD-MM-YYYY HH24:MI'), '2','','u1','17')");
			s2.executeUpdate("insert into Emprunt values(seqEmprunt.nextval,"+"SYSDATE-40," +
			"'', '4','','u10','34')");

			s2.executeUpdate("COMMIT");
			System.out.println("Update effectué.");

		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
	}	
	
	/**
	 * Suppression des tables et des séquences SQL
	 * préalablement créées au moyen de la méthode {@link InitialisationBaseDeDonnees#creationTables()},
	 * dans l'ordre inverse de leur création.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	static public void suppressionTables() throws SQLException,ClassNotFoundException, ConnexionFermeeException {

		ConnexionOracleViaJdbc.ouvrir();
		Statement s = ConnexionOracleViaJdbc.createStatement();


		try{
			s.executeUpdate("DROP TABLE Emprunt");
			s.executeUpdate("DROP SEQUENCE seqEmprunt");
			s.executeUpdate("DROP TABLE DemandeAssignation ");
			s.executeUpdate("DROP SEQUENCE seqDemandeAssignation");
			s.executeUpdate("DROP TABLE DemandeIntervention");
			s.executeUpdate("DROP SEQUENCE seqDemandeIntervention");
			s.executeUpdate("DROP TABLE Intervention");
			s.executeUpdate("DROP SEQUENCE seqIntervention");
			s.executeUpdate("DROP TABLE TypeIntervention ");	
			s.executeUpdate("DROP SEQUENCE seqTypeIntervention");
			s.executeUpdate("DROP TABLE Compte");
			s.executeUpdate("DROP SEQUENCE seqTechnicien");
			s.executeUpdate("DROP SEQUENCE seqUtilisateur");
			s.executeUpdate("DROP SEQUENCE seqAdministrateur");
			s.executeUpdate("DROP TABLE Velo");
			s.executeUpdate("DROP SEQUENCE seqVelo");
			s.executeUpdate("DROP TABLE Lieu");
			s.executeUpdate("DROP SEQUENCE seqLieu");
			

			s.executeUpdate("COMMIT");
			
			System.out.println("Suppression effectuee.");
		}
		catch (SQLException e){
			ConnexionOracleViaJdbc.fermer();
			System.out.println(e.getMessage());
		}
		finally{
			ConnexionOracleViaJdbc.fermer();
		}
	}		
}