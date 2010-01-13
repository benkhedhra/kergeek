package envoieMail;



import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * La classe SendMail permet d'envoyer un mail.
 * @see MyAuthenticator
 * @author KerGeek
 */ 

//La classe SendMail ne permet d'envoyer des messages que à l'intérieur du réseau ensai, 
//et depuis l'école uniquement.

public class SendMail {

	/**
	 * Envoi d'un e-mail à partir d'un {@link SendMail#message_dest}, d'un {@link SendMail#message_objet} et d'un {@link SendMail#message_corps} .
	 * @param message_dest
	 * @param message_objet
	 * @param message_corps
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws SendFailedException
	 */

	public static void sendMail(String message_dest,String message_objet,String message_corps) throws SendFailedException, MessagingException, UnsupportedEncodingException{

		/** Objet session de JavaMail. */
		Session session;
		/** Objet message de JavaMail. */
		Message mesg;

		//		 Nous devons passer les informations au serveur de messagerie sous
		//		 forme de propriétés car JavaMail en comporte beaucoup...
		Properties props = new Properties();

		//		 Notre réseau doit donner au serveur SMTP local le nom "smm-01.domensai.ecole"

		props.put("mail.smtp.host", "smm-01.domensai.ecole");
		props.setProperty("mail.smtp.auth", "false");
		/*
		 * props.setProperty("mail.smtp.auth", "true");
		 */

		//		 Créer l’objet Session.
		session = Session.getInstance(props);
		session.setDebug(false); //activer le mode verbeux !

		//		 Créer un message.
		mesg = new MimeMessage(session);

		//		 Adresse From - Indiquer la provenance du message
		mesg.setFrom(new InternetAddress("id3033@ensai.fr","Système Kergeek"));

		//		 Adresse TO.
		InternetAddress toAddress = new InternetAddress(message_dest);
		mesg.addRecipient(Message.RecipientType.TO, toAddress);

		//		 Objet.
		mesg.setSubject(message_objet);

		//		 Corps du message.
		mesg.setText(message_corps);

		//		 Enfin, envoyer le message !

		System.out.println("Début de la tentative d'envoi d'un e-mail à l'adresse "+message_dest+"...");
		try{
		Transport.send(mesg);

		/*Si l'authentification fonctionnait :
		 * Transport tr = session.getTransport("smtp");
		 * tr.connect("smm-01.domensai.ecole", "domensai\\id3033\\id3033@ensai.fr", "bibliotheque");
		 * mesg.saveChanges();
		 * tr.sendMessage(mesg, mesg.getAllRecipients());
		 * tr.close();*/
		}
		catch (SendFailedException e){
			System.out.println("L'envoi d'un e-mail à l'adresse "+message_dest+" n'a pas fonctionné.");
			throw new SendFailedException(e.getMessage());
		}
		System.out.println("envoi éffectué");

	}

}

