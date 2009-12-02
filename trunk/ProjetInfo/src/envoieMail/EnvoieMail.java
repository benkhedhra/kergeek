package envoieMail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvoieMail {

	/*TODO
	 *Un truc que j'ai trouver sur internet pour envoyer des mails... a configurer, 
	 *il faudrait nous creer une adresse kergeek (sur hotmail par exemple..)
	 */
	public static void envoyerMail( String recipients, String subject, String message , String from) throws MessagingException
	{
		boolean debug = false;

		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");

		// create some properties and get the default Session
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		/*InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
		for (int i = 0; i < recipients.length; i++)
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);*/
		
		InternetAddress addressTo = new InternetAddress(recipients);
		msg.setRecipient(null, addressTo);


		// Optional : You can also set your custom headers in the Email if you Want
		msg.addHeader("MyHeaderName", "myHeaderValue");

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}
	
	public static void main(final String[] args) throws MessagingException {
		envoyerMail("kergeek@hotmail.fr", "test", "alors ca marche?", "berangerlueza@gmail.com");

	}

}
