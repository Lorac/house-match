package ca.ulaval.glo4003.housematch.domain.notification;

import java.time.ZonedDateTime;

import ca.ulaval.glo4003.housematch.utils.DateFormatter;

public class Notification {

    private NotificationType type;
    private String eventDescription;
    private ZonedDateTime creationDate = ZonedDateTime.now();

    public Notification(final NotificationType notificationType, final String eventDescription) {
        this.type = notificationType;
        this.eventDescription = eventDescription;
    }

    public NotificationType getType() {
        return type;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return String.format("%s: %s\r\n", DateFormatter.toShortDateTime(creationDate), eventDescription);
    }
}
