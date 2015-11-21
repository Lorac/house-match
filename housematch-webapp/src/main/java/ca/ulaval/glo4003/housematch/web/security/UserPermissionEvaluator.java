package ca.ulaval.glo4003.housematch.web.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return hasPermission(authentication, (String) permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    private boolean hasPermission(Authentication authentication, String role) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        User user = ((User) authentication.getPrincipal());
        return hasPermission(user, role);
    }

    private boolean hasPermission(User user, String role) {
        if (!user.isActivated()) {
            return false;
        } else if (!user.hasRole(UserRole.valueOf(role))) {
            return false;
        }

        return true;
    }
}
