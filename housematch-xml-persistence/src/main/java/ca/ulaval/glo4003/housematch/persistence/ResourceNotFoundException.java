package ca.ulaval.glo4003.housematch.persistence;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7964276925754946574L;

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
