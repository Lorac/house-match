package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.utils.Observable;

public class UserObservable extends Observable<UserObserver> {

    public void userStatusChanged(User user, UserStatus newStatus) {
        observers.stream().forEach(o -> o.userStatusChanged(user, newStatus));
    }

    public void userNotificationQueued(User user, Notification notification) {
        observers.stream().forEach(o -> o.userNotificationQueued(user, notification));
    }
}
