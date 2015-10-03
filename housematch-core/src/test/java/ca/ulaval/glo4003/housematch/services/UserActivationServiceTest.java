package ca.ulaval.glo4003.housematch.services;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class UserActivationServiceTest {
    private static final Integer SAMPLE_ACTVIATION_CODE = 34234213;
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String ANOTHER_SAMPLE_EMAIL = "test2@test.com";

    private UserRepository userRepositoryMock;
    private MailSender emailSenderMock;
    private User userMock;

    private UserActivationService userActivationService;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        userActivationService = new UserActivationService(emailSenderMock, userRepositoryMock);
    }

    private void initMocks() throws Exception {
        userRepositoryMock = mock(UserRepository.class);
        userMock = mock(User.class);
        emailSenderMock = mock(MailSender.class);
    }

    private void stubMethods() throws UserNotFoundException {
        when(userRepositoryMock.getByActivationCode(SAMPLE_ACTVIATION_CODE)).thenReturn(userMock);
    }

    @Test
    public void activationValidationValidatesUserActivationFromTheUserObject() throws Exception {
        userActivationService.validateActivation(userMock);
        verify(userMock).validateActivation();
    }

    @Test
    public void updatingActivationEmailUpdatesTheEmailFromOfTheUserObject() throws Exception {
        userActivationService.updateActivationEmail(userMock, ANOTHER_SAMPLE_EMAIL);
        verify(userMock).setEmail(eq(ANOTHER_SAMPLE_EMAIL));
    }

    @Test
    public void updatingActivationEmailSendsTheActivationLink() {
        when(userMock.getEmail()).thenReturn(ANOTHER_SAMPLE_EMAIL);
        userActivationService.updateActivationEmail(userMock, ANOTHER_SAMPLE_EMAIL);
        verify(emailSenderMock).send(anyString(), anyString(), eq(ANOTHER_SAMPLE_EMAIL));
    }

    @Test
    public void beginningTheActivationSetsTheUserActivationCode() throws Exception {
        userActivationService.beginActivation(userMock);
        verify(userMock).setActivationCode(anyInt());
    }

    @Test
    public void beginningTheActivationSendsTheActivationLink() throws Exception {
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
        userActivationService.beginActivation(userMock);
        verify(emailSenderMock).send(anyString(), anyString(), eq(SAMPLE_EMAIL));
    }

    @Test
    public void activationCompletionActivatesUserFromTheSpecifiedActivationCode() throws Exception {
        userActivationService.completeActivation(SAMPLE_ACTVIATION_CODE);
        verify(userRepositoryMock).getByActivationCode(SAMPLE_ACTVIATION_CODE);
    }
}
