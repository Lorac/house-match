package ca.ulaval.glo4003.housematch.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import ca.ulaval.glo4003.housematch.email.EmailServiceLocator.EmailService;

public class ValidationMail {

    public void sendValidationEmail(String username, String email) {
        EmailServiceLocator gmailProperties = new EmailServiceLocator(EmailService.GMAIL);
        Properties emailProperties = gmailProperties.getEmailProperties();
        Authenticator emailAuthenticator = gmailProperties.getEmailAuthenticator();
        EmailSender emailSender = new EmailSender(emailProperties, emailAuthenticator);
        try {
            emailSender.addRecipient(Message.RecipientType.TO, email);
            emailSender.setFrom(new InternetAddress("housematchb5@gmail.com"));
            emailSender.setSubject("Welcome to HouseMatch!");
            emailSender.setBody("Hello, " + username + ".\n\nPlease verify your email address "
                    + "by clicking the following link :\n" + " " + "\n\n\n\nCheers,\nThe HouseMatch team.");
            emailSender.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
