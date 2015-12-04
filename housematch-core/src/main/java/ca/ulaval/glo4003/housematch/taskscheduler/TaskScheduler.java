package ca.ulaval.glo4003.housematch.taskscheduler;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class TaskScheduler {

    private ScheduledExecutorService scheduledExecutorService;

    public TaskScheduler(final ScheduledExecutorService scheduledExecutorService, final List<TaskSchedule> taskSchedules) {
        this.scheduledExecutorService = scheduledExecutorService;
        scheduleTasks(taskSchedules);
    }

    private void scheduleTasks(List<TaskSchedule> taskSchedules) {
        for (TaskSchedule taskSchedule : taskSchedules) {
            scheduleTask(taskSchedule);
        }
    }

    private void scheduleTask(TaskSchedule taskSchedule) {
        scheduledExecutorService.scheduleAtFixedRate(taskSchedule.getRunnableTask(), taskSchedule.getInitialDelay(),
                taskSchedule.getInterval(), taskSchedule.getTimeUnit());
    }
}
