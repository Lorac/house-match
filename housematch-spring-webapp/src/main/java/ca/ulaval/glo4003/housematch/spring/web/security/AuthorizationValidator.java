package ca.ulaval.glo4003.housematch.spring.web.security;

import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class AuthorizationValidator {

    private Map<String, AccessControlList> resourceAccessListMap;

    public AuthorizationValidator(final Map<String, AccessControlList> resourceAccessListMap) {
        this.resourceAccessListMap = resourceAccessListMap;
    }

    public void validateResourceAccess(String resourceName, HttpSession session, String userAttributeName)
            throws AuthenticationException {
        User user = (User) session.getAttribute(userAttributeName);

        if (user == null) {
            throw new AnonymousAccessDeniedException(
                    String.format("Anonymous access to resource '%s' is not authorized.", resourceName));
        } else {
            validateUserResourceAccess(resourceName, user);
        }
    }

    private void validateUserResourceAccess(String resourceName, User user) throws AuthenticationException {
        AccessControlList resourceAccessList = resourceAccessListMap.get(resourceName);
        if (!resourceAccessList.isUserAuthorized(user)) {
            throw new AccessDeniedException(
                    String.format("Access to resource '%s' for the specified user is not authorized.", resourceName));
        }
    }
}
