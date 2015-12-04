package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public class PropertyPhotoAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 6163013110930633718L;

    public PropertyPhotoAlreadyExistsException() {
        super();
    }

    public PropertyPhotoAlreadyExistsException(final String message) {
        super(message);
    }
}
