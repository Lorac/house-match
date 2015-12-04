package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public class PropertyPhotoNotFoundException extends Exception {

    private static final long serialVersionUID = 751474011098753048L;

    public PropertyPhotoNotFoundException() {
        super();
    }

    public PropertyPhotoNotFoundException(final String message) {
        super(message);
    }
}
