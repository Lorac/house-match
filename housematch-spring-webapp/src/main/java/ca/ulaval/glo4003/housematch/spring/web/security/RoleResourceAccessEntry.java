package ca.ulaval.glo4003.housematch.spring.web.security;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class RoleResourceAccessEntry extends ResourceAccessEntry {

    private UserRole userRole;

    public RoleResourceAccessEntry(UserRole userRole, Boolean authorized) {
        this.userRole = userRole;
        this.authorized = authorized;
    }

    @Override
    public Boolean isAuthorized(User user) {
        return userRole.equals(user.getRole()) && authorized;
    }

    @Override
    public Boolean isAnonymousUserAuthorized() {
        return false;
    }
}
