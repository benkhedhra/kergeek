package envoieMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * La classe MyAuthenticator permet de générer les mot de passe et identifiant nécesssaires à l'authentification, lors de l'envoi d'un e-mail..
 * @see SendMail
 * @author KerGeek
 */ 

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
