package metier;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UtilitaireDate {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	
	public static java.sql.Date dateCourante() {
		GregorianCalendar cal = new GregorianCalendar();
		java.util.Date dateUtil = cal.getTime();
		java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
		return dateSql; //cal.getTime renvoie une Date 

	}
	
	public static Time heureCourante(){
		return new Time(System.currentTimeMillis());
	}
	
public static java.sql.Date retrancheHeures(java.sql.Date dateSql, int nbHeures){
		
		// déclaration d'un calendrier
		  GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier à la date courante;
		  gCal.setTime(dateSql);
		  gCal.add(Calendar.HOUR_OF_DAY, -nbHeures);
		  
		  java.util.Date dateUtil = gCal.getTime();
		  java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());
		  
		return nouvelleDateSql;
	}
	
	public static java.sql.Date retrancheJours(java.sql.Date dateSql, int nbJours){
		
		// déclaration d'un calendrier
		  GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier à la date courante;
		  gCal.setTime(dateSql);
		  gCal.add(Calendar.DATE, -nbJours);
		  
		  java.util.Date dateUtil = gCal.getTime();
		  java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());
		  
		return nouvelleDateSql;
	}
	
	public static java.sql.Date ajouteJours(java.sql.Date dateSql, int nbJours){
		
		// déclaration d'un calendrier
		  GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier à la date courante;
		  gCal.setTime(dateSql);
		  gCal.add(Calendar.DATE, nbJours);
		  
		  java.util.Date dateUtil = gCal.getTime();
		  java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());
		  
		return nouvelleDateSql;
	}
	
	public static java.sql.Date retrancheMois(java.sql.Date dateSql, int nbMois){
		
		// déclaration d'un calendrier
		  GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier à la date courante;
		  gCal.setTime(dateSql);
		  gCal.add(Calendar.MONTH, -nbMois);
		  
		  java.util.Date dateUtil = gCal.getTime();
		  java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());
		  
		return nouvelleDateSql;
	}
	
	public static java.sql.Date initialisationDebutMois(java.sql.Date dateSql){
		
		// déclaration d'un calendrier
		  GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier à la date courante;
		  gCal.setTime(dateSql);
		  gCal.set(gCal.get(Calendar.YEAR), gCal.get(Calendar.MONTH), gCal.getMinimum(Calendar.DAY_OF_MONTH), gCal.getMinimum(Calendar.HOUR_OF_DAY), gCal.getMinimum(Calendar.MINUTE));
		  java.util.Date dateUtil = gCal.getTime();
		  java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());
		  
		  return nouvelleDateSql;
	}
	
	public static java.sql.Date initialisationDebutJour(java.sql.Date dateSql){
		
		// déclaration d'un calendrier
		  GregorianCalendar gCal = new GregorianCalendar();
		// initialise le calendrier à la date courante;
		  gCal.setTime(dateSql);
		  gCal.set(gCal.get(Calendar.YEAR), gCal.get(Calendar.MONTH), gCal.get(Calendar.DATE), gCal.getMinimum(Calendar.HOUR_OF_DAY), gCal.getMinimum(Calendar.MINUTE));
		  java.util.Date dateUtil = gCal.getTime();
		  java.sql.Date nouvelleDateSql = new java.sql.Date(dateUtil.getTime());
		  
		  return nouvelleDateSql;
	}
		


}
