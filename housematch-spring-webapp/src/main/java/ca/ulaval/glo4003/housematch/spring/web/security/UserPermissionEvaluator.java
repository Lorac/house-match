package ca.ulaval.glo4003.housematch.spring.web.security;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.security.accessvalidators.PropertyAccessValidator;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import javax.inject.Inject;
import java.io.Serializable;

public class UserPermissionEvaluator implements PermissionEvaluator {

    @Inject
    private PropertyAccessValidator propertyAccessValidator;

    public UserPermissionEvaluator() {

    }

    public UserPermissionEvaluator(final PropertyAccessValidator propertyAccessValidator) {
        this.propertyAccessValidator = propertyAccessValidator;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Property) {
            return propertyAccessValidator.validateUserAccess(authentication, (Property) targetDomainObject, (String) permission);
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
