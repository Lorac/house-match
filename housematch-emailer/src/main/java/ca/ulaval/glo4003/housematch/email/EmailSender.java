package ca.ulaval.glo4003.housematch.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	private Properties emailProperties;
    private MimeMessage mimeMessage;

    public EmailSender(final Properties emailProperties) {
    	this.emailProperties = emailProperties;
        Session session = Session.getInstance(emailProperties, new MailAuthenticator());
        mimeMessage = new MimeMessage(session);
    }

    public void send() throws MessagingException {
        Transport.send(mimeMessage, mimeMessage.getAllRecipients());
    }

    public void addRecipient(Message.RecipientType recipientType, String email) throws MessagingException {
        mimeMessage.addRecipients(recipientType, email);
    }

    public void setFrom(InternetAddress from) throws MessagingException {
        mimeMessage.setFrom(from);
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessage.setSubject(subject);
    }

    public void setBody(String body) throws MessagingException {
        mimeMessage.setText(body);
    }
    
    private class MailAuthenticator extends Authenticator {

        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailProperties.get(USERNAME).toString(),
                    emailProperties.get(PASSWORD).toString());
        }
    }
}
