package ca.ulaval.glo4003.housematch.validators;

public class UserCreationValidationException extends Exception {

    private static final long serialVersionUID = 7674395538155608284L;

    public UserCreationValidationException() {
        super();
    }

    public UserCreationValidationException(final String message) {
        super(message);
    }
}
