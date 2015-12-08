package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;

public interface UserObserver {

    void userStatusChanged(Object sender, UserStatus newStatus);

    void userNotificationQueued(Object sender, Notification notification);

}
