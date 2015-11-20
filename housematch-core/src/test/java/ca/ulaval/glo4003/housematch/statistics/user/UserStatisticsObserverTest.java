package ca.ulaval.glo4003.housematch.statistics.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserverTest {

    private UserStatisticsCollector userStatisticsCollectorMock;
    private User userMock;
    private UserStatisticsObserver userStatisticsObserver;

    @Before
    public void init() {
        userStatisticsCollectorMock = mock(UserStatisticsCollector.class);
        userMock = mock(User.class);
        userStatisticsObserver = new UserStatisticsObserver(userStatisticsCollectorMock);
    }

    @Test
    public void userStatusChangeToActiveFiresTheProperEventOfTheStatisticsCollector() {
        userStatisticsObserver.userStatusChanged(userMock, UserStatus.ACTIVE);
        verify(userStatisticsCollectorMock).applyUserStatusChangeToActive(userMock);
    }

    @Test
    public void userStatusChangeToInactiveFiresTheProperEventOfTheStatisticsCollector() {
        userStatisticsObserver.userStatusChanged(userMock, UserStatus.INACTIVE);
        verify(userStatisticsCollectorMock).applyUserStatusChangeToInactive(userMock);
    }

}
