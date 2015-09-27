package ca.ulaval.glo4003.housematch.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String ADDRESS_FROM = "housematchb5@gmail.com";
    private JavaxMailSender javaxMailSender;

    public EmailSender(final JavaxMailSender javaxMailSender) {
        this.javaxMailSender = javaxMailSender;
    }

    public void send(String subject, String content, String email) throws MailSendException {

        try {
            javaxMailSender.addRecipient(MimeMessage.RecipientType.TO, email);
            javaxMailSender.setFrom(new InternetAddress(ADDRESS_FROM));
            javaxMailSender.setSubject(subject);
            javaxMailSender.setContent(content);
        } catch (MessagingException e) {
            throw new MailSendException("Couldn't send the email", e);
        }

        sendMail();
    }

    private void sendMail() {
        Runnable sendMailRunnable = () -> {
            try {
                javaxMailSender.send();
            } catch (MessagingException e) {
                throw new MailSendException("Couldn't send the email", e);
            }
        };
        Thread sendMailThread = new Thread(sendMailRunnable);
        sendMailThread.start();
    }
}
