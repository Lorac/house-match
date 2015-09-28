package ca.ulaval.glo4003.housematch.spring.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.spring.web.security.AccessControlList;

public class AccessControlListTest {

    private AccessControlList accessControlList;
    private User userMock;
    HashSet<UserRole> userRoles = new HashSet<UserRole>();

    @Before
    public void init() throws Exception {
        userMock = mock(User.class);
    }

    @Test
    public void userIsNotAuthorizedWhenAccessControlListIsEmpty() {
        initAccessControlList();
        when(userMock.hasRole(UserRole.ADMINISTRATOR)).thenReturn(true);

        assertFalse(accessControlList.isUserAuthorized(userMock));
    }

    @Test
    public void userForWhichRoleHasNotBeenAuthorizedIsNotAuthorized() {
        userRoles.add(UserRole.BUYER);
        initAccessControlList();
        when(userMock.hasRole(UserRole.ADMINISTRATOR)).thenReturn(false);

        assertFalse(accessControlList.isUserAuthorized(userMock));
    }

    @Test
    public void userForWhichRoleHasBeenAuthorizedIsAuthorized() {
        userRoles.add(UserRole.ADMINISTRATOR);
        initAccessControlList();
        when(userMock.hasRole(UserRole.ADMINISTRATOR)).thenReturn(true);

        assertTrue(accessControlList.isUserAuthorized(userMock));
    }

    private void initAccessControlList() {
        accessControlList = new AccessControlList(userRoles);
    }
}
