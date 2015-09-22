package ca.ulaval.glo4003.housematch.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private JavaxMailSender emailSender;

    public EmailSender(final JavaxMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(String subject, String body, String email) throws CannotSendEmailException {
        try {
            emailSender.addRecipient(MimeMessage.RecipientType.TO, email);
            emailSender.setFrom(new InternetAddress("housematchb5@gmail.com"));
            emailSender.setSubject(subject);
            emailSender.setBody(body);
            emailSender.send();
        } catch (MessagingException e) {
            throw new CannotSendEmailException("Couldn't send the email", e);
        }
    }
}
