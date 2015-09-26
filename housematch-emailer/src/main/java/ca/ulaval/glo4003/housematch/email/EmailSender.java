package ca.ulaval.glo4003.housematch.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private final String ADDRESS_FROM = "housematchb5@gmail.com";
    private JavaxMailSender emailSender;

    public EmailSender(final JavaxMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(String subject, String content, String email) throws SendEmailException {
        try {
            emailSender.addRecipient(MimeMessage.RecipientType.TO, email);
            emailSender.setFrom(new InternetAddress(ADDRESS_FROM));
            emailSender.setSubject(subject);
            emailSender.setContent(content);
            emailSender.send();
        } catch (MessagingException e) {
            throw new SendEmailException("Couldn't send the email", e);
        }
    }
}
