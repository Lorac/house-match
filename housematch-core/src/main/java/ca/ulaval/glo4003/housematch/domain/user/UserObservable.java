package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.utils.Observable;

public class UserObservable extends Observable<UserObserver> {

    public void userStatusChanged(Object sender, UserStatus newStatus) {
        observers.stream().forEach(o -> o.userStatusChanged(sender, newStatus));
    }

    public void userNotificationQueued(Object sender, Notification notification) {
        observers.stream().forEach(o -> o.userNotificationQueued(sender, notification));
    }
}
