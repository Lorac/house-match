package ca.ulaval.glo4003.housematch.spring.web.security;

import javax.naming.AuthenticationException;

public class AccessDeniedException extends AuthenticationException {

    private static final long serialVersionUID = 1291515053041657044L;

    public AccessDeniedException(final String message) {
        super(message);
    }
}
