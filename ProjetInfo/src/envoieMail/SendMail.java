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

 
public class SendMail {
 
	
	public static void sendMail(String message_dest,String message_objet,String message_corps) throws MessagingException, UnsupportedEncodingException{
		
		try{
		/** Objet session de JavaMail. */
		Session session;
		/** Objet message de JavaMail. */
		Message mesg;
	
		//		 Nous devons passer les informations au serveur de messagerie sous
		//		 forme de propriÈtÈs car JavaMail en comporte beaucoup...
		Properties props = new Properties();
 
		//		 Notre rÈseau doit donner au serveur SMTP local le nom "smm-01.domensai.ecole"
 
		props.put("mail.smtp.host", "smm-01.domensai.ecole");
		props.setProperty("mail.smtp.auth", "true");
 
		//		 CrÈer líobjet Session.
		//Authenticator auth = new MyAuthenticator();//USERSMTP et PASSWDSMTP
		session = Session.getDefaultInstance(props);
		session.setDebug(true); //activer le mode verbeux !

			//		 CrÈer un message.
			mesg = new MimeMessage(session);
	
			//		 Adresse From - Indiquer la provenance du message
			mesg.setFrom(new InternetAddress("id3033@ensai.fr","SystËme Kergeek"));
	
			//		 Adresse TO.
			InternetAddress toAddress = new InternetAddress(message_dest);
			mesg.addRecipient(Message.RecipientType.TO, toAddress);
	
			//		 Objet.
			mesg.setSubject(message_objet);
	
			//		 Corps du message.
			mesg.setText(message_corps);
	
			
			//		 Enfin, envoyer le message !
			Transport tr = session.getTransport("smtp");
			tr.connect("smm-01.domensai.ecole", "//DOMENSAI/id3033", "bibliotheque");
			//TODO "DOMENSAI/id3033"
			mesg.saveChanges();
			tr.sendMessage(mesg, mesg.getAllRecipients());
			tr.close();
		}
		catch (SendFailedException e){
			System.out.println("commande : "+e.getNextException().getMessage());
			System.out.println("erreur smtp" + e.getMessage());
		}
		
		//TODO Si ça ne marche pas, on revient à la révision 388 qui fonctionnait
 
	}

}
 
