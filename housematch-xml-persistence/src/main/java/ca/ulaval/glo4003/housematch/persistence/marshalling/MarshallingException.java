package ca.ulaval.glo4003.housematch.persistence.marshalling;

public class MarshallingException extends RuntimeException {

    private static final long serialVersionUID = 7219602225282744002L;

    public MarshallingException(final String message) {
        super(message);
    }

    public MarshallingException(final String message, final Exception e) {
        super(message, e);
    }
}
