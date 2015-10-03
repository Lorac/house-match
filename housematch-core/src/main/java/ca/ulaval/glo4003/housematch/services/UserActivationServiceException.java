package ca.ulaval.glo4003.housematch.services;

public class UserActivationServiceException extends Exception {

    private static final long serialVersionUID = 3617270637181333532L;

    public UserActivationServiceException() {
        super();
    }

    public UserActivationServiceException(final Exception e) {
        super(e);
    }

    public UserActivationServiceException(final String message, final Exception e) {
        super(message, e);
    }

}
