package envoieMail;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOCompte;

 
public class SendMail {
 
	
	public static void sendMail(String message_dest,String message_objet,String message_corps) throws MessagingException, UnsupportedEncodingException{
		
 
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
 
		//		 Créer l’objet Session.
		session = Session.getDefaultInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("id2927", "beatlesss");
            }
        });
		session.setDebug(true); //activer le mode verbeux !

			//		 Créer un message.
			mesg = new MimeMessage(session);
	
			//		 Adresse From - Indiquer la provenance du message
			mesg.setFrom(new InternetAddress("id2927@ensai.fr","Système Kergeek"));
	
			//		 Adresse TO.
			InternetAddress toAddress = new InternetAddress(message_dest);
			mesg.addRecipient(Message.RecipientType.TO, toAddress);
	
			//		 Objet.
			mesg.setSubject(message_objet);
	
			//		 Corps du message.
			mesg.setText(message_corps);
	
			//		 Enfin, envoyer le message !
			Transport.send(mesg);
 
	}
	
	
	public static void main(String[] args) throws MessagingException, UnsupportedEncodingException, SQLException, ClassNotFoundException, ConnexionFermeeException{
		sendMail(DAOCompte.getCompteById("u2").getAdresseEmail(),"test","bonjour");
	}
}
 
