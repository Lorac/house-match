package ca.ulaval.glo4003.housematch.validators.property;

public class PropertyCreationValidationException extends Exception {

    private static final long serialVersionUID = 3280790278183620775L;

    public PropertyCreationValidationException() {
        super();
    }

    public PropertyCreationValidationException(final String message) {
        super(message);
    }

    public PropertyCreationValidationException(final String message, final Exception e) {
        super(message, e);
    }
}
