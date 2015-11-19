package ca.ulaval.glo4003.housematch.web.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.web.security.accessvalidators.PropertyAccessValidator;

public class UserPermissionEvaluator implements PermissionEvaluator {

    @Inject
    private PropertyAccessValidator propertyAccessValidator;

    public UserPermissionEvaluator(final PropertyAccessValidator propertyAccessValidator) {
        this.propertyAccessValidator = propertyAccessValidator;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject == null) {
            return hasPermission(authentication, (String) permission);
        } else if (targetDomainObject instanceof Property) {
            return propertyAccessValidator.validateAccess(authentication, (Property) targetDomainObject);
        }
        return false;
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