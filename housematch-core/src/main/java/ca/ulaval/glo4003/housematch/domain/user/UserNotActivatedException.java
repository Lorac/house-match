package ca.ulaval.glo4003.housematch.domain.user;

public class UserNotActivatedException extends Exception {

    private static final long serialVersionUID = 1007473395383529133L;

    public UserNotActivatedException() {
        super();
    }

    public UserNotActivatedException(final String message) {
        super(message);
    }
}
