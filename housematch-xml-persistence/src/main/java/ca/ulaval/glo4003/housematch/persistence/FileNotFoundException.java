package ca.ulaval.glo4003.housematch.persistence;

public class FileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7964276925754946574L;

    public FileNotFoundException(final String message) {
        super(message);
    }
}
