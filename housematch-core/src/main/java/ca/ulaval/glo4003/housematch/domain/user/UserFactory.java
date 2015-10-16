package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class UserFactory {

    StringHasher stringHasher;

    public UserFactory(StringHasher stringHasher) {
        this.stringHasher = stringHasher;
    }

    public User createUser(final String username, final String email, final String password, final UserRole role) {
        return new User(stringHasher, username, email, password, role);
    }

}
