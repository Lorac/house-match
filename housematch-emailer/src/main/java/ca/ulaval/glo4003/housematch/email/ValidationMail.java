package ca.ulaval.glo4003.housematch.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;

public class ValidationMail {
    protected static final String AUTH_USERNAME = "housematchb5@gmail.com";
    protected static final String AUTH_PASSWORD = "glo-4003";
    protected static final String LINK = "un lien";
    protected static final String PORT = "587";
    protected static final String HOST = "smtp.gmail.com";

    public void sendValidationEmail(String username, String email) {
        Properties emailProperties = new Properties();
        Authenticator emailAuthenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AUTH_USERNAME, AUTH_PASSWORD);
            }
        };
        EmailSender emailSender = new EmailSender(emailProperties, emailAuthenticator);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", HOST);
        emailProperties.put("mail.smtp.port", PORT);
        try {
            emailSender.addRecipient(Message.RecipientType.TO, email);
            emailSender.setFrom(new InternetAddress(AUTH_USERNAME));
            emailSender.setSubject("Welcome to HouseMatch!");
            emailSender.setBody("Hello, " + username + ".\n\nPlease verify your email address "
                    + "by clicking the following link :\n" + LINK + "\n\n\n\nCheers,\nThe HouseMatch team.");
            emailSender.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
