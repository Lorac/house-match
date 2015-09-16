package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.DomainException;

public class UserNotFoundException extends DomainException {

    private static final long serialVersionUID = 1205713722003337460L;

    public UserNotFoundException(final String message) {
        super(message);
    }

}
