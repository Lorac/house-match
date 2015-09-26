package ca.ulaval.glo4003.housematch.email;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailSenderTest {

    private static final int SMTP_PORT = 597;

    private SimpleSmtpServer smtpServer;
    private JavaxMailSender javaxMailSender;

    @Mock
    private Properties propertiesMock;

    @Before
    public void init() {
        smtpServer = SimpleSmtpServer.start(SMTP_PORT);
        javaxMailSender = new JavaxMailSender(getMailProperties(SMTP_PORT));
    }

    @After
    public void tearDown() {
        smtpServer.stop();
    }

    @Test
    public void whenSendingAMessageItShouldSendTheMessage() throws MessagingException {
        sendMessage("sender@here.com", "Test", "Test Body", "receiver@there.com");

        assertEquals(1, smtpServer.getReceivedEmailSize());
        Iterator emailIter = smtpServer.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        assertTrue(email.getHeaderValue("Subject").equals("Test"));
        assertTrue(email.getBody().equals("Test Body"));
    }

    private Properties getMailProperties(int port) {
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.host", "localhost");
        mailProps.setProperty("mail.smtp.port", "" + port);
        mailProps.setProperty("mail.smtp.sendpartial", "true");
        mailProps.setProperty("mail.user", "true");
        mailProps.setProperty("mail.password", "true");
        return mailProps;
    }

    private void sendMessage(String from, String subject, String body, String to) throws MessagingException {
        createMessage(from, to, subject, body);
        javaxMailSender.send();
    }

    private void createMessage(String from, String to, String subject, String content) throws MessagingException {
        javaxMailSender.setFrom(new InternetAddress(from));
        javaxMailSender.setSubject(subject);
        javaxMailSender.setContent(content);
        javaxMailSender.addRecipient(MimeMessage.RecipientType.TO, to);
    }
}