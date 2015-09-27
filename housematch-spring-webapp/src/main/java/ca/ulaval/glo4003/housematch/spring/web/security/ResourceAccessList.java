package ca.ulaval.glo4003.housematch.spring.web.security;

import java.util.Set;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class ResourceAccessList {

    private Set<ResourceAccessEntry> accessEntries;

    public ResourceAccessList(final Set<ResourceAccessEntry> accessEntries) {
        this.accessEntries = accessEntries;
    }

    public Boolean isUserAuthorized(User user) {
        return accessEntries.stream().anyMatch(entry -> entry.isAuthorized(user));
    }

    public Boolean isAnonymousUserAuthorized() {
        return accessEntries.stream().anyMatch(entry -> entry.isAnonymousUserAuthorized());
    }
}
