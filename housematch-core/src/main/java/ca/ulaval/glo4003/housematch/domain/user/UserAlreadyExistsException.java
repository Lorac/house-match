package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.DomainException;

public class UserAlreadyExistsException extends DomainException {

    private static final long serialVersionUID = 6163013110930633718L;

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

}
