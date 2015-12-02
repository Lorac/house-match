package ca.ulaval.glo4003.housematch.taskscheduler;

import java.util.concurrent.TimeUnit;

public class TaskSchedule {

    private Runnable task;
    private Long interval;
    private Long initialDelay;
    private TimeUnit timeUnit;

    public TaskSchedule(Runnable task, Long interval, Long initialDelay, TimeUnit timeUnit) {
        this.task = task;
        this.interval = interval;
        this.initialDelay = initialDelay;
        this.timeUnit = timeUnit;
    }

    public Runnable getTask() {
        return task;
    }

    public Long getInterval() {
        return interval;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
