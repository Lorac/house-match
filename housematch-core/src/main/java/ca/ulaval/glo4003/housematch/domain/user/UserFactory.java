package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class UserFactory {

    StringHasher stringHasher;
    UserObserver sharedUserObserver;

    public UserFactory(final StringHasher stringHasher, final UserObserver sharedUserObserver) {
        this.stringHasher = stringHasher;
        this.sharedUserObserver = sharedUserObserver;
    }

    public User createUser(final String username, final String email, final String password, final UserRole role) {
        User user = new User(stringHasher, username, email, password, role);
        user.registerObserver(sharedUserObserver);
        return user;
    }

}
