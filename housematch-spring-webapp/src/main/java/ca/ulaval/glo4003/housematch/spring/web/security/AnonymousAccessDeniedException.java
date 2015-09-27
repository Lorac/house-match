package ca.ulaval.glo4003.housematch.spring.web.security;

import javax.naming.AuthenticationException;

public class AnonymousAccessDeniedException extends AuthenticationException {

    private static final long serialVersionUID = -4510609948588708850L;

    public AnonymousAccessDeniedException(final String message) {
        super(message);
    }
}
