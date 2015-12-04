package ca.ulaval.glo4003.housematch.taskscheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TaskScheduleTest {

    private static final Long SAMPLE_INITIAL_DELAY = 1000L;
    private static final Long SAMPLE_INTERVAL = 1000L;
    private static final TimeUnit SAMPLE_TIME_UNIT = TimeUnit.DAYS;

    private Runnable runnableTaskMock;
    private TaskSchedule taskSchedule;

    @Before
    public void init() {
        runnableTaskMock = mock(Runnable.class);
        taskSchedule = new TaskSchedule(runnableTaskMock, SAMPLE_INITIAL_DELAY, SAMPLE_INTERVAL, SAMPLE_TIME_UNIT);
    }

    @Test
    public void gettingTheRunnableTaskGetsTheRunnableTask() {
        assertEquals(runnableTaskMock, taskSchedule.getRunnableTask());
    }

    @Test
    public void gettingTheIntervalGetsTheInterval() {
        assertEquals(SAMPLE_INTERVAL, taskSchedule.getInterval());
    }

    @Test
    public void gettingTheInitialDelayGetsTheInitialDelay() {
        assertEquals(SAMPLE_INITIAL_DELAY, taskSchedule.getInitialDelay());
    }

    @Test
    public void gettingTheTimeUnitGetsTheTimeUnit() {
        assertEquals(SAMPLE_TIME_UNIT, taskSchedule.getTimeUnit());
    }
}
