package ca.ulaval.glo4003.housematch.statistics.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class UserStatisticsCollectorTest {

    private static final Integer SAMPLE_NUMBER_OF_ACTIVER_BUYERS = 3;
    private static final Integer SAMPLE_NUMBER_OF_ACTIVER_SELLERS = 2;

    private StatisticsRepository statisticsRepositoryMock;
    private User userMock;

    private UserStatisticsCollector userStatisticsCollector;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        userStatisticsCollector = new UserStatisticsCollector(statisticsRepositoryMock);
    }

    private void initMocks() {
        statisticsRepositoryMock = mock(StatisticsRepository.class);
        userMock = mock(User.class);
    }

    private void stubMethods() {
        when(statisticsRepositoryMock.get(eq(UserStatisticsCollector.NUMBER_OF_ACTIVE_BUYERS_STAT_NAME), anyInt()))
                .thenReturn(SAMPLE_NUMBER_OF_ACTIVER_BUYERS);
        when(statisticsRepositoryMock.get(eq(UserStatisticsCollector.NUMBER_OF_ACTIVE_SELLERS_STAT_NAME), anyInt()))
                .thenReturn(SAMPLE_NUMBER_OF_ACTIVER_SELLERS);
    }

    @Test
    public void applyingBuyerStatusChangeToActiveIncrementsTheNumberOfActiveBuyers() {
        when(userMock.hasRole(UserRole.BUYER)).thenReturn(Boolean.TRUE);
        userStatisticsCollector.applyUserStatusChangeToActive(userMock);
        verify(statisticsRepositoryMock).persist(UserStatisticsCollector.NUMBER_OF_ACTIVE_BUYERS_STAT_NAME,
                SAMPLE_NUMBER_OF_ACTIVER_BUYERS + 1);
    }

    @Test
    public void applyingSellerStatusChangeToActiveIncrementsTheNumberOfActiveSellers() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(Boolean.TRUE);
        userStatisticsCollector.applyUserStatusChangeToActive(userMock);
        verify(statisticsRepositoryMock).persist(UserStatisticsCollector.NUMBER_OF_ACTIVE_SELLERS_STAT_NAME,
                SAMPLE_NUMBER_OF_ACTIVER_SELLERS + 1);
    }

    @Test
    public void applyingBuyerStatusChangeToInactiveDecrementsTheNumberOfActiveBuyers() {
        when(userMock.hasRole(UserRole.BUYER)).thenReturn(Boolean.TRUE);
        userStatisticsCollector.applyUserStatusChangeToInactive(userMock);
        verify(statisticsRepositoryMock).persist(UserStatisticsCollector.NUMBER_OF_ACTIVE_BUYERS_STAT_NAME,
                SAMPLE_NUMBER_OF_ACTIVER_BUYERS - 1);
    }

    @Test
    public void applyingSellerStatusChangeToInactiveDecrementsTheNumberOfActiveSellers() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(Boolean.TRUE);
        userStatisticsCollector.applyUserStatusChangeToInactive(userMock);
        verify(statisticsRepositoryMock).persist(UserStatisticsCollector.NUMBER_OF_ACTIVE_SELLERS_STAT_NAME,
                SAMPLE_NUMBER_OF_ACTIVER_SELLERS - 1);
    }

    @Test
    public void gettingTheStatisticsCorrectlyCreatesTheStatisticsDTO() {
        UserStatistics userStatistics = userStatisticsCollector.getStatistics();

        assertEquals(SAMPLE_NUMBER_OF_ACTIVER_BUYERS, userStatistics.getNumberOfActiveBuyers());
        assertEquals(SAMPLE_NUMBER_OF_ACTIVER_SELLERS, userStatistics.getNumberOfActiveSellers());
    }
}
