package ca.ulaval.glo4003.housematch.spring.web.security;

import javax.naming.AuthenticationException;

public class ResourceAccessDeniedException extends AuthenticationException {

    private static final long serialVersionUID = 1291515053041657044L;

    public ResourceAccessDeniedException(final String message) {
        super(message);
    }
}
