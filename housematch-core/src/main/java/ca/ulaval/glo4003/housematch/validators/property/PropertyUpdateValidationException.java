package ca.ulaval.glo4003.housematch.validators.property;

public class PropertyUpdateValidationException extends Exception {

    private static final long serialVersionUID = -4794902260242583427L;

    public PropertyUpdateValidationException() {
        super();
    }

    public PropertyUpdateValidationException(final String message) {
        super(message);
    }

    public PropertyUpdateValidationException(final String message, final Exception e) {
        super(message, e);
    }
}
