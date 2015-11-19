package ca.ulaval.glo4003.housematch.web.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.web.security.UserPermissionEvaluator;
import ca.ulaval.glo4003.housematch.web.security.accessvalidators.PropertyAccessValidator;

public class UserPermissionEvaluatorTest {
    private static final Object SAMPLE_OBJECT = new Object();
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    UserPermissionEvaluator userPermissionEvaluator;

    AnonymousAuthenticationToken anonymousAuthenticationTokenMock;
    Authentication authenticationMock;
    PropertyAccessValidator propertyAccessValidatorMock;
    Property propertyMock;
    User userMock;
    Serializable serializableMock;

    @Before
    public void init() {
        initMocks();
        userPermissionEvaluator = new UserPermissionEvaluator(propertyAccessValidatorMock);
        initStubs();
    }

    private void initMocks() {
        anonymousAuthenticationTokenMock = mock(AnonymousAuthenticationToken.class);
        serializableMock = mock(Serializable.class);
        propertyAccessValidatorMock = mock(PropertyAccessValidator.class);
        propertyMock = mock(Property.class);
        userMock = mock(User.class);
        authenticationMock = mock(Authentication.class);
    }

    private void initStubs() {
        when(authenticationMock.getPrincipal()).thenReturn(userMock);
        when(userMock.isActivated()).thenReturn(true);
        when(userMock.hasRole(SAMPLE_ROLE)).thenReturn(true);
    }

    @Test
    public void generalPermissionIsDeniedWhenEvaluatingUsingUnsupportedPermissionEvaluationMethod() {
        boolean permissionGanted = userPermissionEvaluator.hasPermission(anonymousAuthenticationTokenMock, serializableMock, null, null);
        assertFalse(permissionGanted);
    }

    @Test
    public void generalPermissionIsDeniedWhenAuthenticatedUserIsAnonymous() {
        boolean permissionGanted = userPermissionEvaluator.hasPermission(anonymousAuthenticationTokenMock, null, SAMPLE_ROLE.name());
        assertFalse(permissionGanted);
    }

    @Test
    public void generalPermissionIsDeniedWhenUserIsNotActivated() {
        when(userMock.isActivated()).thenReturn(false);
        boolean permissionGanted = userPermissionEvaluator.hasPermission(authenticationMock, null, SAMPLE_ROLE.name());
        assertFalse(permissionGanted);
    }

    @Test
    public void generalPermissionIsDeniedWhenUserDoesNotHaveTheRequiredRole() {
        when(userMock.hasRole(SAMPLE_ROLE)).thenReturn(false);
        boolean permissionGanted = userPermissionEvaluator.hasPermission(authenticationMock, null, SAMPLE_ROLE.name());
        assertFalse(permissionGanted);
    }

    @Test
    public void generalPermissionIsGrantedWhenUserMeetsTheAuthorizationRequirements() {
        boolean permissionGanted = userPermissionEvaluator.hasPermission(authenticationMock, null, SAMPLE_ROLE.name());
        assertTrue(permissionGanted);
    }

    @Test
    public void generalPermissionIsDeniedWhenUserDoesNotHaveAccessToTheSpecifiedTargetDomainObject() {
        when(propertyAccessValidatorMock.validateAccess(authenticationMock, propertyMock)).thenReturn(false);
        boolean permissionGanted = userPermissionEvaluator.hasPermission(authenticationMock, propertyMock, null);
        assertFalse(permissionGanted);
    }

    @Test
    public void generalPermissionIsDeniedWhenSecurityForTargetDomainObjectIsNotImplemented() {
        boolean permissionGanted = userPermissionEvaluator.hasPermission(authenticationMock, SAMPLE_OBJECT, null);
        assertFalse(permissionGanted);
    }

    @Test
    public void generalPermissionIsGrantedWhenUserHasAccessToTheSpecifiedTargetDomainObject() {
        when(propertyAccessValidatorMock.validateAccess(authenticationMock, propertyMock)).thenReturn(true);
        boolean permissionGanted = userPermissionEvaluator.hasPermission(authenticationMock, propertyMock, null);
        assertTrue(permissionGanted);
    }

}
