package ca.ulaval.glo4003.housematch.domain;

public class InvalidValueException extends DomainException {

    private static final long serialVersionUID = 7674395538155608284L;

    public InvalidValueException(final String message) {
        super(message);
    }
}
