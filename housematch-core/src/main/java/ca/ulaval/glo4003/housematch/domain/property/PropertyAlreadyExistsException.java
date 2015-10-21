package ca.ulaval.glo4003.housematch.domain.property;

public class PropertyAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 6163013110930633718L;

    public PropertyAlreadyExistsException() {
        super();
    }

    public PropertyAlreadyExistsException(final String message) {
        super(message);
    }
}
