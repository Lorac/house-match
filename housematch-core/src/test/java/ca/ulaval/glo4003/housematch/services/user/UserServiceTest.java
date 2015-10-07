package ca.ulaval.glo4003.housematch.services.user;

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

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;

public class UserServiceTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private UserRepository userRepositoryMock;
    private UserRegistrationValidator userRegistrationValidatorMock;
    private UserActivationService userActivationServiceMock;
    private AddressValidator addressValidatorMock;
    private Address addressMock;

    private User userMock;

    private UserService userService;

    @Before
    public void init() throws Exception {
        initMocks();
        userService = new UserService(userRepositoryMock, userRegistrationValidatorMock, userActivationServiceMock,
                addressValidatorMock);
    }

    private void initMocks() throws UserNotFoundException {
        userRepositoryMock = mock(UserRepository.class);
        userMock = mock(User.class);
        userActivationServiceMock = mock(UserActivationService.class);
        userRegistrationValidatorMock = mock(UserRegistrationValidator.class);
        addressValidatorMock = mock(AddressValidator.class);
        addressMock = mock(Address.class);
        when(userRepositoryMock.getByUsername(SAMPLE_USERNAME)).thenReturn(userMock);
    }

    @Test
    public void gettingUserByLoginCredentialsValidatesPasswordFromTheUserObject() throws Exception {
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userMock).validatePassword(SAMPLE_PASSWORD);
    }

    @Test
    public void gettingUserByLoginCredentialsRetrievesUserByUsernameFromRepository() throws Exception {
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userRepositoryMock).getByUsername(SAMPLE_USERNAME);
    }

    @Test
    public void gettingUserByLoginCredentialsReturnsTheUser() throws Exception {
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
        verify(userRegistrationValidatorMock).validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD,
                SAMPLE_ROLE);
    }

    @Test
    public void userRegistrationBeginsTheActivationProcess() throws Exception {
        registerUser();
        verify(userActivationServiceMock).beginActivation(any(User.class));
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserRegistrationValidationException() throws Exception {
        doThrow(new UserRegistrationValidationException()).when(userRegistrationValidatorMock)
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
    public void updatingUserEmailUpdatesTheEmailFromTheUserObject() throws Exception {
        userService.updateUserEmail(userMock, SAMPLE_EMAIL);
        verify(userMock).updateEmail(SAMPLE_EMAIL);
    }

    @Test
    public void updatingUserEmailBeginsTheUserActivationProcess() throws Exception {
        userService.updateUserEmail(userMock, SAMPLE_EMAIL);
        verify(userActivationServiceMock).beginActivation(userMock);
    }

    @Test
    public void updatingUserEmailPushesUserUpdateToRepository() throws Exception {
        userService.updateUserEmail(userMock, SAMPLE_EMAIL);
        verify(userRepositoryMock).update(userMock);
    }

    @Test
    public void updatingUserCoordinatesValidatesAddress() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(addressValidatorMock).validateAddress(addressMock);
    }

    @Test
    public void updatingUserCoordinatesSetsNewAddress() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userMock).setAddress(addressMock);
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
