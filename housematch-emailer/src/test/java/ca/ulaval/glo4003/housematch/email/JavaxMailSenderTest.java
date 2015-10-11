package ca.ulaval.glo4003.housematch.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailSenderTest {

    private static final String SMTP_HOST_PROPERTY_NAME = "mail.smtp.host";
    private static final String SMTP_HOST_PROPERTY_VALUE = "localhost";
    private static final String SMTP_PORT_PROPERTY_NAME = "mail.smtp.port";
    private static final int SMTP_PORT_VALUE = 5597;
    private static final String SMTP_SENDPARTIAL_PROPERTY_NAME = "mail.smtp.sendpartial";
    private static final String SMTP_SENDPARTIAL_PROPERTY_VALUE = "true";
    private static final String SMTP_USER_PROPERTY_NAME = "mail.user";
    private static final String SAMPLE_SMTP_USER_PROPERTY_VALUE = "user";
    private static final String SMTP_PASSWORD_PROPERTY_NAME = "mail.password";
    private static final String SAMPLE_SMTP_PASSWORD_PROPERTY_VALUE = "password";
    private static final String SUBJECT_HEADER_PROPERTY_NAME = "Subject";

    private static final String SAMPLE_SENDER_ADDRESS = "abc@gmail.com";
    private static final String SAMPLE_RECIPIENT_ADDRESS = "xyz@gmail.com";
    private static final String SAMPLE_SUBJECT = "subject";
    private static final String SAMPLE_CONTENT = "content";

    private SimpleSmtpServer smtpServer;
    private JavaxMailSender javaxMailSender;

    @Mock
    private Properties propertiesMock;

    @Before
    public void init() {
        smtpServer = SimpleSmtpServer.start(SMTP_PORT_VALUE);
        javaxMailSender = new JavaxMailSender(getMailProperties(SMTP_PORT_VALUE));
    }

    private Properties getMailProperties(int port) {
        Properties mailProperties = new Properties();
        mailProperties.setProperty(SMTP_HOST_PROPERTY_NAME, SMTP_HOST_PROPERTY_VALUE);
        mailProperties.setProperty(SMTP_PORT_PROPERTY_NAME, String.valueOf(port));
        mailProperties.setProperty(SMTP_SENDPARTIAL_PROPERTY_NAME, SMTP_SENDPARTIAL_PROPERTY_VALUE);
        mailProperties.setProperty(SMTP_USER_PROPERTY_NAME, SAMPLE_SMTP_USER_PROPERTY_VALUE);
        mailProperties.setProperty(SMTP_PASSWORD_PROPERTY_NAME, SAMPLE_SMTP_PASSWORD_PROPERTY_VALUE);
        return mailProperties;
    }

    @After
    public void tearDown() {
        smtpServer.stop();
    }

    @Test
    public void sendingMessageShouldSendTheMessage() throws MessagingException {
        sendMessage(SAMPLE_SENDER_ADDRESS, SAMPLE_RECIPIENT_ADDRESS, SAMPLE_SUBJECT, SAMPLE_CONTENT);
        assertEquals(1, smtpServer.getReceivedEmailSize());
    }

    @Test
    public void sendingMessageShouldSendTheMessageWithTheCorrectSubjectAndContent() throws MessagingException {
        sendMessage(SAMPLE_SENDER_ADDRESS, SAMPLE_RECIPIENT_ADDRESS, SAMPLE_SUBJECT, SAMPLE_CONTENT);

        SmtpMessage message = getReceivedMessage();
        assertTrue(message.getHeaderValue(SUBJECT_HEADER_PROPERTY_NAME).equals(SAMPLE_SUBJECT));
        assertTrue(message.getBody().equals(SAMPLE_CONTENT));
    }

    @SuppressWarnings("unchecked")
    private SmtpMessage getReceivedMessage() {
        Iterator<SmtpMessage> messageIterator = smtpServer.getReceivedEmail();
        SmtpMessage message = (SmtpMessage) messageIterator.next();
        return message;
    }

    private void sendMessage(String from, String recipient, String subject, String content) throws MessagingException {
        createMessage(from, recipient, subject, content);
        javaxMailSender.send();
    }

    private void createMessage(String from, String recipient, String subject, String content)
            throws MessagingException {
        javaxMailSender.setFrom(new InternetAddress(from));
        javaxMailSender.setSubject(subject);
        javaxMailSender.setContent(content);
        javaxMailSender.addRecipient(MimeMessage.RecipientType.TO, recipient);
    }
}