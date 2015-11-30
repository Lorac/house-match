package ca.ulaval.glo4003.housematch.domain.user;

import java.util.List;

import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class UserFactory {

    StringHasher stringHasher;
    List<UserObserver> sharedUserObservers;

    public UserFactory(final StringHasher stringHasher, final List<UserObserver> sharedUserObservers) {
        this.stringHasher = stringHasher;
        this.sharedUserObservers = sharedUserObservers;
    }

    public User createUser(final String username, final String email, final String password, final UserRole role) {
        User user = new User(stringHasher, username, email, password, role);
        user.registerObservers(sharedUserObservers);
        return user;
    }

}
