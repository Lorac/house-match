package ca.ulaval.glo4003.housematch.validators.address;

public class AddressValidationException extends Exception {

    private static final long serialVersionUID = -2827488486434585344L;

    public AddressValidationException() {
        super();
    }

    public AddressValidationException(final String message) {
        super(message);
    }
}
