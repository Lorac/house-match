package ca.ulaval.glo4003.housematch.email;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaxMailSender {

    private final Properties emailProperties;
    private MimeMessage mimeMessage;

    public JavaxMailSender(final Properties emailProperties) {
        this.emailProperties = emailProperties;
        Session session = Session.getInstance(this.emailProperties, new MailAuthenticator());
        mimeMessage = new MimeMessage(session);
    }

    public void send() throws MessagingException {
        Transport.send(mimeMessage);
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

    public void setContent(String content) throws MessagingException {
        mimeMessage.setContent(content, "text/html; charset=utf-8");
    }

    private class MailAuthenticator extends Authenticator {

        private static final String USERNAME_PROPERTY_NAME = "mail.smtp.user";
        private static final String PASSWORD_PROPERTY_NAME = "mail.password";

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailProperties.get(USERNAME_PROPERTY_NAME).toString(),
                    emailProperties.get(PASSWORD_PROPERTY_NAME).toString());
        }
    }
}
