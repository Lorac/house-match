package ca.ulaval.glo4003.housematch.spring.web.security;

import java.util.Map;

import javax.servlet.http.HttpSession;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class ResourceAccessValidator {

    private Map<String, ResourceAccessList> resourceAccessListMap;

    public ResourceAccessValidator(Map<String, ResourceAccessList> resourceAccessListMap) {
        this.resourceAccessListMap = resourceAccessListMap;
    }

    public void validateResourceAccess(String resourceName, HttpSession session) {
        if (session.getAttribute("user") == null) {
            validateAnonymousUserResourceAccess(resourceName);
        } else {
            User user = (User) session.getAttribute("user");
            validateUserResourceAccess(resourceName, user);
        }
    }

    private void validateUserResourceAccess(String resourceName, User user) {
        ResourceAccessList resourceAccessList = resourceAccessListMap.get(resourceName);
        if (!resourceAccessList.isUserAuthorized(user)) {
            throw new ResourceAccessDeniedException(
                    String.format("Access to resource '%s' for the specified user is not authorized.", resourceName));
        }
    }

    private void validateAnonymousUserResourceAccess(String resourceName) {
        ResourceAccessList resourceAccessList = resourceAccessListMap.get(resourceName);
        if (!resourceAccessList.isAnonymousUserAuthorized()) {
            throw new AnonymousResourceAccessDeniedException(
                    String.format("Anonymous access to the resource '%s' is not authorized.", resourceName));
        }
    }
}
