package ca.ulaval.glo4003.housematch.tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.notification.NotificationService;
import ca.ulaval.glo4003.housematch.services.user.UserService;

public class UserNotificationTaskTest {

    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.FAVORITE_PROPERTY_MODIFIED;
    private static final NotificationInterval SAMPLE_NOTIFICATION_INTERVAL = NotificationInterval.DAILY;

    private NotificationService notificationServiceMock;
    private UserService userServiceMock;
    private User userMock;

    private UserNotificationTask userNotificationTask;
    private List<User> users = new ArrayList<>();

    @Before
    public void init() {
        initMocks();
        initStubs();
        users.add(userMock);
        userNotificationTask = new UserNotificationTask(SAMPLE_NOTIFICATION_INTERVAL, notificationServiceMock, userServiceMock);
    }

    private void initMocks() {
        userMock = mock(User.class);
        userServiceMock = mock(UserService.class);
        notificationServiceMock = mock(NotificationService.class);
    }

    private void initStubs() {
        when(userServiceMock.getUsers()).thenReturn(users);
    }

    @Test
    public void runningTheTaskCallsTheNotificationServiceWhenUserNotificationIntervalSettingMatchesTheIntervalSettingOfTheTask() {
        when(userMock.notificationIntervalEquals(SAMPLE_NOTIFICATION_TYPE, SAMPLE_NOTIFICATION_INTERVAL)).thenReturn(true);
        userNotificationTask.run();
        verify(notificationServiceMock).processUserNotificationQueue(userMock, SAMPLE_NOTIFICATION_TYPE);
    }
}
