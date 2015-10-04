package ca.ulaval.glo4003.housematch.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.UserRegistrationValidator;

public class UserServiceTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private UserRepository userRepositoryMock;
    private UserRegistrationValidator userCreationValidatorMock;
    private UserActivationService userActivationServiceMock;
    private User userMock;

    private UserService userService;

    @Before
    public void init() throws Exception {
        initMocks();
        userService = new UserService(userRepositoryMock, userCreationValidatorMock, userActivationServiceMock);
    }

    private void initMocks() throws Exception {
        stubMethods();
    }

    private void stubMethods() {
        userRepositoryMock = mock(UserRepository.class);
        userMock = mock(User.class);
        userActivationServiceMock = mock(UserActivationService.class);
        userCreationValidatorMock = mock(UserRegistrationValidator.class);
    }

    @Test
    public void gettingUserByLoginCredentialsValidatesPasswordFromTheUserObject() throws Exception {
        when(userRepositoryMock.getByUsername(SAMPLE_USERNAME)).thenReturn(userMock);
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userMock).validatePassword(SAMPLE_PASSWORD);
    }

    @Test
    public void gettingUserByLoginCredentialsRetrievesUserByUsernameFromRepository() throws Exception {
        when(userRepositoryMock.getByUsername(SAMPLE_USERNAME)).thenReturn(userMock);
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userRepositoryMock).getByUsername(SAMPLE_USERNAME);
    }

    @Test
    public void gettingUserByLoginCredentialsReturnsTheUser() throws Exception {
        when(userRepositoryMock.getByUsername(SAMPLE_USERNAME)).thenReturn(userMock);
        User user = userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        assertSame(userMock, user);
    }

    @Test(expected = UserServiceException.class)
    public void gettingUserByLoginCredentialsThrowsUserServiceExceptionOnUserNotFoundException() throws Exception {
        doThrow(new UserNotFoundException()).when(userRepositoryMock).getByUsername(SAMPLE_USERNAME);
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
    }

    @Test(expected = UserServiceException.class)
    public void gettingUserByLoginCredentialsThrowsUserServiceExceptionOnInvalidPasswordException() throws Exception {
        when(userRepositoryMock.getByUsername(SAMPLE_USERNAME)).thenReturn(userMock);
        doThrow(new InvalidPasswordException()).when(userMock).validatePassword(SAMPLE_PASSWORD);

        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
    }

    @Test
    public void userRegistrationPersistsNewUserToRepository() throws Exception {
        registerUser();
        verify(userRepositoryMock).persist(any(User.class));
    }

    @Test
    public void userRegistrationCallsTheUserCreationValidator() throws Exception {
        registerUser();
        verify(userCreationValidatorMock).validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD,
                SAMPLE_ROLE);
    }

    @Test
    public void userRegistrationBeginsTheActivationProcess() throws Exception {
        registerUser();
        verify(userActivationServiceMock).beginActivation(any(User.class));
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserRegistrationValidationException() throws Exception {
        doThrow(new UserRegistrationValidationException()).when(userCreationValidatorMock)
                .validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        registerUser();
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserAlreadyExistsException() throws Exception {
        doThrow(new UserAlreadyExistsException()).when(userRepositoryMock).persist(any(User.class));
        registerUser();
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserActivationServiceException() throws Exception {
        doThrow(new UserActivationServiceException()).when(userActivationServiceMock).beginActivation(any(User.class));
        registerUser();
    }

    @Test
    public void gettingPubliclyRegistrableUserRolesReturnsAListOfPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = userService.getPubliclyRegistrableUserRoles();
        assertFalse(userRoles.isEmpty());
        userRoles.stream().forEach(u -> assertTrue(u.isPubliclyRegistrable()));
    }

    private void registerUser() throws UserServiceException {
        userService.registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }
}
