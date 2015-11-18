package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class UserObservableTest {

    private static final UserStatus SAMPLE_USER_STATUS = UserStatus.ACTIVE;

    private UserObservable userObservable;

    private User userMock;
    private UserObserver userObserverMock;
    private UserObserver anotherUserObserverMock;

    @Before
    public void init() {
        userObserverMock = mock(UserObserver.class);
        anotherUserObserverMock = mock(UserObserver.class);
        userObservable = new UserObservable();
        registerObservers();
    }

    private void registerObservers() {
        userObservable.registerObserver(userObserverMock);
        userObservable.registerObserver(anotherUserObserverMock);
    }

    @Test
    public void registeringAnObserverRegistersTheObserver() {
        assertTrue(userObservable.isObserverRegistered(userObserverMock));
    }

    @Test
    public void changingUserStatusNotifiesAllTheObservers() {
        userObservable.userStatusChanged(userMock, SAMPLE_USER_STATUS);

        verify(userObserverMock).userStatusChanged(userMock, SAMPLE_USER_STATUS);
        verify(anotherUserObserverMock).userStatusChanged(userMock, SAMPLE_USER_STATUS);
    }

    @Test
    public void unregisteringAnObserverUnregistersTheObserver() {
        userObservable.unregisterObserver(userObserverMock);
        assertFalse(userObservable.isObserverRegistered(userObserverMock));
    }

    @Test
    public void unregisteringAnObserverStopsNotifyingThatObserver() {
        userObservable.unregisterObserver(userObserverMock);

        userObservable.userStatusChanged(userMock, SAMPLE_USER_STATUS);

        verify(userObserverMock, never()).userStatusChanged(userMock, SAMPLE_USER_STATUS);
        verify(anotherUserObserverMock).userStatusChanged(userMock, SAMPLE_USER_STATUS);
    }
}
