package ca.ulaval.glo4003.housematch.services.statistics;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserverTest {

    private UserStatisticsService userStatisticsServiceMock;
    private User userMock;
    private UserStatisticsObserver userStatisticsObserver;

    @Before
    public void init() {
        userStatisticsServiceMock = mock(UserStatisticsService.class);
        userMock = mock(User.class);
        userStatisticsObserver = new UserStatisticsObserver(userStatisticsServiceMock);
    }

    @Test
    public void userStatusChangeToActiveFiresTheProperEventOfTheStatisticsService() {
        userStatisticsObserver.userStatusChanged(userMock, UserStatus.ACTIVE);
        verify(userStatisticsServiceMock).applyUserStatusChangeToActive(userMock);
    }

    @Test
    public void userStatusChangeToInactiveFiresTheProperEventOfTheStatisticsService() {
        userStatisticsObserver.userStatusChanged(userMock, UserStatus.INACTIVE);
        verify(userStatisticsServiceMock).applyUserStatusChangeToInactive(userMock);
    }

}
