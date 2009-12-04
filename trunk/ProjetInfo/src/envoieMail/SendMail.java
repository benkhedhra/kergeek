package envoieMail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMail {
 
	
	public static void sendMail(String message_dest,String message_objet,String message_corps) throws MessagingException{
		
 
		/** Objet session de JavaMail. */
		Session session;
		/** Objet message de JavaMail. */
		Message mesg;
	
		//		 Nous devons passer les informations au serveur de messagerie sous
		//		 forme de propriétés car JavaMail en comporte beaucoup...
		Properties props = new Properties();
 
		//		 Votre réseau doit donner au serveur SMTP local le nom "nom_du_serveur_smtp"
 
		props.put("mail.smtp.host", "smm-01.domensai.ecole");
		props.setProperty("mail.smtp.auth", "true");
 
		//		 Créer l’objet Session.
		Authenticator auth = new MyAuthentificator();//USERSMTP et PASSWDSMTP
		session = Session.getDefaultInstance(props, auth);
		//session.setDebug(true); //activer le mode verbeux !

		//try {
			//		 Créer un message.
			mesg = new MimeMessage(session);
	
			//		 Adresse From - Indiquer la provenance du message
			mesg.setFrom(new InternetAddress("id2927@ensai.fr"));
	
			//		 Adresse TO.
			InternetAddress toAddress = new InternetAddress(message_dest);
			mesg.addRecipient(Message.RecipientType.TO, toAddress);
	
			//		 Objet.
			mesg.setSubject(message_objet);
	
			//		 Corps du message.
			mesg.setText(message_corps);
	
			//		 Enfin, envoyer le message !
			Transport.send(mesg);
 
		//}
		/*catch (MessagingException ex) {
			while ((ex = (MessagingException)ex.getNextException()) != null) {
				ex.printStackTrace();
			}*/
		}
	
	
	public static void main(String[] args) throws MessagingException{
		sendMail("id2927@ensai.fr", "bonjour","bonjour");
	}
}
 
