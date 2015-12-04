package ca.ulaval.glo4003.housematch.services.property;

public class PropertyPhotoServiceException extends Exception {

    private static final long serialVersionUID = 6123532595322361119L;

    public PropertyPhotoServiceException() {
        super();
    }

    public PropertyPhotoServiceException(final Exception e) {
        super(e.getMessage(), e);
    }

    public PropertyPhotoServiceException(final String message, final Exception e) {
        super(message, e);
    }

}
