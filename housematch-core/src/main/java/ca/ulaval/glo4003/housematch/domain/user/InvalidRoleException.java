package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.DomainException;

public class InvalidRoleException extends DomainException {

    private static final long serialVersionUID = -4081194602588074521L;

    public InvalidRoleException(final String message) {
        super(message);
    }

}
