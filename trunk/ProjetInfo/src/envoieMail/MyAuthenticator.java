package envoieMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {

	private final String LOGIN = "//DOMENSAI/id3033";
	//TODO "DOMENSAI/id3033"
	private final String MOT_DE_PASSE = "bibliotheque";

	public PasswordAuthentication getPasswordAuthentication(){

		String nom = LOGIN;
		String mdp = MOT_DE_PASSE;
		return new PasswordAuthentication(nom, mdp);
	}

}
