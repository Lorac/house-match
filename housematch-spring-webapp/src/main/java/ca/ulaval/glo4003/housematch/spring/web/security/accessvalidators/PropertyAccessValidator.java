package ca.ulaval.glo4003.housematch.spring.web.security.accessvalidators;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class PropertyAccessValidator {

    public boolean validateUserAccess(Authentication authentication, Property property, String permission) {
        if (hasPermission(authentication, permission) || authentication instanceof AnonymousAuthenticationToken) {
            return property.isMostPopular();
        }
        return false;
    }

    protected boolean hasPermission(Authentication authentication, String role) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        User user = ((User) authentication.getPrincipal());
        return hasPermission(user, role);
    }

    protected boolean hasPermission(User user, String role) {
        if (!user.isActivated()) {
            return false;
        } else if (!user.hasRole(UserRole.valueOf(role))) {
            return false;
        }

        return true;
    }


}
