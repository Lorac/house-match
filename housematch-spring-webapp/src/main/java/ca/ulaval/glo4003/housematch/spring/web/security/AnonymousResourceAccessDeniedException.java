package ca.ulaval.glo4003.housematch.spring.web.security;

import javax.naming.AuthenticationException;

public class AnonymousResourceAccessDeniedException extends AuthenticationException {

    private static final long serialVersionUID = -4510609948588708850L;

    public AnonymousResourceAccessDeniedException(final String message) {
        super(message);
    }
}
