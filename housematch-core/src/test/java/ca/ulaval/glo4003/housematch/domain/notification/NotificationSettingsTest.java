package ca.ulaval.glo4003.housematch.domain.notification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class NotificationSettingsTest {

    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.FAVORITE_PROPERTY_MODIFIED;

    private Map<NotificationType, NotificationInterval> notificationIntervals = new HashMap<>();

    private NotificationSettings notificationSettings;

    @Before
    public void init() {
        notificationSettings = new NotificationSettings();
    }

    @Test
    public void settingTheNotificationIntervalsSetsTheNotificationIntervals() {
        notificationSettings.setNotificationIntervals(notificationIntervals);
        assertSame(notificationIntervals, notificationSettings.getNotificationIntervals());
    }

    @Test
    public void settingTheNotificationIntervalSetsTheNotificationInterval() {
        notificationSettings.setNotificationInterval(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.DAILY);
        assertSame(NotificationInterval.DAILY, notificationSettings.getNotificationInterval(SAMPLE_NOTIFICATION_TYPE));
    }

    @Test
    public void differentNotificationIntervalsAreConsideredAsDifferent() {
        notificationSettings.setNotificationInterval(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.DAILY);
        assertFalse(notificationSettings.notificationIntervalEquals(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.WEEKLY));
    }

    @Test
    public void theSameNotificationIntervalIsConsideredAsEqual() {
        notificationSettings.setNotificationInterval(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.DAILY);
        assertTrue(notificationSettings.notificationIntervalEquals(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.DAILY));
    }

    @Test
    public void notificationIsConsideredAsDisabledWhenNotificationIntervalIsSetToNever() {
        notificationSettings.setNotificationInterval(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.NEVER);
        assertFalse(notificationSettings.isNotificationEnabled(SAMPLE_NOTIFICATION_TYPE));
    }

    @Test
    public void notificationIsConsideredAsEnabledWhenNotificationIntervalIsNotSetToNever() {
        notificationSettings.setNotificationInterval(SAMPLE_NOTIFICATION_TYPE, NotificationInterval.DAILY);
        assertTrue(notificationSettings.isNotificationEnabled(SAMPLE_NOTIFICATION_TYPE));
    }
}
