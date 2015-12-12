package ca.ulaval.glo4003.housematch.domain.notification;

public class NotificationFactory {

    public Notification createNotification(NotificationType notificationType, String notificationDescription) {
        return new Notification(notificationType, notificationDescription);
    }
}
