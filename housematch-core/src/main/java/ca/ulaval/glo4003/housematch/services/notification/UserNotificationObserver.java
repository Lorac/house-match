package ca.ulaval.glo4003.housematch.services.notification;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserObserver;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserNotificationObserver implements UserObserver {

    private NotificationService notificationService;

    public UserNotificationObserver(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void userStatusChanged(Object sender, UserStatus newStatus) {
        // Event intentionally ignored.
    }

    @Override
    public void userNotificationQueued(Object sender, Notification notification) {
        notificationService.processQueuedUserNotification((User) sender, notification);
    }

}
