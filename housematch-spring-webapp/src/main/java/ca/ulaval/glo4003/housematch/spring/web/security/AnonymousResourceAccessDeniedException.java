package ca.ulaval.glo4003.housematch.spring.web.security;

public class AnonymousResourceAccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = -4510609948588708850L;

    public AnonymousResourceAccessDeniedException(final String message) {
        super(message);
    }
}
