package ca.ulaval.glo4003.housematch.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaxMailSender {

    private static final String USERNAME = "mail.user";
    private static final String PASSWORD = "mail.password";
    private static final String HOST = "mail.smtp.host";
    private static final String PORT = "mail.smtp.port";
    private Properties emailProperties;
    private MimeMessage mimeMessage;

    public JavaxMailSender(final Properties emailProperties) {
        this.emailProperties = emailProperties;
        Session session = Session.getDefaultInstance(this.emailProperties);
        mimeMessage = new MimeMessage(session);
    }

    public void send() throws MessagingException {
        String port = emailProperties.get(PORT).toString();
        Transport transport = mimeMessage.getSession().getTransport("smtp");
        transport.connect(emailProperties.get(HOST).toString(), Integer.valueOf(port),
                emailProperties.get(USERNAME).toString(), emailProperties.get(PASSWORD).toString());
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
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
}
