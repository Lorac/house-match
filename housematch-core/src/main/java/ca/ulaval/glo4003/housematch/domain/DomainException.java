package ca.ulaval.glo4003.housematch.domain;

public class DomainException extends RuntimeException {

    private static final long serialVersionUID = 195523093263519295L;

    public DomainException(final String message) {
        super(message);
    }
}
