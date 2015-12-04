package ca.ulaval.glo4003.housematch.services.statistics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.statistics.Statistic;
import ca.ulaval.glo4003.housematch.domain.statistics.StatisticFactory;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserStatisticsServiceTest {

    private static final Integer SAMPLE_NUMBER_OF_ACTIVER_BUYERS = 3;
    private static final Integer SAMPLE_NUMBER_OF_ACTIVER_SELLERS = 2;

    private StatisticFactory statisticFactoryMock;
    private Statistic<Integer> numberOfActiveBuyersStatMock;
    private Statistic<Integer> numberOfActiveSellersStatMock;
    private User userMock;

    private UserStatisticsService userStatisticsService;

    @Before
    public void init() {
        initMocks();
        initStubs();
        initStats();
        userStatisticsService = new UserStatisticsService(statisticFactoryMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        statisticFactoryMock = mock(StatisticFactory.class);
        numberOfActiveBuyersStatMock = mock(Statistic.class);
        numberOfActiveSellersStatMock = mock(Statistic.class);
        userMock = mock(User.class);
    }

    private void initStubs() {
        when(statisticFactoryMock.createStatistic(eq(UserStatisticsService.NUMBER_OF_ACTIVE_BUYERS_STAT_NAME), anyInt()))
                .thenReturn(numberOfActiveBuyersStatMock);
        when(statisticFactoryMock.createStatistic(eq(UserStatisticsService.NUMBER_OF_ACTIVE_SELLERS_STAT_NAME), anyInt()))
                .thenReturn(numberOfActiveSellersStatMock);
    }

    private void initStats() {
        when(numberOfActiveBuyersStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_ACTIVER_BUYERS);
        when(numberOfActiveSellersStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_ACTIVER_SELLERS);
    }

    @Test
    public void processingBuyerStatusChangeToActiveIncrementsTheNumberOfActiveBuyers() {
        when(userMock.hasRole(UserRole.BUYER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToActive(userMock);
        verify(numberOfActiveBuyersStatMock).setValue(SAMPLE_NUMBER_OF_ACTIVER_BUYERS + 1);
    }

    @Test
    public void processingSellerStatusChangeToActiveIncrementsTheNumberOfActiveSellers() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToActive(userMock);
        verify(numberOfActiveSellersStatMock).setValue(SAMPLE_NUMBER_OF_ACTIVER_SELLERS + 1);
    }

    @Test
    public void processingBuyerStatusChangeToInactiveDecrementsTheNumberOfActiveBuyers() {
        when(userMock.hasRole(UserRole.BUYER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToInactive(userMock);
        verify(numberOfActiveBuyersStatMock).setValue(SAMPLE_NUMBER_OF_ACTIVER_BUYERS - 1);
    }

    @Test
    public void processingSellerStatusChangeToInactiveDecrementsTheNumberOfActiveSellers() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(Boolean.TRUE);
        userStatisticsService.processUserStatusChangeToInactive(userMock);
        verify(numberOfActiveSellersStatMock).setValue(SAMPLE_NUMBER_OF_ACTIVER_SELLERS - 1);
    }

    @Test
    public void gettingTheStatisticsCorrectlyCreatesTheStatisticsDTO() {
        UserStatistics userStatistics = userStatisticsService.getStatistics();

        assertEquals(SAMPLE_NUMBER_OF_ACTIVER_BUYERS, userStatistics.getNumberOfActiveBuyers());
        assertEquals(SAMPLE_NUMBER_OF_ACTIVER_SELLERS, userStatistics.getNumberOfActiveSellers());
    }
}
