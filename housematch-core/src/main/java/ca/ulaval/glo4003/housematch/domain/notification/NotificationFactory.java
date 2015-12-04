package ca.ulaval.glo4003.housematch.domain.notification;

public class NotificationFactory {

    public Notification createNotification(NotificationType notificationType, String eventDescription) {
        return new Notification(notificationType, eventDescription);
    }
}
