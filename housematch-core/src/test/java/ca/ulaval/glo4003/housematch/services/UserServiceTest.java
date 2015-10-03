package ca.ulaval.glo4003.housematch.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
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

    @Test
    public void userRegistrationPersistsNewUserToRepository() throws Exception {
        userService.registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        verify(userRepositoryMock).persist(any(User.class));
    }

    @Test
    public void userRegistrationCallsTheUserCreationValidator() throws Exception {
        userService.registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        verify(userCreationValidatorMock).validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD,
                SAMPLE_ROLE);
    }

    @Test
    public void userRegistrationBeginsTheActivationProcess() throws Exception {
        userService.registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        verify(userActivationServiceMock).beginActivation(any(User.class));
    }

    @Test
    public void gettingPubliclyRegistrableUserRolesReturnsAListOfPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = userService.getPubliclyRegistrableUserRoles();
        assertFalse(userRoles.isEmpty());
        userRoles.stream().forEach(u -> assertTrue(u.isPubliclyRegistrable()));
    }
}
