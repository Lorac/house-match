package ca.ulaval.glo4003.housematch.spring.web.security;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class PublicResourceAccessEntry extends ResourceAccessEntry {

    public PublicResourceAccessEntry(Boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public Boolean isAuthorized(User user) {
        return authorized;
    }

    @Override
    public Boolean isAnonymousUserAuthorized() {
        return authorized;
    }
}
