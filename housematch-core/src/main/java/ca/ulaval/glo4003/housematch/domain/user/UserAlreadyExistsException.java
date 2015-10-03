package ca.ulaval.glo4003.housematch.domain.user;

public class UserAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 6163013110930633718L;

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
