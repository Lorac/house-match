package ca.ulaval.glo4003.housematch.services;

public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 6163013110930633718L;

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

}
