package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.DomainException;

public class InvalidRoleException extends DomainException {

    public InvalidRoleException(final String message) {
        super(message);
    }

}
