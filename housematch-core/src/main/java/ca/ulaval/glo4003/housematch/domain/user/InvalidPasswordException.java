package ca.ulaval.glo4003.housematch.domain.user;

public class InvalidPasswordException extends Exception {

    private static final long serialVersionUID = -1359096800328030500L;

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(final String message) {
        super(message);
    }
}
