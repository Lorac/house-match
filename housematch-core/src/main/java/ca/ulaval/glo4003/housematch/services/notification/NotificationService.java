package ca.ulaval.glo4003.housematch.services.notification;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationConsumer;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.user.User;

public class NotificationService {

    private List<NotificationConsumer> notificationConsumers;

    public void processScheduledNotifications(User user, NotificationInterval notificationInterval) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (user.getNotificationSettings().notificationIntervalEquals(notificationType, notificationInterval)) {
                processNotifications(user, notificationType);
            }
        }
    }

    public void processNotifications(User user, NotificationType notificationType) {
        Queue<Notification> notificationQueue = user.getNotificationQueue(notificationType);
        List<Notification> notifications = notificationQueue.stream().collect(Collectors.toList());
        notifyConsumers(notifications);
    }

    public void registerNotificationConsumers(NotificationConsumer notificationConsumer) {
        notificationConsumers.add(notificationConsumer);
    }

    private void notifyConsumers(Collection<Notification> notifications) {
        notificationConsumers.stream().forEach(n -> n.accept(notifications));
    }
}
