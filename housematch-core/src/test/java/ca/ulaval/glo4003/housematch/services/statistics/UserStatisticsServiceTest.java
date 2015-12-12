package ca.ulaval.glo4003.housematch.services.statistics;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserStatisticsServiceTest {

    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_BUYERS = 3;
    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_SELLERS = 2;

    private UserStatistics userStatisticsMock;
    private User userMock;

    private UserStatisticsService userStatisticsService;

    @Before
    public void init() {
        initMocks();
        initStubs();
        userStatisticsService = new UserStatisticsService(userStatisticsMock);
    }

    private void initMocks() {
        userMock = mock(User.class);
        userStatisticsMock = mock(UserStatistics.class);
    }

    private void initStubs() {
        when(userStatisticsMock.getNumberOfActiveBuyers()).thenReturn(SAMPLE_NUMBER_OF_ACTIVE_BUYERS);
        when(userStatisticsMock.getNumberOfActiveSellers()).thenReturn(SAMPLE_NUMBER_OF_ACTIVE_SELLERS);
    }

    @Test
    public void processingBuyerStatusChangeToActiveIncrementsTheNumberOfActiveBuyers() {
        when(userMock.hasRole(UserRole.BUYER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToActive(userMock);
        verify(userStatisticsMock).setNumberOfActiveBuyers(SAMPLE_NUMBER_OF_ACTIVE_BUYERS + 1);
    }

    @Test
    public void processingSellerStatusChangeToActiveIncrementsTheNumberOfActiveSellers() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToActive(userMock);
        verify(userStatisticsMock).setNumberOfActiveSellers(SAMPLE_NUMBER_OF_ACTIVE_SELLERS + 1);
    }

    @Test
    public void processingBuyerStatusChangeToInactiveDecrementsTheNumberOfActiveBuyers() {
        when(userMock.hasRole(UserRole.BUYER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToInactive(userMock);
        verify(userStatisticsMock).setNumberOfActiveBuyers(SAMPLE_NUMBER_OF_ACTIVE_BUYERS - 1);
    }

    @Test
    public void processingSellerStatusChangeToInactiveDecrementsTheNumberOfActiveSellers() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToInactive(userMock);
        verify(userStatisticsMock).setNumberOfActiveSellers(SAMPLE_NUMBER_OF_ACTIVE_SELLERS - 1);
    }

    @Test
    public void gettingTheStatisticsGetsTheStatistics() {
        assertSame(userStatisticsMock, userStatisticsService.getStatistics());
    }
}
