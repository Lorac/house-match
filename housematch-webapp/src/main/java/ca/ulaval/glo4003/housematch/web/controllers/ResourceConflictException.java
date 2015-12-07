package ca.ulaval.glo4003.housematch.web.controllers;

public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = -7559761075099006038L;

    public ResourceConflictException(final Exception e) {
        super(e);
    }

}
