package ca.ulaval.glo4003.housematch.services.property;

public class PropertyServiceException extends Exception {

    private static final long serialVersionUID = 6123532595322361119L;

    public PropertyServiceException() {
        super();
    }

    public PropertyServiceException(final Exception e) {
        super(e.getMessage(), e);
    }

}
