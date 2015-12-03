package ca.ulaval.glo4003.housematch.domain.picture;

public class PictureAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public PictureAlreadyExistsException() {
        super();
    }

    public PictureAlreadyExistsException(final String message) {
        super(message);
    }
}
