package ca.ulaval.glo4003.housematch.services.user;

public class UserMailModificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserMailModificationException() {
        super();
    }

    public UserMailModificationException(final String message, final Exception e) {
        super(message);
    }

}
