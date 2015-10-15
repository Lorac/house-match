package ca.ulaval.glo4003.housematch.services.user;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_INVALID_EMAIL = "asd@asd<!";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;
    private static final int SAMPLE_HASHCODE = new Object().hashCode();

    private UserRepository userRepositoryMock;
    private UserRegistrationValidator userRegistrationValidatorMock;
    private UserActivationService userActivationServiceMock;
    private AddressValidator addressValidatorMock;
    private Address addressMock;

    private User userMock;
    private Property propertyMock;

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
        propertyMock = mock(Property.class);
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

    @Test(expected = UserServiceException.class)
    public void updatingUserEmailUsingInvalidEmaiThrowsUserServiceExceptionl() throws Exception {
        userService.updateUserEmail(userMock, SAMPLE_INVALID_EMAIL);
    }

    @Test
    public void updatingUserEmailWithTheSameEmailDoesNotBeginTheUserActivationProcess() throws Exception {
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
        userService.updateUserEmail(userMock, SAMPLE_EMAIL);
        verify(userActivationServiceMock, never()).beginActivation(userMock);
    }

    @Test
    public void updatingUserContactInformationsPushesUserUpdateToRepository() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userRepositoryMock).update(userMock);
    }

    @Test
    public void updatingUserContactInformationsValidatesTheAddress() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(addressValidatorMock).validateAddress(addressMock);
    }

    @Test
    public void updatingUserContactInformationsSetsTheNewAddressInTheUserObject() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userMock).setAddress(addressMock);
    }

    @Test(expected = UserServiceException.class)
    public void updatingUserContactInformationsThrowsUserServiceExceptionOnUserActivationServiceException()
            throws Exception {
        doThrow(new UserActivationServiceException()).when(userActivationServiceMock).beginActivation(userMock);
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
    }

    @Test(expected = UserServiceException.class)
    public void updatingUserContactInformationsThrowsUserServiceExceptionOnAddressValidationException()
            throws Exception {
        doThrow(new AddressValidationException()).when(addressValidatorMock).validateAddress(addressMock);
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
    }

    @Test
    public void gettingPropertyByHashCodeReturnsThePropertyFromTheSpecifiedHashCode() throws Exception {
        when(userMock.getPropertyByHashCode(SAMPLE_HASHCODE)).thenReturn(propertyMock);
        Property returnedProperty = userService.getPropertyByHashCode(userMock, SAMPLE_HASHCODE);
        assertSame(propertyMock, returnedProperty);
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
