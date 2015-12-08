package ca.ulaval.glo4003.housematch.domain.user;

import java.util.List;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class UserFactory {

    private NotificationFactory notificationFactory;
    private StringHasher stringHasher;
    private List<UserObserver> sharedUserObservers;

    public UserFactory(final NotificationFactory notificationFactory, final StringHasher stringHasher,
            final List<UserObserver> sharedUserObservers) {
        this.notificationFactory = notificationFactory;
        this.stringHasher = stringHasher;
        this.sharedUserObservers = sharedUserObservers;
    }

    public User createUser(final String username, final String email, final String password, final UserRole role) {
        User user = new User(notificationFactory, stringHasher, username, email, password, role);
        user.registerObservers(sharedUserObservers);
        return user;
    }

}
