package ca.ulaval.glo4003.housematch.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;

public class ValidationMail {
    protected static final String AUTH_USERNAME = "test@gmail.com";
    protected static final String AUTH_PASSWORD = "password";
    protected static final String PORT = "587";
    protected static final String HOST = "smtp.gmail.com";

    public void sendValidationEmail() {
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", HOST);
        emailProperties.put("mail.smtp.port", PORT);
        Authenticator emailAuthenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AUTH_USERNAME, AUTH_PASSWORD);
            }
        };
        EmailSender emailSender = new EmailSender(emailProperties, emailAuthenticator);
        try {
            emailSender.addRecipient(Message.RecipientType.TO, "test@gmail.com");
            emailSender.setFrom(new InternetAddress("test@gmail.com"));
            emailSender.setSubject("User validation");
            emailSender.setBody("Click the following link to validate your email :");
            emailSender.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
