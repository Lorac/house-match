package ca.ulaval.glo4003.housematch.domain.notification;

import java.util.HashMap;
import java.util.Map;

public class NotificationSettings {

    private Map<NotificationType, NotificationInterval> notificationIntervals = new HashMap<>();

    public NotificationSettings() {
        for (NotificationType notificationType : NotificationType.values()) {
            notificationIntervals.put(notificationType, notificationType.getDefaultNotificationInterval());
        }
    }

    public Map<NotificationType, NotificationInterval> getNotificationIntervals() {
        return notificationIntervals;
    }

    public void setNotificationIntervals(Map<NotificationType, NotificationInterval> notificationIntervals) {
        this.notificationIntervals = notificationIntervals;
    }

    public void setNotificationInterval(NotificationType notificationType, NotificationInterval notificationInterval) {
        notificationIntervals.put(notificationType, notificationInterval);
    }

    public NotificationInterval getNotificationInterval(NotificationType notificationType) {
        return notificationIntervals.get(notificationType);
    }

    public Boolean notificationIntervalEquals(NotificationType notificationType, NotificationInterval notificationInterval) {
        return notificationIntervals.get(notificationType) == notificationInterval;
    }

    public Boolean isNotificationEnabled(NotificationType notificationType) {
        return getNotificationInterval(notificationType) != NotificationInterval.NEVER;
    }

}
