package ca.ulaval.glo4003.housematch.statistics;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class StatisticTest {

    private static final String SAMPLE_STAT_NAME = "SampleStatName";
    private static final Object SAMPLE_DEFAULT_VALUE = new Object();
    private static final Object SAMPLE_INITIAL_VALUE = new Object();
    private static final Object SAMPLE_VALUE = new Object();

    private StatisticsRepository statisticRepositoryMock;
    private Statistic<Object> statistic;

    @Before
    public void init() {
        statisticRepositoryMock = mock(StatisticsRepository.class);
        when(statisticRepositoryMock.get(SAMPLE_STAT_NAME, SAMPLE_DEFAULT_VALUE)).thenReturn(SAMPLE_INITIAL_VALUE);
        statistic = new Statistic<Object>(SAMPLE_DEFAULT_VALUE, SAMPLE_STAT_NAME, statisticRepositoryMock);
    }

    @Test
    public void settingTheValueSetsTheValue() {
        statistic.setValue(SAMPLE_VALUE);
        assertSame(SAMPLE_VALUE, statistic.getValue());
    }

    @Test
    public void settingTheValuePersistsTheStatisticToTheStatisticsRepository() {
        statistic.setValue(SAMPLE_VALUE);
        verify(statisticRepositoryMock).persist(SAMPLE_STAT_NAME, SAMPLE_VALUE);
    }
}
