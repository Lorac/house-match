package ca.ulaval.glo4003.housematch.domain.picture;

public class PictureNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PictureNotFoundException() {
        super();
    }

    public PictureNotFoundException(final String message) {
        super(message);
    }
}
