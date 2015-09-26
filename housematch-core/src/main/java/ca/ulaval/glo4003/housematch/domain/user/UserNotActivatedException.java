package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.DomainException;

public class UserNotActivatedException extends DomainException {

    private static final long serialVersionUID = 1007473395383529133L;

    public UserNotActivatedException(final String message) {
        super(message);
    }
}
