package ca.ulaval.glo4003.housematch.statistics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class StatisticFactoryTest {

    private static final String SAMPLE_STAT_NAME = "SampleStatName";
    private static final Object SAMPLE_INITIAL_VALUE = new Object();
    private static final Object SAMPLE_VALUE = new Object();

    private StatisticsRepository statisticsRepositoryMock;
    private StatisticFactory statisticFactory;

    @Before
    public void init() {
        statisticsRepositoryMock = mock(StatisticsRepository.class);
        statisticFactory = new StatisticFactory(statisticsRepositoryMock);
    }

    @Test
    public void userFactoryCreatesStatisticWithTheSpecifiedAttributes() {
        when(statisticsRepositoryMock.get(SAMPLE_STAT_NAME, SAMPLE_INITIAL_VALUE)).thenReturn(SAMPLE_VALUE);
        Statistic<Object> statistic = statisticFactory.createStatistic(SAMPLE_STAT_NAME, SAMPLE_INITIAL_VALUE);
        assertEquals(SAMPLE_VALUE, statistic.getValue());
    }
}
