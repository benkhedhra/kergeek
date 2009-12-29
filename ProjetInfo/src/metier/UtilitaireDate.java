package metier;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * La classe UtilitaireDate rassemble l'ensemble des mÈthodes static relatives ‡ l'utilisation
 *  des dates et horaires dans l'applications.
 * @author KerGeek
 */
public class UtilitaireDate {
	
	/**
	 * @return une instance de la classe {@link java.sql.Date} correspondant ‡ la date courante.
	 */
	public static java.sql.Date dateCourante() {
		GregorianCalendar cal = new GregorianCalendar();
		java.util.Date dateUtil = cal.getTime();
		java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
		return dateSql; //cal.getTime renvoie une Date 

	}
	
	/**
	 * Retranche nbHeures heures ‡ la date dateSql.
	 * @param dateSql
	 * @param nbHeures
	 * @return une nouvelle instance de la classe {@link java.sql.Date} dont la date est celle de dateSql à laquelle
	 *  on a retranchÈ nbHeures heures
	 */
	public static java.sql.Date retrancheHeures(java.sql.Date dateSql, int nbHeures){

		// dÈclaration d'un calendrier
		GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier ‡ la date courante;
		gCal.setTime(dateSql);
		gCal.add(Calendar.HOUR_OF_DAY, -nbHeures);

		java.util.Date dateUtil = gCal.getTime();
		java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());

		return nouvelleDateSql;
	}
	
	/**
	 * Retranche nbJours jours ‡ la date dateSql.
	 * @param dateSql
	 * @param nbJours
	 * @return une nouvelle instance de la classe {@link java.sql.Date} dont la date est celle de dateSql à laquelle
	 *  on a retranchÈ nbJours jours
	 */
	public static java.sql.Date retrancheJours(java.sql.Date dateSql, int nbJours){

		// dÈclaration d'un calendrier
		GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier ‡ la date courante;
		gCal.setTime(dateSql);
		gCal.add(Calendar.DATE, -nbJours);

		java.util.Date dateUtil = gCal.getTime();
		java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());

		return nouvelleDateSql;
	}
	
	/**
	 * Ajoute nbJours jours ‡ la date dateSql.
	 * @param dateSql
	 * @param nbJours
	 * @return une nouvelle instance de la classe {@link java.sql.Date} dont la date est celle de dateSql à laquelle
	 *  on a ajoutÈ nbJours jours
	 */
	public static java.sql.Date ajouteJours(java.sql.Date dateSql, int nbJours){

		// dÈclaration d'un calendrier
		GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier ‡ la date courante;
		gCal.setTime(dateSql);
		gCal.add(Calendar.DATE, nbJours);

		java.util.Date dateUtil = gCal.getTime();
		java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());

		return nouvelleDateSql;
	}
	
	/**
	 * Retranche nbMois mois ‡ la date dateSql.
	 * @param dateSql
	 * @param nbMois
	 * @return une nouvelle instance de la classe {@link java.sql.Date} dont la date est celle de dateSql ‡ laquelle
	 *  on a retranchÈ nbMois mois
	 */
	public static java.sql.Date retrancheMois(java.sql.Date dateSql, int nbMois){

		// dÈclaration d'un calendrier
		GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier ‡ la date courante;
		gCal.setTime(dateSql);
		gCal.add(Calendar.MONTH, -nbMois);

		java.util.Date dateUtil = gCal.getTime();
		java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());

		return nouvelleDateSql;
	}
	
	/**
	 * Initialise la date dateSql au début du premier jour du mois de celle-ci.
	 * @param dateSql
	 * @return  une nouvelle instance de la classe {@link java.sql.Date} dont l'annÉe et le mois sont les mÍmes que ceux de dateSql,
	 *  mais dont le jour est le premier de ce mois, ‡ l'horaire 00:00:00.
	 */
	public static java.sql.Date initialisationDebutMois(java.sql.Date dateSql){

		// dÈclaration d'un calendrier
		GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier ‡ la date courante;
		gCal.setTime(dateSql);
		gCal.set(gCal.get(Calendar.YEAR), gCal.get(Calendar.MONTH), gCal.getMinimum(Calendar.DAY_OF_MONTH), gCal.getMinimum(Calendar.HOUR_OF_DAY), gCal.getMinimum(Calendar.MINUTE));
		java.util.Date dateUtil = gCal.getTime();
		java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());

		return nouvelleDateSql;
	}
	
	/**
	 * Initialise la date dateSql au début du jour de celle-ci.
	 * @param dateSql
	 * @return  une nouvelle instance de la classe {@link java.sql.Date} dont l'annÉe le mois et le jour sont les mÍmes que ceux de dateSql,
	 *  mais dont l'horaire est : 00:00:00 .
	 */
	public static java.sql.Date initialisationDebutJour(java.sql.Date dateSql){

		// dÈclaration d'un calendrier
		GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier ‡ la date courante;
		gCal.setTime(dateSql);
		gCal.set(gCal.get(Calendar.YEAR), gCal.get(Calendar.MONTH), gCal.get(Calendar.DATE), gCal.getMinimum(Calendar.HOUR_OF_DAY), gCal.getMinimum(Calendar.MINUTE));
		java.util.Date dateUtil = gCal.getTime();
		java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());

		return nouvelleDateSql;
	}

	/**
	 * Convertit une instance de la Classe {@link java.sql.Date} en chaÓne de caractère
	 *  utilisable pour remplir la base de donnÈes associÈe à l'application.
	 * @param date
	 * @return une chaÓne de caractère présentant la date au format 'DD-MM-YYYY HH24:MI'
	 *  utilisÈ par la base de donnÈes associÈe à l'application.
	 */
	public static String conversionPourSQL(java.sql.Date date){

		String stringDate = "";

		GregorianCalendar calEmprunt =  new GregorianCalendar();
		calEmprunt.setTime(date);
		
		//Jour
		Integer intNumeroJour = calEmprunt.get(Calendar.DAY_OF_MONTH);
		String numeroJour = "";
		if(intNumeroJour.toString().length() == 1){
			numeroJour = "0" + calEmprunt.get(Calendar.DAY_OF_MONTH);
		}
		else{
			numeroJour = "" + calEmprunt.get(Calendar.DAY_OF_MONTH);
		}
		
		//Mois
		Integer intNumeroMois = calEmprunt.get(Calendar.MONTH) + 1;
		String numeroMois = "";
		if(intNumeroMois.toString().length() == 1){
			numeroMois = "0" + intNumeroMois;
		}
		else{
			numeroMois = "" + intNumeroMois;
		}
		
		//Annee
		String numeroAnnee = ""+calEmprunt.get(Calendar.YEAR);
		
		
		//Heure
		Integer intNumeroHeure = calEmprunt.get(Calendar.HOUR_OF_DAY);
		String numeroHeure = "";
		if(intNumeroHeure.toString().length() == 1){
			numeroHeure = "0" + calEmprunt.get(Calendar.HOUR_OF_DAY);
		}
		else{
			numeroHeure = "" + calEmprunt.get(Calendar.HOUR_OF_DAY );
		}
		
		//Minute
		Integer intNumeroMinute = calEmprunt.get(Calendar.MINUTE);
		String numeroMinute = "";
		if(intNumeroMinute.toString().length() == 1){
			numeroMinute = "0" + calEmprunt.get(Calendar.MINUTE);
		}
		else{
			numeroMinute = "" + calEmprunt.get(Calendar.MINUTE);
		}
		
		stringDate = numeroJour+"-"+numeroMois+"-"+numeroAnnee+" "+numeroHeure+":"+numeroMinute;
		return stringDate;
		
	}



}
