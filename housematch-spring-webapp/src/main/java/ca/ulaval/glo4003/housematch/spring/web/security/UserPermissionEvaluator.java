package ca.ulaval.glo4003.housematch.spring.web.security;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class UserPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Property) {
            if (hasPermission(authentication, (String) permission) || authentication instanceof AnonymousAuthenticationToken) {
                return ((Property) targetDomainObject).isMostPopular();
            }
        }
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
