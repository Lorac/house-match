package ca.ulaval.glo4003.housematch.services.notification;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class NotificationServiceTest {

    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.FAVORITE_PROPERTY_MODIFIED;
    private static final String SAMPLE_NOTIFICATION_STRING = "sampleString1";
    private static final String ANOTHER_SAMPLE_NOTIFICATION_STRING = "sampleString2";
    private static final String SAMPLE_EMAIL = "test@test.com";

    private User userMock;
    private User anotherUserMock;
    private MailSender mailSenderMock;
    private Notification notificationMock;
    private Notification anotherNotificationMock;
    private UserRepository userRepositoryMock;

    Queue<Notification> notificationQueue = new ConcurrentLinkedQueue<>();
    List<User> users = new ArrayList<>();
    private NotificationService notificationService;

    @Before
    public void init() {
        initMocks();
        initStubs();
        initCollections();
        notificationService = new NotificationService(mailSenderMock, userRepositoryMock);
    }

    private void initMocks() {
        userMock = mock(User.class);
        anotherUserMock = mock(User.class);
        mailSenderMock = mock(MailSender.class);
        notificationMock = mock(Notification.class);
        anotherNotificationMock = mock(Notification.class);
        userRepositoryMock = mock(UserRepository.class);
    }

    private void initStubs() {
        when(userMock.getNotificationQueue(SAMPLE_NOTIFICATION_TYPE)).thenReturn(notificationQueue);
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
        when(notificationMock.toString()).thenReturn(SAMPLE_NOTIFICATION_STRING);
        when(anotherNotificationMock.toString()).thenReturn(ANOTHER_SAMPLE_NOTIFICATION_STRING);
    }

    private void initCollections() {
        notificationQueue.add(notificationMock);
        users.add(userMock);
        users.add(anotherUserMock);
    }

    @Test
    public void processingNotificationRemovesTheNotificationFromTheQueue() {
        notificationService.processNotifications(userMock, SAMPLE_NOTIFICATION_TYPE);
        assertTrue(notificationQueue.isEmpty());
    }

    @Test
    public void processingNotificationDoesNotSendAnyEmailWhenNotificationQueueIsEmpty() {
        notificationQueue.clear();
        notificationService.processNotifications(userMock, SAMPLE_NOTIFICATION_TYPE);
        verify(mailSenderMock, never()).sendAsync(anyString(), anyString(), anyString());
    }

    @Test
    public void processingNotificationSendsAnEmailContainingTheNotificationDescriptionToTheSpecifiedUser() {
        notificationService.processNotifications(userMock, SAMPLE_NOTIFICATION_TYPE);
        verify(mailSenderMock).sendAsync(anyString(), eq(SAMPLE_NOTIFICATION_STRING), eq(SAMPLE_EMAIL));
    }

    @Test
    public void processingNotificationsSendsTheEmailInTheRightOrderWhenNotificationQueueContainsMultipleNotifications() {
        notificationQueue.add(anotherNotificationMock);

        notificationService.processNotifications(userMock, SAMPLE_NOTIFICATION_TYPE);

        InOrder inOrder = inOrder(mailSenderMock);
        inOrder.verify(mailSenderMock).sendAsync(anyString(), eq(SAMPLE_NOTIFICATION_STRING), anyString());
        inOrder.verify(mailSenderMock).sendAsync(anyString(), eq(ANOTHER_SAMPLE_NOTIFICATION_STRING), anyString());
    }

    @Test
    public void notifyingTheUsersNotifiesAllTheUsers() {
        when(userRepositoryMock.getAll()).thenReturn(users);

        notificationService.notifyAllUsers(notificationMock);

        verify(userMock).notify(notificationMock);
        verify(anotherUserMock).notify(notificationMock);
    }
}
