package ca.ulaval.glo4003.housematch.validators;

public class UserRegistrationValidationException extends Exception {

    private static final long serialVersionUID = 7674395538155608284L;

    public UserRegistrationValidationException() {
        super();
    }

    public UserRegistrationValidationException(final String message) {
        super(message);
    }
}
