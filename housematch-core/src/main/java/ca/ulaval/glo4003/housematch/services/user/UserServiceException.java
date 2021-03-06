package ca.ulaval.glo4003.housematch.services.user;

public class UserServiceException extends Exception {

    private static final long serialVersionUID = 3617270637181333532L;

    public UserServiceException() {
        super();
    }

    public UserServiceException(final Exception e) {
        super(e.getMessage(), e);
    }

    public UserServiceException(final String message) {
        super(message);
    }

    public UserServiceException(final String message, final Exception e) {
        super(message, e);
    }

}
