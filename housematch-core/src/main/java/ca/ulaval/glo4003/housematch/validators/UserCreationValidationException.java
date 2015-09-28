package ca.ulaval.glo4003.housematch.validators;

import ca.ulaval.glo4003.housematch.domain.DomainException;

public class UserCreationValidationException extends DomainException {

    private static final long serialVersionUID = 7674395538155608284L;

    public UserCreationValidationException() {
        super();
    }

    public UserCreationValidationException(final String message) {
        super(message);
    }
}
