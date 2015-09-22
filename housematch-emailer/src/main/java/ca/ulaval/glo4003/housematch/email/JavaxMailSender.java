package ca.ulaval.glo4003.housematch.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import ca.ulaval.glo4003.housematch.domain.user.User;

public class JavaxMailSender {

    private EmailSender emailSender;

    public JavaxMailSender(final EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(Message message, User user) throws CannotSendEmailException {
        try {
            emailSender.addRecipient(MimeMessage.RecipientType.TO, user.getEmail());
            emailSender.setFrom(new InternetAddress(user.getEmail()));
            emailSender.setSubject(message.getSubject());
            emailSender.setBody("Hello, " + user.getUsername() + ".\n\nPlease verify your email address "
                    + "by clicking the following link :\n" + " " + "\n\n\n\nCheers,\nThe HouseMatch team.");
            emailSender.send();
        } catch (MessagingException e) {
            throw new CannotSendEmailException("Couldn't send the email", e);
        }
    }
}
