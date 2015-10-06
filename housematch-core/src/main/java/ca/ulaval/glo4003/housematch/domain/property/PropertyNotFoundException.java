package ca.ulaval.glo4003.housematch.domain.property;

public class PropertyNotFoundException extends Exception {

    private static final long serialVersionUID = 751474011098753048L;

    public PropertyNotFoundException() {
        super();
    }

    public PropertyNotFoundException(final String message) {
        super(message);
    }
}
