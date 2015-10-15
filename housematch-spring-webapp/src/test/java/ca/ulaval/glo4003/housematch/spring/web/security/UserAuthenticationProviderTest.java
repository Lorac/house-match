package ca.ulaval.glo4003.housematch.spring.web.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;

public class UserAuthenticationProviderTest {

    private static final String SAMPLE_USERNAME = "name";
    private static final String SAMPLE_PASSWORD = "1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private Authentication authenticationMock;
    private UserService userServiceMock;
    private User userMock;

    private UserAuthenticationProvider userAuthenticationProvider;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        userAuthenticationProvider = new UserAuthenticationProvider(userServiceMock);
    }

    private void initMocks() {
        authenticationMock = mock(Authentication.class);
        userServiceMock = mock(UserService.class);
        userMock = mock(User.class);
    }

    private void stubMethods() throws Exception {
        when(authenticationMock.getName()).thenReturn(SAMPLE_USERNAME);
        when(authenticationMock.getCredentials()).thenReturn(SAMPLE_PASSWORD);
        when(userServiceMock.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD)).thenReturn(userMock);
        when(userMock.isActivated()).thenReturn(true);
        when(userMock.getRole()).thenReturn(SAMPLE_ROLE);
    }

    @Test
    public void authenticationProviderRetrievesUserByLoginCredentialsFromTheUserService() throws Exception {
        performAuthentication();
        verify(userServiceMock).getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticationProviderThrowsBadCredentialsExceptionOnUserServiceException() throws Exception {
        doThrow(new UserServiceException()).when(userServiceMock).getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        performAuthentication();
    }

    @Test
    public void authenticationProviderCreatesTheAuthenticationTokenFromTheSpecifiedCredentials() throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = performAuthentication();

        assertSame(userMock, (User) authenticationToken.getPrincipal());
        assertEquals(SAMPLE_PASSWORD, authenticationToken.getCredentials());
    }

    @Test
    public void authenticationProviderCreatesTheAuthenticationTokenUsingTheRolesOfTheUser() throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = performAuthentication();

        Stream<GrantedAuthority> grantedAuthorityStream = authenticationToken.getAuthorities().stream();
        assertTrue(grantedAuthorityStream.anyMatch(a -> a.getAuthority().equals(SAMPLE_ROLE.getDisplayName())));
    }

    @Test
    public void authenticationProviderCreatesTheAuthenticationTokenWithoutRolesWhenUserIsNotActivated() throws Exception {
        when(userMock.isActivated()).thenReturn(false);
        UsernamePasswordAuthenticationToken authenticationToken = performAuthentication();
        assertTrue(authenticationToken.getAuthorities().isEmpty());
    }

    private UsernamePasswordAuthenticationToken performAuthentication() {
        return (UsernamePasswordAuthenticationToken) userAuthenticationProvider.authenticate(authenticationMock);
    }
}
