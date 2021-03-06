package ca.ulaval.glo4003.housematch.email;

import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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
    public void sendingMessageAsyncSendsMessageWithTheCorrectRecipient() throws Exception {
        emailSender.sendAsync(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        verify(javaxMailSenderMock).addRecipient(MimeMessage.RecipientType.TO, SAMPLE_RECIPIENT_ADDRESS);
    }

    @Test
    public void sendingMessageAsyncSendsMessageWithTheCorrectSubject() throws Exception {
        emailSender.sendAsync(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        verify(javaxMailSenderMock).setSubject(SAMPLE_SUBJECT);
    }

    @Test
    public void sendingMessageSAsyncendsMessageWithTheCorrectContent() throws Exception {
        emailSender.sendAsync(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        verify(javaxMailSenderMock).setContent(SAMPLE_CONTENT);
    }

    @Test
    public void sendingMessageAsyncSendsTheMessage() throws Exception {
        emailSender.sendAsync(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
        Thread.sleep(10);
        verify(javaxMailSenderMock).send();
    }

    @Test(expected = MailSendException.class)
    public void sendingMessageThrowsMailSendExceptionOnMessagingException() throws Exception {
        doThrow(new MessagingException()).when(javaxMailSenderMock).setSubject(anyString());
        emailSender.sendAsync(SAMPLE_SUBJECT, SAMPLE_CONTENT, SAMPLE_RECIPIENT_ADDRESS);
    }
}
