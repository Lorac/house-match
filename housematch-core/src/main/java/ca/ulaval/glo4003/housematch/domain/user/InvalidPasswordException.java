package ca.ulaval.glo4003.housematch.domain.user;

public class InvalidPasswordException  extends RuntimeException {

    private static final long serialVersionUID = -1359096800328030500L;

    public InvalidPasswordException(final String message) {
        super(message);
    }
}
