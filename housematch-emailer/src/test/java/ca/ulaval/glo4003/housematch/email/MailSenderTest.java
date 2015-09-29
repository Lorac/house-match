package ca.ulaval.glo4003.housematch.email;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;

public class MailSenderTest {

    private static final String SAMPLE_RECIPIENT_ADDRESS = "xyz@gmail.com";
    private static final String SAMPLE_SUBJECT = "subject";
    private static final String SAMPLE_CONTENT = "content";

    private MailSender emailSender;
    private JavaxMailSender javaxMailSenderMock;

    @Before
    public void init() throws Exception {
        javaxMailSenderMock = mock(JavaxMailSender.class);
        emailSender = new MailSender(javaxMailSenderMock);
    }

    @Test
    public void sendingMessageSetsTheCorrectRecipient() throws Exception {
        emailSender.send(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        verify(javaxMailSenderMock).addRecipient(MimeMessage.RecipientType.TO, SAMPLE_RECIPIENT_ADDRESS);
    }

    @Test
    public void sendingMessageSetsTheCorrectSubject() throws Exception {
        emailSender.send(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        verify(javaxMailSenderMock).setSubject(SAMPLE_SUBJECT);
    }

    @Test
    public void sendingMessageSetsTheCorrectContent() throws Exception {
        emailSender.send(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        verify(javaxMailSenderMock).setContent(SAMPLE_CONTENT);
    }

    @Test
    public void sendingMessageSendsTheMessage() throws Exception {
        emailSender.send(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        Thread.sleep(10);
        verify(javaxMailSenderMock).send();
    }

    @Test(expected = MailSendException.class)
    public void sendingMessageThrowsMailSendExceptionOnMessagingException() throws Exception {
        doThrow(new MessagingException()).when(javaxMailSenderMock).setSubject(anyString());
        emailSender.send(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
    }
}
