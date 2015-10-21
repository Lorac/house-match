package ca.ulaval.glo4003.housematch.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private static final String ADDRESS_FROM = "noreply@housematch.glo4003.ulaval.ca";
    private JavaxMailSender javaxMailSender;

    public MailSender(final JavaxMailSender javaxMailSender) {
        this.javaxMailSender = javaxMailSender;
    }

    public void sendAsync(String subject, String content, String email) throws MailSendException {
        setupMailSender(subject, content, email);
        sendMailAsync();
    }

    private void setupMailSender(String subject, String content, String email) {
        try {
            javaxMailSender.addRecipient(MimeMessage.RecipientType.TO, email);
            javaxMailSender.setFrom(new InternetAddress(ADDRESS_FROM));
            javaxMailSender.setSubject(subject);
            javaxMailSender.setContent(content);
        } catch (MessagingException e) {
            throw new MailSendException("Could not setup the email.", e);
        }
    }

    private void sendMailAsync() {
        Runnable sendMailRunnable = () -> {
            try {
                javaxMailSender.send();
            } catch (MessagingException e) {
                throw new MailSendException("Could not send the email.", e);
            }
        };
        Thread sendMailThread = new Thread(sendMailRunnable);
        sendMailThread.start();
    }
}
