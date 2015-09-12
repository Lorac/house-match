package ca.ulaval.glo4003.housematch.services;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1205713722003337460L;

    public UserNotFoundException(final String message) {
        super(message);
    }

}
