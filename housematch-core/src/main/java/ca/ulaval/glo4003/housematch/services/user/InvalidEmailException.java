package ca.ulaval.glo4003.housematch.services.user;

public class InvalidEmailException extends Exception {

    private static final long serialVersionUID = 2571035044974939706L;

    public InvalidEmailException(final String string) {
        super();
    }

    public InvalidEmailException(final Exception e) {
        super(e.getMessage(), e);
    }

}
