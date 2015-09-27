package ca.ulaval.glo4003.housematch.spring.web.security;

import ca.ulaval.glo4003.housematch.domain.user.User;

public abstract class ResourceAccessEntry {

    protected Boolean authorized;

    public Boolean isAuthorized(final User user) {
        return false;
    }

    public Boolean isAnonymousUserAuthorized() {
        return false;
    }
}
