package ca.ulaval.glo4003.housematch.services.user;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class UserActivationServiceTest {
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final String SAMPLE_EMAIL = "test@test.com";

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
        when(userRepositoryMock.getByActivationCode(SAMPLE_ACTIVATION_CODE)).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
    }

    @Test
    public void beginningTheActivationSetsTheUserActivationCode() throws Exception {
        userActivationService.beginActivation(userMock);
        verify(userMock).setActivationCode(any(UUID.class));
    }

    @Test
    public void beginningTheActivationSendsTheActivationLink() throws Exception {
        userActivationService.beginActivation(userMock);
        verify(emailSenderMock).sendAsync(anyString(), anyString(), eq(SAMPLE_EMAIL));
    }

    @Test(expected = UserActivationServiceException.class)
    public void beginningTheActivationThrowsUserActivationServiceExceptionOnMailSendException() throws Exception {
        doThrow(new MailSendException()).when(emailSenderMock).sendAsync(anyString(), anyString(), eq(SAMPLE_EMAIL));
        userActivationService.beginActivation(userMock);
    }

    @Test
    public void completingActivationActivatesUserFromTheSpecifiedActivationCode() throws Exception {
        userActivationService.completeActivation(SAMPLE_ACTIVATION_CODE);
        verify(userRepositoryMock).getByActivationCode(SAMPLE_ACTIVATION_CODE);
    }

    @Test
    public void completingActivationPushesUserUpdateToRepository() throws Exception {
        userActivationService.completeActivation(SAMPLE_ACTIVATION_CODE);
        verify(userRepositoryMock).update(userMock);
    }

    @Test(expected = UserActivationServiceException.class)
    public void completingActivationThrowsUserActivationServiceExceptionOnUserNotFoundException() throws Exception {
        doThrow(new UserNotFoundException()).when(userRepositoryMock).getByActivationCode(SAMPLE_ACTIVATION_CODE);
        userActivationService.completeActivation(SAMPLE_ACTIVATION_CODE);
    }
}