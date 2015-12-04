package ca.ulaval.glo4003.housematch.taskscheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TaskSchedulerTest {
    private static final Long SAMPLE_INITIAL_DELAY = 1000L;
    private static final Long SAMPLE_INTERVAL = 1000L;
    private static final TimeUnit SAMPLE_TIME_UNIT = TimeUnit.DAYS;

    private ScheduledExecutorService scheduledExecutorServiceMock;
    private TaskSchedule taskScheduleMock;
    private TaskSchedule anotherTaskScheduleMock;
    private List<TaskSchedule> taskSchedules = new ArrayList<>();
    private Runnable runnableTaskMock;
    private Runnable anotherRunnableTaskMock;

    @Before
    public void init() {
        initMocks();
        initStubs();
        taskSchedules.add(taskScheduleMock);
        taskSchedules.add(anotherTaskScheduleMock);
    }

    private void initMocks() {
        runnableTaskMock = mock(Runnable.class);
        anotherRunnableTaskMock = mock(Runnable.class);
        scheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        taskScheduleMock = mock(TaskSchedule.class);
        anotherTaskScheduleMock = mock(TaskSchedule.class);
    }

    private void initStubs() {
        when(taskScheduleMock.getRunnableTask()).thenReturn(runnableTaskMock);
        when(anotherTaskScheduleMock.getRunnableTask()).thenReturn(anotherRunnableTaskMock);
        initCommonTaskScheduleStubs(taskScheduleMock);
        initCommonTaskScheduleStubs(anotherTaskScheduleMock);
    }

    private void initCommonTaskScheduleStubs(TaskSchedule taskSchedule) {
        when(taskSchedule.getInitialDelay()).thenReturn(SAMPLE_INITIAL_DELAY);
        when(taskSchedule.getInterval()).thenReturn(SAMPLE_INTERVAL);
        when(taskSchedule.getTimeUnit()).thenReturn(SAMPLE_TIME_UNIT);
    }

    @Test
    public void taskSchedulerSchedulesAllTasks() {
        new TaskScheduler(scheduledExecutorServiceMock, taskSchedules);

        verify(scheduledExecutorServiceMock).scheduleAtFixedRate(runnableTaskMock, SAMPLE_INITIAL_DELAY, SAMPLE_INTERVAL, SAMPLE_TIME_UNIT);
        verify(scheduledExecutorServiceMock).scheduleAtFixedRate(anotherRunnableTaskMock, SAMPLE_INITIAL_DELAY, SAMPLE_INTERVAL,
                SAMPLE_TIME_UNIT);
    }
}
