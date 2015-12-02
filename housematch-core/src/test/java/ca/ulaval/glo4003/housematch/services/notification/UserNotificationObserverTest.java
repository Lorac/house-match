package ca.ulaval.glo4003.housematch.services.notification;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.user.User;

public class UserNotificationObserverTest {

    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.FAVORITE_PROPERTY_MODIFIED;

    private User userMock;
    private Notification notificationMock;
    private NotificationSettings notificationSettingsMock;
    private NotificationService notificationServiceMock;

    private UserNotificationObserver userNotificationObserver;

    @Before
    public void init() {
        initMocks();
        initStubs();
        userNotificationObserver = new UserNotificationObserver(notificationServiceMock);
    }

    private void initMocks() {
        userMock = mock(User.class);
        notificationSettingsMock = mock(NotificationSettings.class);
        notificationServiceMock = mock(NotificationService.class);
        notificationMock = mock(Notification.class);
    }

    private void initStubs() {
        when(notificationMock.getType()).thenReturn(SAMPLE_NOTIFICATION_TYPE);
        when(userMock.getNotificationSettings()).thenReturn(notificationSettingsMock);
        when(notificationSettingsMock.notificationIntervalEquals(eq(SAMPLE_NOTIFICATION_TYPE), eq(NotificationInterval.IMMEDIATELY)))
                .thenReturn(true);
    }

    @Test
    public void propertyStatusChangeNotifiesAllTheUsersUsingTheNotificationService() {
        userNotificationObserver.userNotificationQueued(userMock, notificationMock);
        verify(notificationServiceMock).processNotifications(userMock, SAMPLE_NOTIFICATION_TYPE);
    }

    @Test
    public void propertyStatusChangeDoesNotNotifyTheUsersWhenTheNotificationIntervalSettingIsNotSetToRealtime() {
        when(notificationSettingsMock.notificationIntervalEquals(eq(SAMPLE_NOTIFICATION_TYPE), eq(NotificationInterval.IMMEDIATELY)))
                .thenReturn(false);
        userNotificationObserver.userNotificationQueued(userMock, notificationMock);
        verify(notificationServiceMock, never()).processNotifications(userMock, SAMPLE_NOTIFICATION_TYPE);
    }

}
