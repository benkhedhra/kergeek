package gestionBaseDeDonnees;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilitaireDate {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	
	public static Date dateCourante() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime(); //cal.getTime renvoie une Date 

	}
	
	public static String afficherDateCourante() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime()); //cal.getTime renvoie une Date 

	}
	
	public static Time heureCourante(){
		return new Time(System.currentTimeMillis());
	}
		


}
