package ca.ulaval.glo4003.housematch.domain.notification;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NotificationTest {

    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.FAVORITE_PROPERTY_CHANGED;
    private static final String SAMPLE_EVENT_DESCRIPTION = "eventDescription";

    private Notification notification;

    @Before
    public void init() {
        notification = new Notification(SAMPLE_NOTIFICATION_TYPE, SAMPLE_EVENT_DESCRIPTION);
    }

    @Test
    public void gettingTheNotificationTypeGetsTheNotificationType() {
        assertEquals(SAMPLE_NOTIFICATION_TYPE, notification.getType());
    }

    @Test
    public void gettingTheEventDescriptionGetsTheEventDescription() {
        assertEquals(SAMPLE_EVENT_DESCRIPTION, notification.getDescription());
    }
}
