package ca.ulaval.glo4003.housematch.taskscheduler;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class TaskScheduler {

    private ScheduledExecutorService scheduledExecutorService;

    public TaskScheduler(ScheduledExecutorService scheduledExecutorService, List<TaskSchedule> taskSchedules) {
        this.scheduledExecutorService = scheduledExecutorService;
        scheduleTasks(taskSchedules);
    }

    private void scheduleTasks(List<TaskSchedule> taskSchedules) {
        for (TaskSchedule taskSchedule : taskSchedules) {
            scheduledExecutorService.scheduleAtFixedRate(taskSchedule.getTask(), taskSchedule.getInitialDelay(), taskSchedule.getInterval(),
                    taskSchedule.getTimeUnit());
        }
    }
}
