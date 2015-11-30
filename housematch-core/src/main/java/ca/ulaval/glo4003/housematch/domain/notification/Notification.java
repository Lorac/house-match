package ca.ulaval.glo4003.housematch.domain.notification;

import java.time.ZonedDateTime;

public class Notification {

    private NotificationType notificationType;
    private String eventDescription;
    private ZonedDateTime notificationDate = ZonedDateTime.now();

    public Notification(final NotificationType notificationType, final String eventDescription) {
        this.notificationType = notificationType;
        this.eventDescription = eventDescription;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public ZonedDateTime getNotificationDate() {
        return notificationDate;
    }
}
