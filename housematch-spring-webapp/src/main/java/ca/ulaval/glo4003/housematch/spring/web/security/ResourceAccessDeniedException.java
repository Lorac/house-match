package ca.ulaval.glo4003.housematch.spring.web.security;

public class ResourceAccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1291515053041657044L;

    public ResourceAccessDeniedException(final String message) {
        super(message);
    }
}
