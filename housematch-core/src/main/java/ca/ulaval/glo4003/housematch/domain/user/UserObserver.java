package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;

public interface UserObserver {

    void userStatusChanged(User user, UserStatus newStatus);

    void userNotificationQueued(User user, Notification notification);

}
