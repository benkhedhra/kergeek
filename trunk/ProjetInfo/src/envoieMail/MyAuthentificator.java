package envoieMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthentificator extends Authenticator {
	 
	public PasswordAuthentication getAuthentification(){
		String login = "id2927";
		String mdp = "beatlesss";
	
		return new PasswordAuthentication(login, mdp);
	}
}
