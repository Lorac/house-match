package ca.ulaval.glo4003.housematch.domain.notification;

import java.time.ZonedDateTime;

import ca.ulaval.glo4003.housematch.utils.DateFormatter;

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

    @Override
    public String toString() {
        return String.format("%s: %s\r\n", DateFormatter.toShortDateTime(notificationDate), eventDescription);
    }
}
