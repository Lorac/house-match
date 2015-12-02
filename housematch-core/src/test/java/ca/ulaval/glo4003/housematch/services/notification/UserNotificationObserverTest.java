package ca.ulaval.glo4003.housematch.services.notification;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.user.User;

public class UserNotificationObserverTest {

    private User userMock;
    private Notification notificationMock;
    private NotificationService notificationServiceMock;

    private UserNotificationObserver userNotificationObserver;

    @Before
    public void init() {
        initMocks();
        userNotificationObserver = new UserNotificationObserver(notificationServiceMock);
    }

    private void initMocks() {
        userMock = mock(User.class);
        notificationServiceMock = mock(NotificationService.class);
        notificationMock = mock(Notification.class);
    }

    @Test
    public void userNotificationQueuedFiresTheProperEventOfTheNotificationService() {
        userNotificationObserver.userNotificationQueued(userMock, notificationMock);
        verify(notificationServiceMock).processQueuedUserNotification(userMock, notificationMock);
    }

}
