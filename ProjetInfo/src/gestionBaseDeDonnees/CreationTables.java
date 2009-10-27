package gestionBaseDeDonnees;

/**
 * @author sbalmand
 * @version 1.0
 */

//crÈation des tables dans la base, ‡ faire avant de lancer l'application

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
		 * Ci après, un exemple de creation de table (celui du tp de complemetn de java)
		 * (a faire des que le modele de bdd en 3e forme normale est pret)
		 *  

		 Creation des tables:




		s.executeUpdate(
				"CREATE TABLE TypeVoie (idTypeVoie CHAR(1)," +
				"typeVoie VARCHAR2(20) NOT NULL, libelle VARCHAR2(30)," +
				"CONSTRAINT pk_TypeVoie  PRIMARY KEY(idTypeVoie))"
		);


		s.executeUpdate(
				"CREATE TABLE TypeNoeud (idTypeNoeud CHAR(1),"+
				"typeNoeud VARCHAR2(10) NOT NULL," +
				"CONSTRAINT pk_TypeNoeud  PRIMARY KEY(idTypeNoeud))"
		);

		s.executeUpdate(
				"CREATE TABLE CategorieTrain (idCategorieTrain CHAR(1)," +
				"categorie VARCHAR2(10) NOT NULL," +
				"CONSTRAINT pk_CategorieTrain  PRIMARY KEY(idCategorieTrain))"
		);

		s.executeUpdate(
				"CREATE TABLE Train (idTrain VARCHAR2(6)," + 
				"nom VARCHAR2(5), idCategorieTrain CHAR(1) NOT NULL," +
				"CONSTRAINT sk1_Train CHECK(nom IS NOT NULL),CONSTRAINT sk2_Train UNIQUE(nom)," +
				"CONSTRAINT pk_Train  PRIMARY KEY(idTrain)," +
				"CONSTRAINT fk_Train_CategorieTrain FOREIGN KEY(idCategorieTrain) REFERENCES CategorieTrain)" 
		);
		s.executeUpdate(
				"CREATE TABLE Noeud (idNoeud VARCHAR2(6)," +
				"nom VARCHAR2(20), idTypeNoeud CHAR(1)," +
				"CONSTRAINT pk_Noeud PRIMARY KEY(idNoeud)," +
				"CONSTRAINT sk1_Noeud CHECK(nom IS NOT NULL),CONSTRAINT sk2_Noeud UNIQUE(nom)," +
				"CONSTRAINT fk_Noeud_TypeNoeud FOREIGN KEY(idTypeNoeud) REFERENCES TypeNoeud)"
		);
		s.executeUpdate(
				"CREATE TABLE Troncon (idTroncon VARCHAR2(6)," + 
				"idNoeud1 VARCHAR2(6), idNoeud2 VARCHAR2(6), distance INTEGER," + 
				"CONSTRAINT pk_Troncon  PRIMARY KEY(idTroncon)," +
				"CONSTRAINT fk_Troncon_Noeud1 FOREIGN KEY(idNoeud1) REFERENCES Noeud," +
				"CONSTRAINT fk_Troncon_Noeud2 FOREIGN KEY(idNoeud2) REFERENCES Noeud)"
		);
		s.executeUpdate(
				"CREATE TABLE Voie (idVoie VARCHAR2(8)," + 
				"idTypeVoie CHAR(1), idTroncon VARCHAR2(6)," + 
				"tempsSuccessionMinimal INTEGER," + 
				"CONSTRAINT pk_Voie  PRIMARY KEY(idVoie)," +
				"CONSTRAINT fk_Voie_TypeVoie FOREIGN KEY(idTypeVoie) REFERENCES TypeVoie," + 
				"CONSTRAINT fk_Voie_Troncon FOREIGN KEY(idTroncon) REFERENCES Troncon)"		
		);
		s.executeUpdate(
				"CREATE TABLE TempsParcours (idVoie VARCHAR2(8)," +
				"idTrain VARCHAR2(6)," +
				"tempsParcoursMin INTEGER,tempsParcoursMax INTEGER," +
				"CONSTRAINT pk_TempsParcours  PRIMARY KEY(idVoie,idTrain)," +
				"CONSTRAINT fk_TempsParcours_Voie FOREIGN KEY(idVoie) REFERENCES Voie," +
				"CONSTRAINT fk_TempsParcours_Train FOREIGN KEY(idTrain) REFERENCES Train)"
		);
		s.executeUpdate(
				"CREATE TABLE Passage (idPassage VARCHAR2(10)," + 
				"idNoeudPassage VARCHAR2(6), idTrain VARCHAR2(6)," +
				"idNoeudSuivant VARCHAR2(6), idVoie VARCHAR2(8), heureArrNormal DATE," +
				"heureDepNormal DATE, dureeMin INTEGER, dureeMax INTEGER, heureArrMin DATE," + 
				"heureArrMax DATE, heureDepMin DATE, heureDepMax DATE," +
				"CONSTRAINT pk_Passage  PRIMARY KEY(idPassage)," +
				"CONSTRAINT fk_Passage_NoeudPassage FOREIGN KEY(idNoeudPassage) REFERENCES Noeud," +
				"CONSTRAINT fk_Passage_NoeudSuivant FOREIGN KEY(idNoeudSuivant) REFERENCES Noeud," +
				"CONSTRAINT fk_Passage_Train FOREIGN KEY(idTrain) REFERENCES Train," +
				"CONSTRAINT fk_Passage_Voie FOREIGN KEY(idVoie) REFERENCES Voie)"
		);
		s.executeUpdate(
				"CREATE SEQUENCE ClefPassage MINVALUE 1 MAXVALUE 999999999"+ 
				"START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE"		
		);

		s.executeUpdate("COMMIT"); // ne pas oublie le commit!
		ConnexionOracleViaJdbc.fermer(); // et ne pas oublie non plus de fermer la base
		System.out.println("Base cree.");*/







		/* TODO
		 * Ci après, un exemple de creation de table (celui du tp de complemetn de java)
		 * 
		
		Update:
		
		
		
		
		s.executeUpdate("insert into TypeVoie values('1','LGV','ligne ‡ grande vitesse')");
		s.executeUpdate("insert into TypeVoie values('2','LE25','ligne ÈlectrifiÈe 25 000 VA')");
		s.executeUpdate("insert into TypeVoie values('3','LE1.5','ligne ÈlectrifiÈe 1 500 VC')");
		s.executeUpdate("insert into TypeVoie values('4','NE','ligne non ÈlectrifiÈe')");


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
		System.out.println("Update effectuÈe.");
		 */

	}		
}