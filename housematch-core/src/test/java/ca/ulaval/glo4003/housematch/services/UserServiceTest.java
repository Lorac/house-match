package ca.ulaval.glo4003.housematch.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserServiceTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private UserRepository userRepositoryMock;
    private User userMock;
    private UserService userService;

    @Before
    public void init() {
        initMocks();
        userService = new UserService(userRepositoryMock);
    }

    private void initMocks() {
        userRepositoryMock = mock(UserRepository.class);
        userMock = mock(User.class);
    }

    @Test
    public void validateUserCredentialsMethodValidatesPasswordFromTheUserObject() {
        when(userRepositoryMock.getByUsername(anyString())).thenReturn(userMock);
        userService.validateUserCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userMock, times(1)).validatePassword(SAMPLE_PASSWORD);
    }

    @Test
    public void getUserByUsernameMethodRetrievesUserByUsernameFromRepository() {
        userService.getUserByUsername(SAMPLE_USERNAME);
        verify(userRepositoryMock, times(1)).getByUsername(SAMPLE_USERNAME);
    }

    @Test
    public void createUserMethodPersistsNewUserToRepository() {
        userService.createUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        verify(userRepositoryMock, times(1)).persist(any(User.class));
    }

    @Test
    public void getPubliclyRegistrableUserRolesMethodReturnsAListOfPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = userService.getPubliclyRegistrableUserRoles();
        assertFalse(userRoles.isEmpty());
        userRoles.stream().forEach(u -> assertTrue(u.isPubliclyRegistrable()));
    }
}
