package ca.ulaval.glo4003.housematch.domain.notification;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NotificationFactoryTest {

    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.FAVORITE_PROPERTY_CHANGED;
    private static final String SAMPLE_EVENT_DESCRIPTION = "eventDescription";

    private NotificationFactory notificationFactory;

    @Before
    public void init() {
        notificationFactory = new NotificationFactory();
    }

    @Test
    public void notificationFactoryCreatesNotificationWithTheSpecifiedAttributes() {
        Notification notification = notificationFactory.createNotification(SAMPLE_NOTIFICATION_TYPE, SAMPLE_EVENT_DESCRIPTION);

        assertEquals(SAMPLE_NOTIFICATION_TYPE, notification.getType());
        assertEquals(SAMPLE_EVENT_DESCRIPTION, notification.getEventDescription());
    }
}
