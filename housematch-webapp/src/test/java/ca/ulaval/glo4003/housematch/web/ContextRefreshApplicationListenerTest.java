package ca.ulaval.glo4003.housematch.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.services.notification.UserNotificationObserver;
import ca.ulaval.glo4003.housematch.services.statistics.UserStatisticsObserver;
import ca.ulaval.glo4003.housematch.web.ContextRefreshApplicationListener;

public class ContextRefreshApplicationListenerTest {

    private ContextRefreshedEvent contextRefreshedEventMock;
    private ApplicationContext applicationContextMock;
    private UserRepository userRepositoryMock;
    private UserNotificationObserver userNotificationObserverMock;
    private UserStatisticsObserver userStatisticsObserverMock;
    private User userMock;
    private List<User> users = new ArrayList<>();

    private ContextRefreshApplicationListener contextRefreshApplicationListener;

    @Before
    public void init() {
        initMocks();
        initStubs();
        users.add(userMock);
        contextRefreshApplicationListener = new ContextRefreshApplicationListener();
    }

    private void initMocks() {
        contextRefreshedEventMock = mock(ContextRefreshedEvent.class);
        applicationContextMock = mock(ApplicationContext.class);
        userRepositoryMock = mock(UserRepository.class);
        userNotificationObserverMock = mock(UserNotificationObserver.class);
        userStatisticsObserverMock = mock(UserStatisticsObserver.class);
        userMock = mock(User.class);
    }

    private void initStubs() {
        when(contextRefreshedEventMock.getApplicationContext()).thenReturn(applicationContextMock);
        when(applicationContextMock.getBean(UserRepository.class)).thenReturn(userRepositoryMock);
        when(applicationContextMock.getBean(UserNotificationObserver.class)).thenReturn(userNotificationObserverMock);
        when(applicationContextMock.getBean(UserStatisticsObserver.class)).thenReturn(userStatisticsObserverMock);
        when(userRepositoryMock.getAll()).thenReturn(users);
    }

    @Test
    public void globalUserNotificationObserverIsLinkedToUsersOnContextRefresh() {
        contextRefreshApplicationListener.onApplicationEvent(contextRefreshedEventMock);
        verify(userMock).registerObserver(userNotificationObserverMock);
    }

    @Test
    public void globalUserStatisticsObserverIsLinkedToUsersOnContextRefresh() {
        contextRefreshApplicationListener.onApplicationEvent(contextRefreshedEventMock);
        verify(userMock).registerObserver(userStatisticsObserverMock);
    }
}
