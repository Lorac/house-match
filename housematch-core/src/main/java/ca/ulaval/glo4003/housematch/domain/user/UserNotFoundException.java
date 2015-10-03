package ca.ulaval.glo4003.housematch.domain.user;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1205713722003337460L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final String message) {
        super(message);
    }
}
