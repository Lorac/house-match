package ca.ulaval.glo4003.housematch.domain.statistics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class UserStatisticsTest {

    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_BUYERS = 4;
    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_SELLERS = 3;

    private StatisticFactory statisticFactoryMock;
    private Statistic<Integer> numberOfActiveBuyersStatMock;
    private Statistic<Integer> numberOfActiveSellersStatMock;
    private UserStatistics userStatistics;

    @Before
    public void init() {
        initMocks();
        initStubs();
        initStats();
        userStatistics = new UserStatistics(statisticFactoryMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        statisticFactoryMock = mock(StatisticFactory.class);
        numberOfActiveBuyersStatMock = mock(Statistic.class);
        numberOfActiveSellersStatMock = mock(Statistic.class);
    }

    private void initStubs() {
        when(statisticFactoryMock.createStatistic(eq(UserStatistics.NUMBER_OF_ACTIVE_BUYERS_STAT_NAME), anyInt()))
                .thenReturn(numberOfActiveBuyersStatMock);
        when(statisticFactoryMock.createStatistic(eq(UserStatistics.NUMBER_OF_ACTIVE_SELLERS_STAT_NAME), anyInt()))
                .thenReturn(numberOfActiveSellersStatMock);
    }

    private void initStats() {
        when(numberOfActiveBuyersStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_ACTIVE_BUYERS);
        when(numberOfActiveSellersStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_ACTIVE_SELLERS);
    }

    @Test
    public void settingTheNumberOfActiveBuyersSetsTheNumberOfActiveBuyers() {
        userStatistics.setNumberOfActiveBuyers(SAMPLE_NUMBER_OF_ACTIVE_BUYERS);
        assertEquals(SAMPLE_NUMBER_OF_ACTIVE_BUYERS, userStatistics.getNumberOfActiveBuyers());
    }

    @Test
    public void settingTheNumberOfActiveSellersSetsTheNumberOfActiveSellers() {
        userStatistics.setNumberOfActiveSellers(SAMPLE_NUMBER_OF_ACTIVE_SELLERS);
        assertEquals(SAMPLE_NUMBER_OF_ACTIVE_SELLERS, userStatistics.getNumberOfActiveSellers());
    }
}
