package ca.ulaval.glo4003.housematch.tasks;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.notification.NotificationService;
import ca.ulaval.glo4003.housematch.services.user.UserService;

public class UserNotificationTask implements Runnable {

    private NotificationInterval notificationInterval;
    private NotificationService notificationService;
    private UserService userService;

    public UserNotificationTask(final NotificationInterval notificationInterval, final NotificationService notificationService,
            final UserService userService) {
        this.notificationInterval = notificationInterval;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @Override
    public void run() {
        for (User user : userService.getUsers()) {
            processScheduledUserNotifications(user);
        }
    }

    private void processScheduledUserNotifications(User user) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (user.getNotificationSettings().notificationIntervalEquals(notificationType, notificationInterval)) {
                notificationService.processNotifications(user, notificationType);
            }
        }
    }

}
