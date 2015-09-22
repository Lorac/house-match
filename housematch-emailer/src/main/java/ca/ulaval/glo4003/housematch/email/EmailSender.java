package ca.ulaval.glo4003.housematch.email;

import ca.ulaval.glo4003.housematch.domain.user.User;

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

    private final JavaxMailSender emailSender;

    public EmailSender(JavaxMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(String subject, String body, User user) throws CannotSendEmailException {
        try {
            emailSender.addRecipient(MimeMessage.RecipientType.TO, user.getEmail());
            emailSender.setFrom(new InternetAddress("housematchb5@gmail.com"));
            emailSender.setSubject(subject);
            emailSender.setBody(body);
            emailSender.send();
        } catch (MessagingException e) {
            throw new CannotSendEmailException("Couldn't send the email", e);
        }
    }
}
