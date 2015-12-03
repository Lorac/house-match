package ca.ulaval.glo4003.housematch.services.picture;

public class PictureServiceException extends Exception {
    
    private static final long serialVersionUID = 6123532595322361119L;
    
    public PictureServiceException() {
        super();
    }

    public PictureServiceException(final Exception e) {
        super(e.getMessage(), e);
    }
    
    public PictureServiceException(final String message, final Exception e) {
        super(message, e);
    }
}
