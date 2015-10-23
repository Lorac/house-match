package ca.ulaval.glo4003.housematch.spring.web.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserPermissionEvaluatorTest {
    private static final Object SAMPLE_OBJECT = new Object();
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    UserPermissionEvaluator userPermisionEvaluator;

    AnonymousAuthenticationToken anonymousAuthenticationTokenMock;
    Authentication authenticationMock;
    User userMock;
    Serializable serializableMock;

    @Before
    public void init() {
        initMocks();
        userPermisionEvaluator = new UserPermissionEvaluator();
        stubMethods();
    }

    private void initMocks() {
        anonymousAuthenticationTokenMock = mock(AnonymousAuthenticationToken.class);
        serializableMock = mock(Serializable.class);
        userMock = mock(User.class);
        authenticationMock = mock(Authentication.class);
    }

    private void stubMethods() {
        when(authenticationMock.getPrincipal()).thenReturn(userMock);
        when(userMock.isActivated()).thenReturn(true);
        when(userMock.hasRole(SAMPLE_ROLE)).thenReturn(true);
    }

    @Test
    public void permissionIsDeniedWhenEvaluatingUsingUnsupportedPermissionEvaluationMethod() {
        boolean permissionGanted = userPermisionEvaluator.hasPermission(anonymousAuthenticationTokenMock, serializableMock, null, null);
        assertFalse(permissionGanted);
    }

    @Test
    public void permissionIsDeniedWhenAuthenticatedUserIsAnonymous() {
        boolean permissionGanted = userPermisionEvaluator.hasPermission(anonymousAuthenticationTokenMock, SAMPLE_OBJECT,
                SAMPLE_ROLE.name());
        assertFalse(permissionGanted);
    }

    @Test
    public void permissionIsDeniedWhenUserIsNotActivated() {
        when(userMock.isActivated()).thenReturn(false);
        boolean permissionGanted = userPermisionEvaluator.hasPermission(authenticationMock, SAMPLE_OBJECT, SAMPLE_ROLE.name());
        assertFalse(permissionGanted);
    }

    @Test
    public void permissionIsDeniedWhenUserDoesNotHaveTheRequiredRole() {
        when(userMock.hasRole(SAMPLE_ROLE)).thenReturn(false);
        boolean permissionGanted = userPermisionEvaluator.hasPermission(authenticationMock, SAMPLE_OBJECT, SAMPLE_ROLE.name());
        assertFalse(permissionGanted);
    }

    @Test
    public void permissionIsGrantedWhenUserHasTheRequiredCriterias() {
        boolean permissionGanted = userPermisionEvaluator.hasPermission(authenticationMock, SAMPLE_OBJECT, SAMPLE_ROLE.name());
        assertTrue(permissionGanted);
    }

}
