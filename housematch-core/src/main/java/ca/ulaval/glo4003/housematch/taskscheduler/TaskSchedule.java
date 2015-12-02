package ca.ulaval.glo4003.housematch.taskscheduler;

import java.util.concurrent.TimeUnit;

public class TaskSchedule {

    private Runnable runnableTask;
    private Long interval;
    private Long initialDelay;
    private TimeUnit timeUnit;

    public TaskSchedule(final Runnable runnableTask, final Long initialDelay, final Long interval, final TimeUnit timeUnit) {
        this.runnableTask = runnableTask;
        this.initialDelay = initialDelay;
        this.interval = interval;
        this.timeUnit = timeUnit;
    }

    public Runnable getRunnableTask() {
        return runnableTask;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public Long getInterval() {
        return interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
