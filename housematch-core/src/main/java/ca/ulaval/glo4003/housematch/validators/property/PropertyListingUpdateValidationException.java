package ca.ulaval.glo4003.housematch.validators.property;

public class PropertyListingUpdateValidationException extends Exception {

    private static final long serialVersionUID = -4794902260242583427L;

    public PropertyListingUpdateValidationException() {
        super();
    }

    public PropertyListingUpdateValidationException(final String message) {
        super(message);
    }

    public PropertyListingUpdateValidationException(final String message, final Exception e) {
        super(message, e);
    }
}
