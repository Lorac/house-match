package ca.ulaval.glo4003.housematch.spring.web.security;

import java.util.Set;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class AccessControlList {

    private Set<UserRole> authorizedRoles;

    public AccessControlList(final Set<UserRole> authorizedRoles) {
        this.authorizedRoles = authorizedRoles;
    }

    public Boolean isUserAuthorized(User user) {
        return authorizedRoles.stream().anyMatch(authorizedRole -> user.hasRole(authorizedRole));
    }
}
