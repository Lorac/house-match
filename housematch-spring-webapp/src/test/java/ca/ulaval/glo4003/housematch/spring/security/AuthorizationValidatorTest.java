package ca.ulaval.glo4003.housematch.spring.security;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.spring.web.security.AccessControlList;
import ca.ulaval.glo4003.housematch.spring.web.security.AccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.AnonymousAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;

public class AuthorizationValidatorTest {

    private static final String USER_ATTRIBUTE_NAME = "user";
    private static final String SAMPLE_RESOURCE_NAME = "resource";
    private static final String ANOTHER_SAMPLE_RESOURCE_NAME = "resource2";

    private AccessControlList accessControlListMock;
    private HttpSession httpSessionMock;
    private User userMock;
    private AuthorizationValidator authorizationValidator;
    private Map<String, AccessControlList> resourceAccessListMap;

    @Before
    public void init() throws Exception {
        accessControlListMock = mock(AccessControlList.class);
        httpSessionMock = mock(HttpSession.class);
        userMock = mock(User.class);

        resourceAccessListMap = new HashMap<String, AccessControlList>();
    }

    @Test(expected = AnonymousAccessDeniedException.class)
    public void resourceAccessValidationThrowsAnonymousAccessDeniedExceptionWhenUserIsAnonymous() throws Exception {
        initAuthorizationValidator();
        when(httpSessionMock.getAttribute(USER_ATTRIBUTE_NAME)).thenReturn(null);
        authorizationValidator.validateResourceAccess(SAMPLE_RESOURCE_NAME, httpSessionMock, USER_ATTRIBUTE_NAME);
    }

    @Test(expected = AccessDeniedException.class)
    public void resourceAccessIsNotAuthorizedWhenResourceDoesNotExist() throws Exception {
        initAuthorizationValidator();

        when(userMock.isActivated()).thenReturn(true);
        when(httpSessionMock.getAttribute(USER_ATTRIBUTE_NAME)).thenReturn(userMock);

        authorizationValidator.validateResourceAccess(ANOTHER_SAMPLE_RESOURCE_NAME, httpSessionMock,
                USER_ATTRIBUTE_NAME);
    }

    @Test(expected = AnonymousAccessDeniedException.class)
    public void resourceAccessIsNotAuthorizedWhenUserIsNotActivated() throws Exception {
        initAuthorizationValidator();

        when(userMock.isActivated()).thenReturn(false);
        when(httpSessionMock.getAttribute(USER_ATTRIBUTE_NAME)).thenReturn(userMock);

        authorizationValidator.validateResourceAccess(SAMPLE_RESOURCE_NAME, httpSessionMock, USER_ATTRIBUTE_NAME);
    }

    @Test(expected = AccessDeniedException.class)
    public void resourceAccessIsNotAuthorizedWhenUserIsNotAuthorizedAccordingToTheAccessControlList() throws Exception {
        initAuthorizationValidator();

        when(userMock.isActivated()).thenReturn(true);
        when(httpSessionMock.getAttribute(USER_ATTRIBUTE_NAME)).thenReturn(userMock);
        when(accessControlListMock.isUserAuthorized(userMock)).thenReturn(false);

        authorizationValidator.validateResourceAccess(SAMPLE_RESOURCE_NAME, httpSessionMock, USER_ATTRIBUTE_NAME);
    }

    @Test
    public void resourceAccessIsAuthorizedWhenUserIsAuthorizedAccordingToTheAccessControlList() throws Exception {
        initAuthorizationValidator();

        when(userMock.isActivated()).thenReturn(true);
        when(httpSessionMock.getAttribute(USER_ATTRIBUTE_NAME)).thenReturn(userMock);
        when(accessControlListMock.isUserAuthorized(userMock)).thenReturn(true);

        try {
            authorizationValidator.validateResourceAccess(SAMPLE_RESOURCE_NAME, httpSessionMock, USER_ATTRIBUTE_NAME);
        } catch (Exception e) {
            fail();
        }
    }

    private void initAuthorizationValidator() {
        resourceAccessListMap.put(SAMPLE_RESOURCE_NAME, accessControlListMock);
        authorizationValidator = new AuthorizationValidator(resourceAccessListMap);
    }

}
