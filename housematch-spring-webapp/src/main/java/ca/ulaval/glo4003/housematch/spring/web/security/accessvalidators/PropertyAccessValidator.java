package ca.ulaval.glo4003.housematch.spring.web.security.accessvalidators;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import ca.ulaval.glo4003.housematch.domain.property.Property;

public class PropertyAccessValidator {

    public boolean validateAccess(Authentication authentication, Property property) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return property.isMostPopular();
        }
        return true;
    }

}
